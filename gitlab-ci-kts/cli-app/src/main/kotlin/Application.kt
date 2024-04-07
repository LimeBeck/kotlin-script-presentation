import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import dev.limebeck.ci.gitlab.GitlabCiKtScriptLoader
import dev.otbe.gitlab.ci.core.toYml
import java.io.File

class Run : CliktCommand() {
    val script: File by argument(help = "Script file").file(canBeDir = false, mustBeReadable = true)

    override fun run() {
        val loader = GitlabCiKtScriptLoader()
        when (val result = loader.loadScript(script)) {
            is GitlabCiKtScriptLoader.LoadResult.Success -> {
                val builder = result.value
                println(builder.build().toYml())
            }

            is GitlabCiKtScriptLoader.LoadResult.Error -> {
                result.diagnostic.forEach(::println)
            }
        }
    }
}

fun main(args: Array<String>) = Run().main(args)