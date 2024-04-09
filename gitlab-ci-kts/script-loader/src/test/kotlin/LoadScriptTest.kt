import dev.limebeck.ci.gitlab.GitlabCiKtScriptLoader
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
        val result = GitlabCiKtScriptLoader().loadScript(file)
        assertIs<GitlabCiKtScriptLoader.LoadResult.Success>(result)
        val yaml = result.value.build().toYml()
        val expectedYaml = examplesDir.resolve(".gitlab-ci.yaml").readText()
        assertEquals(expectedYaml, yaml)
    }
}