import dev.limebeck.ci.gitlab.GitlabCiKtJsr223ScriptLoader
import dev.otbe.gitlab.ci.core.toYml
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class LoadScriptTest {
    @Test
    fun `Load JSR223 Script`() {
        val examplesDir = File(".")
            .absoluteFile
            .parentFile
            .parentFile
            .resolve("script-definition/example")
        val file = examplesDir.resolve("main.gitlab-ci.kts")
        val result = GitlabCiKtJsr223ScriptLoader()
            .loadScript(file)
        assertIs<GitlabCiKtJsr223ScriptLoader.LoadResult.Success>(result)
        val yaml = result.value.build().toYml()
        val expectedYaml = examplesDir.resolve(".gitlab-ci.yaml").readText()
        assertEquals(expectedYaml, yaml)
    }
}