import dev.limebeck.revealkt.core.RevealKt
import qrcode.color.Colors

title = "Kotlin Script: для кого, зачем и как"

fun kotlinCode(block: () -> String) = code(lang = "kotlin", block = block)

configuration {
    controls = false
    progress = true
    theme = RevealKt.Configuration.Theme.Predefined.BLACK
    additionalCssStyle = loadAsset("additional.css").decodeToString()
}

slides {
    verticalSlide {
        slide {
            +title { "Тут вступление и т.д." }
        }
    }

    verticalSlide {
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
                    "Test scripts (Spek)", //Not implemented now
                    "Command-line utilities",
                    "Routing scripts (ktor)", //Not implemented now
                    "Type-safe configuration files (TeamCity)",
                    "In-process scripting and REPL for IDE",
                    "Consoles like IPython/Jupyter Notebook",
                    "Game scripting engines",
                    "..."
                )
            )
            +note {
                """
                    Стоит заметить, что часть этих задумок еще не реализована
                    В частности - скрипты, которые компилируются с исходниками.
                    
                    Я так и не смог найти ни одного адекватного примера скриптов, которые
                    бы компилировались вместе с иходниками
                """.trimIndent()
            }
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
            +note {
                """                    
                    К REPL можно отнести и Jupyter Kotlin
                """.trimIndent()
            }
        }
    }

    slide {
        +smallTitle { "REPL" }
        +regular { "Tools > Kotlin > Kotlin REPL (Experimental)" }
        +img("REPL.png")
        +note {
            """
                Зачем нужен REPL: максимально быстро получить обратную связь
                по коду
            """.trimIndent()
        }
    }

    verticalSlide {
        val jupyterTitle = smallTitle { "Kotlin для Jupiter Notebook" }
        slide {
            +jupyterTitle
            +img("./jupyter.png") {
                stretch = true
            }
            +note {
                """
                    Думаю, не нужно объяснять, что такое ноутбуки для DS
                """.trimIndent()
            }
        }
    }

    verticalSlide {
        val autoTitle = smallTitle { "замена BASH-скриптов в автоматизации задач" }
        slide {
            +autoTitle
        }

        val why = smallTitle { "Зачем?" }
        val thatsWhyNote = note {
            """
                Рассказать, какая боль - писать скрипты на баше.
                Особенно, если в скриптах нужно например сходить за данными в БД
                или по сети и после как-то преобразовать их.
                Ну и typesafe, когда скрипт не запустится, пока не напишешь нормально
            """.trimIndent()
        }
        slide {
            +why
            +thatsWhyNote
        }
        slide {
            +why
            +unorderedListOf(listOf(
                "Сложные скрипты на Bash - боль",
                "Управлять зависимостями Python - тоже боль",
                "Хочется писать на Kotlin",
                "Типобезопасность на уровне компиляции"
            ))
            +thatsWhyNote
        }

        slide {
            +autoTitle
            +smallTitle { "Скрипты .kts" }
            +kotlinCode {
                //language=kotlin
                """
                    #!/usr/bin/env kotlin
                    
                    import java.io.File
                    
                    fun complexProcess(text: String): String = TODO()
                    
                    val file = File("path/to/my.file")
                    val text = file.readText()
                    val newText = complexProcess(text)
                    file.writeText(newText)
                """.trimIndent()
            }
            +note {
                """
                    Но встроенных в Kotlin библиотек недостаточно (по сравнению с тем же Python).
                    Тогда на помощь приходят скрипты .main.kts, куда можно добавить любые JVM библиотеки
                """.trimIndent()
            }
        }

        val mainKtsTitle = smallTitle { "Скрипты .main.kts" }
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
    }

    verticalSlide {
        val scriptEngineTitle = smallTitle { "Встраивание скриптового движка в приложение" }
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
    }

    verticalSlide {
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
    }

    verticalSlide {
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
            note {
                "Нужно ли рассказывать про то, что такое implicit receivers?"
            }
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
            +smallTitle { "\"Evaluate and run\"" }
        }
        slide {
            +loaderTitle
            +kotlinCode {
                """
                    fun BasicJvmScriptingHost.evalFile(
                        scriptFile: File
                    ): ResultWithDiagnostics<EvaluationResult> {
                        val compilationConfiguration = 
                            createJvmCompilationConfigurationFromTemplate<MyShinyScript> { 
                                //Тут мы можем дополнить и переопределить конфигурацию при необходимости
                            }
                        val evaluationConfiguration = 
                            createJvmEvaluationConfigurationFromTemplate<MyShinyScript> {
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

    verticalSlide {
        val ideTitle = smallTitle { "Подсветка в IDE" }
        slide {
            +ideTitle
        }
        slide {
            +ideTitle
            +regular("Пустой файл в META-INF/kotlin/script/templates с полным именем класса в названии")
        }
//        slide {
//            +ideTitle
//            +regular("META-INF/kotlin/script/templates/\ndev.limebeck.scripts.MyShinyScript.classname")
//        }
        slide { //TODO: Заменить скрин на MyShinyScript
            +ideTitle
            +img("IDE_scriptDefPath.png") {
                stretch = true
            }
        }
        slide { //TODO Заменить скрин на MyShinyScript
            +ideTitle
            +img("IDE.png") {
                stretch = true
            }
        }
        slide {
            +ideTitle
            +smallTitle { "Но только в Intellij IDEA" }
        }
    }

    verticalSlide {
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