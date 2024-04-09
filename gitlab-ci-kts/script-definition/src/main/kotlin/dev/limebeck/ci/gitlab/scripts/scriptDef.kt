package dev.limebeck.ci.gitlab.scripts

import dev.limebeck.ci.gitlab.scripts.tools.Directories
import dev.otbe.gitlab.ci.dsl.PipelineBuilder
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.ByteBuffer
import java.security.MessageDigest
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.*
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.host.ScriptingHostConfiguration
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.compilationCache
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.CompiledScriptJarsCache

@KotlinScript(
    displayName = "Gitlab CI Kotlin configuration",
    fileExtension = "gitlab-ci.kts",
    compilationConfiguration = GitlabCiKtScriptCompilationConfiguration::class,
    evaluationConfiguration = GitlabCiKtEvaluationConfiguration::class,
    hostConfiguration = GitlabCiKtHostConfiguration::class,
)
abstract class GitlabCiKtScript

const val COMPILED_SCRIPTS_CACHE_DIR_ENV_VAR = "KOTLIN_MAIN_KTS_COMPILED_SCRIPTS_CACHE_DIR"
const val COMPILED_SCRIPTS_CACHE_DIR_PROPERTY = "kotlin.main.kts.compiled.scripts.cache.dir"
const val COMPILED_SCRIPTS_CACHE_VERSION = 1

object GitlabCiKtScriptCompilationConfiguration : ScriptCompilationConfiguration({
    jvm { dependenciesFromClassContext(PipelineBuilder::class, wholeClasspath = true) }
    defaultImports(DependsOn::class, Repository::class)
    defaultImports(
        "dev.otbe.gitlab.ci.core.model.*",
        "dev.otbe.gitlab.ci.dsl.*",
        "dev.otbe.gitlab.ci.core.goesTo"
    )
    implicitReceivers(PipelineBuilder::class)
    ide { acceptedLocations(ScriptAcceptedLocation.Everywhere) }
    compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH")

    // Callbacks
    refineConfiguration {
        // Process specified annotations with the provided handler
        onAnnotations(DependsOn::class, Repository::class, handler = ::configureMavenDepsOnAnnotations)
    }
})

object GitlabCiKtHostConfiguration : ScriptingHostConfiguration({
    jvm {
        val cacheExtSetting = System.getProperty(COMPILED_SCRIPTS_CACHE_DIR_PROPERTY)
            ?: System.getenv(COMPILED_SCRIPTS_CACHE_DIR_ENV_VAR)
        val cacheBaseDir = when {
            cacheExtSetting == null -> Directories(System.getProperties(), System.getenv()).cache
                ?.takeIf { it.exists() && it.isDirectory }
                ?.let { File(it, "main.kts.compiled.cache").apply { mkdir() } }
            cacheExtSetting.isBlank() -> null
            else -> File(cacheExtSetting)
        }?.takeIf { it.exists() && it.isDirectory }
        if (cacheBaseDir != null)
            compilationCache(
                CompiledScriptJarsCache { script, scriptCompilationConfiguration ->
                    File(cacheBaseDir, compiledScriptUniqueName(script, scriptCompilationConfiguration) + ".jar")
                }
            )
    }
})

object GitlabCiKtEvaluationConfiguration : ScriptEvaluationConfiguration({
    scriptsInstancesSharing(false)
    constructorArgs()
})

// Handler that reconfigures the compilation on the fly
fun configureMavenDepsOnAnnotations(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
    val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
        ?: return context.compilationConfiguration.asSuccess()
    return runBlocking {
        resolver.resolveFromScriptSourceAnnotations(annotations)
    }.onSuccess {
        context.compilationConfiguration.with {
            dependencies.append(JvmDependency(it))
        }.asSuccess()
    }
}

private val resolver = CompoundDependenciesResolver(FileSystemDependenciesResolver(), MavenDependenciesResolver())

private fun compiledScriptUniqueName(script: SourceCode, scriptCompilationConfiguration: ScriptCompilationConfiguration): String {
    val digestWrapper = MessageDigest.getInstance("SHA-256")

    fun addToDigest(chunk: String) = with(digestWrapper) {
        val chunkBytes = chunk.toByteArray()
        update(chunkBytes.size.toByteArray())
        update(chunkBytes)
    }

    digestWrapper.update(COMPILED_SCRIPTS_CACHE_VERSION.toByteArray())
    addToDigest(script.text)
    scriptCompilationConfiguration.notTransientData.entries
        .sortedBy { it.key.name }
        .forEach {
            addToDigest(it.key.name)
            addToDigest(it.value.toString())
        }
    return digestWrapper.digest().toHexString()
}

private fun ByteArray.toHexString(): String = joinToString("", transform = { "%02x".format(it) })

private fun Int.toByteArray() = ByteBuffer.allocate(Int.SIZE_BYTES).also { it.putInt(this) }.array()