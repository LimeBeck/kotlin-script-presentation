package dev.limebeck.ci.gitlab

import dev.otbe.gitlab.ci.dsl.PipelineBuilder
import java.io.File
import javax.script.ScriptEngineManager
import kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl

class GitlabCiKtJsr223ScriptLoader {
    fun loadScript(scriptFile: File): LoadResult = try {
        val engine = ScriptEngineManager().getEngineByExtension("gitlab-ci.kts")!!

        engine.eval(scriptFile.readText())

        val implicitReceivers = (engine as KotlinJsr223ScriptEngineImpl)
            .evaluationConfiguration
            .notTransientData
            .entries
            .find { it.key.name == "implicitReceivers" }
            ?.value as? List<*>

        val builder = implicitReceivers?.filterIsInstance<PipelineBuilder>()?.firstOrNull()
            ?: throw RuntimeException("implicit receivers not provided")

        LoadResult.Success(builder)
    } catch (e: Exception) {
        LoadResult.Error(listOf(e.stackTraceToString()))
    }

    sealed interface LoadResult {
        data class Success(
            val value: PipelineBuilder
        ) : LoadResult

        data class Error(
            val diagnostic: List<String>
        ) : LoadResult
    }
}