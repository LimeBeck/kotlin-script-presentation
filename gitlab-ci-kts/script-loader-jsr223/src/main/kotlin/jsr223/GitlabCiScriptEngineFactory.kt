package dev.limebeck.ci.gitlab.jsr223

import dev.limebeck.ci.gitlab.scripts.GitlabCiKtScript
import dev.otbe.gitlab.ci.dsl.PipelineBuilder
import org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmScriptEngineFactoryBase
import org.jetbrains.kotlin.cli.common.repl.ScriptArgsWithTypes
import java.io.File
import javax.script.ScriptEngine
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.fileExtension
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.api.with
import kotlin.script.experimental.jvm.JvmScriptCompilationConfigurationBuilder
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvm.util.scriptCompilationClasspathFromContext
import kotlin.script.experimental.jvmhost.createJvmScriptDefinitionFromTemplate
import kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223ScriptEngineImpl

class GitlabCiScriptEngineFactory : KotlinJsr223JvmScriptEngineFactoryBase() {
    private val scriptDefinition = createJvmScriptDefinitionFromTemplate<GitlabCiKtScript>()
    private var lastClassLoader: ClassLoader? = null
    private var lastClassPath: List<File>? = null

    override fun getExtensions(): List<String> =
        listOf(scriptDefinition.compilationConfiguration[ScriptCompilationConfiguration.fileExtension]!!)

    override fun getScriptEngine(): ScriptEngine =
        KotlinJsr223ScriptEngineImpl(
            this,
            scriptDefinition.compilationConfiguration.with {
                jvm {
                    dependenciesFromCurrentContext()
                }
            },
            scriptDefinition.evaluationConfiguration.with {
                implicitReceivers(PipelineBuilder())
            }
        ) { ScriptArgsWithTypes(arrayOf(), arrayOf()) }

    @Synchronized
    private fun JvmScriptCompilationConfigurationBuilder.dependenciesFromCurrentContext() {
        val currentClassLoader = Thread.currentThread().contextClassLoader
        val classPath = if (lastClassLoader == null || lastClassLoader != currentClassLoader) {
            scriptCompilationClasspathFromContext(
                classLoader = currentClassLoader,
                wholeClasspath = true,
                unpackJarCollections = true
            ).also {
                lastClassLoader = currentClassLoader
                lastClassPath = it
            }
        } else lastClassPath!!
        updateClasspath(classPath)
    }
}
