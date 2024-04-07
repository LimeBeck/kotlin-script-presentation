package dev.limebeck.ci.gitlab

import dev.limebeck.ci.gitlab.scripts.GitlabCiKtScript
import dev.otbe.gitlab.ci.dsl.PipelineBuilder
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

class GitlabCiKtScriptLoader {
    private val scriptingHost = BasicJvmScriptingHost()

    fun loadScript(scriptFile: File): LoadResult {
        val result = scriptingHost.evalFile(scriptFile)

        val implicitReceivers = result.valueOrNull()
            ?.configuration
            ?.notTransientData
            ?.entries
            ?.find { it.key.name == "implicitReceivers" }?.value as? List<*>

        val builder = implicitReceivers?.filterIsInstance<PipelineBuilder>()?.firstOrNull()

        return if (builder == null) {
            LoadResult.Error(result.reports)
        } else {
            LoadResult.Success(builder)
        }
    }

    sealed interface LoadResult {
        data class Success(
            val value: PipelineBuilder
        ) : LoadResult

        data class Error(
            val diagnostic: List<ScriptDiagnostic>
        ) : LoadResult
    }

    private fun BasicJvmScriptingHost.evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
        val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<GitlabCiKtScript> { }
        val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<GitlabCiKtScript> {
            implicitReceivers(PipelineBuilder())
        }
        return eval(
            script = scriptFile.toScriptSource(),
            compilationConfiguration = compilationConfiguration,
            evaluationConfiguration = evaluationConfiguration
        )
    }
}