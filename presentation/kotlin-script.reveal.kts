import dev.limebeck.revealkt.core.RevealKt
import qrcode.color.Colors
import java.io.File

title = "Hello from my awesome presentation"

fun kotlinCode(block: () -> String) = code(lang = "kotlin", block = block)

configuration {
    controls = false
    progress = true
    theme = RevealKt.Configuration.Theme.Predefined.BLACK
    //language=CSS
    additionalCssStyle = """
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap');

		.reveal h1,
		.reveal h2,
		.reveal h3,
		.reveal h4,
		.reveal h5,
		.reveal h6 {
			font-family: 'Roboto', sans-serif;
		}

		.reveal .slide {
			font-family: 'Roboto', sans-serif;
		}

		.container {
			display: flex;
		}

		.col {
			flex: 1;
		}

        pre > code {
            font-size: large;
        }
    """.trimIndent()
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
                "REPL",
                "встраивание скриптового движка в приложение",
                "замена BASH-скриптов в автоматизации задач",
                "скрипты, которые компилируются вместе с исходниками",
            )
        )
    }

    slide {
        +title { "KEEP: Scripting Support" }
        +qrCode("https://github.com/Kotlin/KEEP/blob/master/proposals/scripting-support.md#implementation-status") {
            stretch = true
            transformBuilder {
                val logo =
                    File("/home/lime/work/opensource/presentations/kotlin-script/kotlin-script/presentation/assets/logo2.png").readBytes()
                it.withSize(20)
                    .withColor(Colors.css("#B125EA"))
                    .withLogo(logo, 150, 150, clearLogoArea = true)
            }
        }
    }

    val componentsTitle = title { "Основные компоненты скриптинга" }
    slide {
        +componentsTitle
    }
    slide {
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
        +unorderedListOf(
            listOf(
                "Compilation configuration",
                "Evaluation configuration",
            )
        )
    }

    slide {
        +smallTitle { "Базовый пример" }
        +smallTitle { "Script Definition" }
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
            +unorderedListOf(listOf(
                "Зависимости",
                "Импорты по умолчанию",
                "Определение неявных (implicit) ресиверов",
                "Конфигурация IDE",
                "Параметры компилятора Kotlin"
            ))
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
            +smallTitle { "Внешние зависимости" }
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

        slide {
            +compilationTitle
            +smallTitle { "Внешние зависимости" }
            +kotlinCode {
                """
                    fun configureMavenDepsOnAnnotations(
                        context: ScriptConfigurationRefinementContext
                    ): ResultWithDiagnostics<ScriptCompilationConfiguration> {
                        val annotations = context.collectedData
                            ?.get(ScriptCollectedData.collectedAnnotations)
                            ?.takeIf { it.isNotEmpty() }
                            ?: return context.compilationConfiguration.asSuccess()
                        return runBlocking {
                            resolver.resolveFromScriptSourceAnnotations(annotations)
                        }.onSuccess {
                            context.compilationConfiguration.with {
                                dependencies.append(JvmDependency(it))
                            }.asSuccess()
                        }
                    }
    
                    private val resolver = CompoundDependenciesResolver(
                        FileSystemDependenciesResolver(),
                        MavenDependenciesResolver()
                    )
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
            +unorderedListOf(listOf(
                "Параметры запуска JVM",
                "Подкидывание созданных экземпляров implicit ресиверов",
                "Аргументы коструктора для базового класса скрипта (MyShinyKtScript)",
                "Возможность разделения инстансов скрипта",
                "Просмотр истории запусков (для REPL)",
                "Возможность переопределения любых частей скрипта"
            ))
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
    }

    slide {
        +title { "Kotlin Script VS JSR223" }
    }
}