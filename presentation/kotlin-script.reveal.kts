import dev.limebeck.revealkt.core.RevealKt
import qrcode.color.Colors

title = "Hello from my awesome presentation"

fun kotlinCode(block: () -> String) = code(lang = "kotlin", block = block)

configuration {
    controls = false
    progress = true
    theme = RevealKt.Configuration.Theme.Predefined.BLACK
    additionalCssStyle = loadAsset("additional.css").decodeToString()
}

slides {
    val scriptPositioningTitle = title { "Позиционирование Kotlin Scripting" }
    slide {
        +scriptPositioningTitle
    }
    slide {
        +scriptPositioningTitle
        +smallTitle { "От Jetbrains:" }
        +unorderedListOf(
            listOf(
                "Build scripts (Gradle/Kobalt)",
                "Test scripts (Spek)",
                "Command-line utilities",
                "Routing scripts (ktor)",
                "Type-safe configuration files (TeamCity)",
                "In-process scripting and REPL for IDE",
                "Consoles like IPython/Jupyter Notebook",
                "Game scripting engines",
                "..."
            )
        )
    }

    slide {
        +scriptPositioningTitle
        +smallTitle { "Обобщенно:" }
        +unorderedListOf(
            listOf(
                "Read-Eval-Print Loop aka REPL",
                "замена BASH-скриптов в автоматизации задач",
                "встраивание скриптового движка в приложение",
                "скрипты, которые компилируются вместе с исходниками",
            )
        )
    }

    slide {
        +smallTitle { "REPL" }
        +img("REPL.png")
    }

    val jupyterTitle = smallTitle { "Kotlin для Jupiter Notebook" }
    slide {
        +jupyterTitle
    }
    slide {
        +jupyterTitle
        +img("./jupyter.png") {
            stretch = true
        }
    }

    val autoTitle = smallTitle { "замена BASH-скриптов в автоматизации задач" }
    val mainKtsTitle = smallTitle { ".main.kts" }
    slide {
        +autoTitle
    }
    slide {
        +autoTitle
        +kotlinCode {
            //language=kotlin
            """
                #!/usr/bin/env kotlin
                
                import java.io.File
                
                val file = File("path/to/my.file")
                val text = file.readText()
                val newText = text.process()
                file.writeText(newText)
            """.trimIndent()
        }
    }
    slide {
        +autoTitle
        +mainKtsTitle
    }
    slide {
        +mainKtsTitle
        +kotlinCode {
            loadAsset("test.main.kts").decodeToString()
        }
    }
    slide {
        +mainKtsTitle
        +code(lang = "bash") {
            """
                > ./test.main.kts 
                Value for option --input should be always provided in command line.
                Usage: example options_list
                Options: 
                    --input, -i -> Input file (always required) { String }
                    --debug, -d [false] -> Turn on debug mode 
                    --help, -h -> Usage info 
            """.trimIndent()
        }
    }

    val scriptEngineTitle = smallTitle { "встраивание скриптового движка в приложение" }
    slide {
        +scriptEngineTitle
    }
    val why = smallTitle { "Зачем?" }
    slide {
        +why
    }
    slide {
        +why
        +unorderedListOf(listOf(
            "конфигурация через \"Typesafe DSL\"",
            "микроядерная архитектура",
            "кастомизация действий пользователем"
        ))
    }


    val componentsTitle = title { "Основные компоненты скриптинга" }
    slide {
        +componentsTitle
    }
    slide {//Добавить схему
        +componentsTitle
        +unorderedListOf(
            listOf(
                "Script Definition",
                "Script Loader",
            )
        )
    }

    val scriptDefTitle = smallTitle { "Script Definition" }
    slide {
        +scriptDefTitle
    }
    slide {
        +scriptDefTitle
        +smallTitle { "\"Look and feel\"" }
    }
    slide {
        +scriptDefTitle
        +unorderedListOf(
            listOf(
                "Конфигурация компиляции",
                "Конфигурация выполнения",
            )
        )
    }

    slide {
        +smallTitle { "Базовый пример" }
        +kotlinCode {
            """
                @KotlinScript(
                    fileExtension = "shiny.kts",
                )
                abstract class MyShinyKtScript //Класс обязательно должен быть открытым или абстрактным
            """.trimIndent()
        }
    }

    val compilationTitle = smallTitle { "Конфигурация компиляции" }
    val exampleTitle = smallTitle { "Пример" }
    verticalSlide {
        slide {
            +compilationTitle
        }
        slide {
            +compilationTitle
            +unorderedListOf(
                listOf(
                    "Зависимости",
                    "Импорты по умолчанию",
                    "Определение неявных (implicit) ресиверов",
                    "Конфигурация IDE",
                    "Параметры компилятора Kotlin"
                )
            )
        }

        slide {
            +compilationTitle
            +exampleTitle
            +kotlinCode {
                """
                    object MyShinyScriptCompilationConfiguration : ScriptCompilationConfiguration({
                        jvm {
                            dependenciesFromCurrentContext(wholeClasspath = true)
                        }
                        defaultImports(
                            "dev.limebeck.myshiny.builder.*",
                        )
                        implicitReceivers(MyShinyBuilder::class)
                        ide {
                            acceptedLocations(ScriptAcceptedLocation.Everywhere)
                        }
                        
                        //Костыль, без которого скриптинг не работает после версии 1.7.20
                        compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH") 
                    })
                """.trimIndent()
            }
        }
        slide {
            +compilationTitle
            +exampleTitle
            +kotlinCode {
                """
                    @KotlinScript(
                        fileExtension = "shiny.kts",
                        compilationConfiguration = MyShinyScriptCompilationConfiguration::class,
                    )
                    abstract class MyShinyKtScript
                """.trimIndent()
            }
        }
    }

    verticalSlide {
        slide {
            +compilationTitle
            +smallTitle { "Внешние зависимости" } //Добавить пример
            +kotlinCode {
                """
                    object MyShinyScriptCompilationConfiguration : ScriptCompilationConfiguration({
                        refineConfiguration {
                            // Process specified annotations with the provided handler
                            onAnnotations(
                                DependsOn::class,
                                Repository::class,
                                handler = ::configureMavenDepsOnAnnotations
                            )
                        }
                    }
                """.trimIndent()
            }
        }
    }

    val evaluationTitle = smallTitle { "Конфигурация исполнения" }
    verticalSlide {
        slide {
            +evaluationTitle
        }
        slide {
            +evaluationTitle
            +unorderedListOf(
                listOf(
                    "Параметры запуска JVM",
                    "Подкидывание созданных экземпляров implicit ресиверов",
                    "Аргументы коструктора для базового класса скрипта (MyShinyKtScript)",
                    "Возможность разделения инстансов скрипта",
                    "Просмотр истории запусков (для REPL)",
                    "Возможность переопределения любых частей скрипта"
                )
            )
        }
        slide {
            +evaluationTitle
            +exampleTitle
            +kotlinCode {
                """
                    object MyShinyScriptEvaluationConfiguration : ScriptEvaluationConfiguration({
                        scriptsInstancesSharing(false)
                    })
                    
                    @KotlinScript(
                        fileExtension = "shiny.kts",
                        compilationConfiguration = MyShinyScriptCompilationConfiguration::class,
                        evaluationConfiguration = MyShinyScriptEvaluationConfiguration::class,
                    )
                    abstract class MyShinyKtScript
                """.trimIndent()
            }
        }
        slide {
            +evaluationTitle
            +exampleTitle
            +kotlinCode {
                """
                    object MyShinyScriptEvaluationConfiguration : ScriptEvaluationConfiguration({
                        scriptsInstancesSharing(false)
                        constructorArgs("My Arg String")
                    })
                    
                    @KotlinScript(
                        fileExtension = "shiny.kts",
                        compilationConfiguration = MyShinyScriptCompilationConfiguration::class,
                        evaluationConfiguration = MyShinyScriptEvaluationConfiguration::class,
                    )
                    abstract class MyShinyKtScript(val arg: String)
                """.trimIndent()
            }
        }
    }

    val loaderTitle = smallTitle { "Script loader" }
    verticalSlide {
        slide {
            +loaderTitle
        }
        slide {
            +loaderTitle
            +kotlinCode {
                """
                    fun BasicJvmScriptingHost.evalFile(
                        scriptFile: File
                    ): ResultWithDiagnostics<EvaluationResult> {
                        val compilationConfiguration = 
                            createJvmCompilationConfigurationFromTemplate<RevealKtScript> { 
                                //Тут мы можем дополнить и переопределить конфигурацию при необходимости
                            }
                        val evaluationConfiguration = 
                            createJvmEvaluationConfigurationFromTemplate<RevealKtScript> {
                                //Тут мы можем дополнить и переопределить конфигурацию при необходимости
                            }
                        return eval(
                            script = scriptFile.toScriptSource(),
                            compilationConfiguration = compilationConfiguration,
                            evaluationConfiguration = evaluationConfiguration
                        )
                    }

                    val scriptingHost = BasicJvmScriptingHost()
                    val result = scriptingHost.evalFile(scriptFile)
                """.trimIndent()
            }
        }
    }

    val jsrTitle = title { "Kotlin Script VS JSR223" }
    slide {
        +jsrTitle
    }
//    markdownSlide {
//        """
//            |     | Kotlin Script | JSR223 |
//            | --- | --- | --- |
//            | Поддержка IDE | Полная (IDEA) | Зависит от языка |
//            | Простой запуск | Через команду kotlin | Нет |
//            | Язык | Kotlin | Зависит от имплементации |
//        """.trimIndent()
//    }
    slide {

        val columns = listOf(
            listOf(
                regularText(""),
                regularText("Поддержка IDE"),
                regularText("Простой запуск"),
                regularText("Язык"),
            ),
            listOf(
                smallTitle("Kotlin Script"),
                regularText("Полная (IDEA)"),
                regularText("Через команду kotlin"),
                regularText("Kotlin"),
            ),
            listOf(
                smallTitle("JSR223"),
                regularText("Зависит от языка"),
                regularText("Нет"),
                regularText("Зависит от имплементации"),
            )
        )
        for (rowNum in columns.first().indices) {
            +row {
                for (column in columns) {
                    column {
                        +column[rowNum]
                    }
                }
            }
        }
    }


//    slide {
//        //Перенести в конец в полезные штуки
//        +title { "KEEP: Scripting Support" } //KEEP расшифровать
//        +qrCode("https://github.com/Kotlin/KEEP/blob/master/proposals/scripting-support.md#implementation-status") {
//            stretch = true
//            transformBuilder {
//                val logo = loadAsset("logo2.png")
//                it.withSize(20)
//                    .withColor(Colors.css("#B125EA"))
//                    .withLogo(logo, 150, 150, clearLogoArea = true)
//            }
//        }
//    }
    slide {
        //Перенести в конец в полезные штуки
        +title { "Ссылка на презентацию" }
        +qrCode("https://github.com/LimeBeck/kotlin-script-presentation") {
            stretch = true
            transformBuilder {
                val logo = loadAsset("logo2.png")
                it.withSize(20)
                    .withColor(Colors.css("#B125EA"))
                    .withLogo(logo, 150, 150, clearLogoArea = true)
            }
        }
    }
}