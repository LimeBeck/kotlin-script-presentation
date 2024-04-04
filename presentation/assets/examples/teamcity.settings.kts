import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

version = "2024.03"

project {
    buildType {
        id("HelloWorld")
        name = "Hello world"
        steps {
            script {
                scriptContent = "echo 'Hello world!'"
            }
        }
    }
}