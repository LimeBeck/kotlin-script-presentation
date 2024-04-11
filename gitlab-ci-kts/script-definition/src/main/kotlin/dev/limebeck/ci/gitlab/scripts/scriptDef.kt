package dev.limebeck.ci.gitlab.scripts

import dev.otbe.gitlab.ci.dsl.PipelineBuilder
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.script.experimental.dependencies.Repository
import kotlin.script.experimental.host.ScriptingHostConfiguration
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
    refineConfiguration {
        onAnnotations(DependsOn::class, Repository::class, handler = ::configureMavenDepsOnAnnotations)
    }
})

object GitlabCiKtHostConfiguration : ScriptingHostConfiguration({
    jvm {
        val cacheBaseDir = findCacheBaseDir()
        if (cacheBaseDir != null)
            compilationCache(
                CompiledScriptJarsCache { script, scriptCompilationConfiguration ->
                    cacheBaseDir
                        .resolve(compiledScriptUniqueName(script, scriptCompilationConfiguration) + ".jar")
                }
            )
    }
})

object GitlabCiKtEvaluationConfiguration : ScriptEvaluationConfiguration({
    scriptsInstancesSharing(false)
    constructorArgs()
})
