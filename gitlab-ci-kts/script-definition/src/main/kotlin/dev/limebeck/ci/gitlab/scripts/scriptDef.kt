package dev.limebeck.ci.gitlab.scripts

import dev.otbe.gitlab.ci.dsl.PipelineBuilder
import kotlinx.coroutines.runBlocking
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.*
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = "gitlab-ci.kts",
    compilationConfiguration = GitlabCiKtScriptCompilationConfiguration::class,
    evaluationConfiguration = GitlabCiKtEvaluationConfiguration::class,
)
abstract class GitlabCiKtScript

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