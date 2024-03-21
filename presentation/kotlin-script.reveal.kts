#!/usr/bin/env -S revealkt run

import dev.limebeck.revealkt.core.RevealKt
import qrcode.color.Colors

title = "Kotlin Script: для кого, зачем и как"

fun kotlinCode(block: () -> String) = code(lang = "kotlin", block = block)

configuration {
    theme = RevealKt.Configuration.Theme.Predefined.BLACK
    additionalCssStyle = loadAsset("additional.css").decodeToString()
}

slides {
    verticalSlide {
        slide {
            +title { "Kotlin Script" }
            +title { "кому, зачем и как" }
        }
        val warning = title { "WARNING" }.apply {
            addStyle("color", "red")
        }
        slide {
            +warning
        }
        slide {
            +warning
            +title { "Kotlin Script еще βeta" }
        }
    }

    verticalSlide {
        slide {
            +title { "Тут вступление и т.д." }
        }
    }

    verticalSlide {
        slide {
            +title { "Позиционирование Kotlin Scripting" }
            +smallTitle { "От Jetbrains" }
        }
        slide {
            +title { "KEEP: Kotlin Scripting support" }
            +unorderedListOf(
                "Build scripts (Gradle/Kobalt)",
                "Test scripts (Spek)",
                "Command-line utilities",
                "Routing scripts (ktor)",
                "Type-safe configuration files (TeamCity)",
                "In-process scripting and REPL for IDE",
                "Consoles like IPython/Jupyter Notebook",
                "Game scripting engines",
                "...",
                fragmented = false
            )
            +note {
                """
                    Стоит заметить, что часть этих задумок еще не реализована
                    В частности - скрипты, которые компилируются с исходниками.
                    
                    Я так и не смог найти ни одного адекватного примера скриптов, которые
                    бы компилировались вместе с иходниками
                    
                    Объяснить, что это прямая цитата из KEEP Jetbrains
                    
                    Не менялся 3 года
                """.trimIndent()
            }
        }
        slide {
            +smallTitle { "Обобщим" }
            +unorderedListOf(
                "Read-Eval-Print Loop (REPL)",
                "замена BASH-скриптов",
                "встраивание скриптового движка",
                "компиляция скриптов с исходниками", //Нужно перефразировать и объяснить
                fragmented = false
            )
            +note {
                """
                    Называть причину по каждому пункту
                    К REPL можно отнести и Jupyter Kotlin
                """.trimIndent()
            }
        }
    }

    verticalSlide {
        val replTitle = smallTitle { "Read-Eval-Print Loop (REPL)" }
        val why = smallTitle { "Зачем он нужен?" }
        slide {
            +replTitle
            +why
        }
        slide {
            +why
            +unorderedListOf(
                "Обучение основам",
                "Прототипирование",
                "Быстрая обратная связь",
            )
        }
        slide {
            +replTitle
            +regular { "IDEA > Tools > Kotlin > Kotlin REPL (Experimental)" }
            +img("REPL.png") //Укрупнить
            +note {
                """
                Зачем нужен REPL: максимально быстро получить обратную связь
                по коду
            """.trimIndent()
            }
        }
        slide {
            +replTitle
            +regular { "SHELL" }
            +img("repl_shell.png")
            +note {
                """
                Зачем нужен REPL: максимально быстро получить обратную связь
                по коду
            """.trimIndent()
            }
        }
    }

    verticalSlide {
        val jupyterTitle = smallTitle { "Kotlin для Jupiter Notebook" }
        slide {
            +jupyterTitle
        }
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
            +code(lang = "bash") {
                """
                    # Нумеруем строки в файле
                    sed = a.txt | sed 'N; s/^/ /; s/ *\(.\{4,\}\)\n/\1 /'
                """.trimIndent()
            }
        }
        slide {
            +why
            +unorderedListOf(
                "Сложные скрипты на Bash - боль",
                "Управлять зависимостями Python - тоже боль",
                "Экосистема JVM",
                "Удобство Kotlin",
                "Типобезопасность на уровне компиляции"
            )
            +thatsWhyNote
            //TODO: Добавить пример на awk/sed + ci/cd
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
            +unorderedListOf(
                "Подключение сторонних библиотек",
                "Конфигурация комплятора в самом скрипте",
                "Удобная работа \"из коробки\"",
            )
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
            +unorderedListOf(
                "конфигурация через \"Typesafe DSL\"",
                "микроядерная архитектура (плагины)",
                "кастомизация действий пользователем"
            )
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
                    "Script Definition", //Associate this
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
                "Зависимости",
                "Импорты по умолчанию",
                "Конфигурация IDE",
                "Параметры компилятора Kotlin",
                "Определение неявных (implicit) ресиверов",
            )
        }

        val implRecTitle = smallTitle { "Receivers" }
        slide {
            +implRecTitle
            +note {
                """
                    Небольшое отступление, рассказ, что такое receivers,
                    чем implicit отличается от explicit
                    Receiver - понятие из классического ООП с определением как обмен сообщениями
                    Receiver - получатель
                    Sender - отправитель
                    Т.е., в нашем случае - Sender вызывает метод у Receiver
                """.trimIndent()
            }
        }
        slide {
            +implRecTitle
            +kotlinCode {
                """
                    class MyClass {
                        fun doSomething() = TODO()
                        
                        fun explicitReceiver() {
                            this.doSomething()
                        }
                        
                        fun implicitReceiver() {
                            doSomething()
                        }
                    }
                """.trimIndent()
            }
            +note {
                """
                    Explicit - когда мы явно указываем получателя (т.е. в этом случае - this)
                    Implicit - неявно, просто вызываем метод, опуская this
                """.trimIndent()
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
                "Параметры запуска JVM",
                "Подкидывание созданных экземпляров implicit ресиверов",
                "Аргументы коструктора для базового класса скрипта (MyShinyKtScript)",
                "Возможность разделения инстансов скрипта",
                "Просмотр истории запусков (для REPL)",
                "Возможность переопределения любых частей скрипта"
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
        slide {
            +ideTitle
            +regular("META-INF/kotlin/script/templates/\ndev.limebeck.scripts.MyShinyScript.classname")
        }
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
            +smallTitle { "Работает только в Intellij IDEA" }
        }
    }

    verticalSlide {
        //TODO: куда поставить?
        val jsrVsKotlinTitle = title { "Kotlin Script VS JSR223" }
        slide {
            +jsrVsKotlinTitle
        }

        val jsrTitle = smallTitle { "JSR223" }
        slide {
            +jsrTitle
        }
        slide {
            +jsrTitle
            +smallTitle { "Абстракция в JVM для скриптовых движков" }
        }
        slide {
            +jsrTitle
            +unorderedListOf(
                "Общий интерфейс для исполнения",
                "Даёт общий интерфейс",
                "Даёт общий интерфейс",
            )
        }
    }

    verticalSlide {
        val securityTitle = smallTitle { "Безопасность в Kotlin Script" }
        slide {
            +securityTitle
        }
        slide {
            +securityTitle
            +unorderedListOf(
                "Ограничение по модулям",
                "Ограничение по доступным классам"
            )
        }
    }

    verticalSlide {
        val minusesTitle = smallTitle { "Минусы" }
        slide {
            +minusesTitle
        }
        slide {
            +minusesTitle
            +unorderedListOf(
                "Слабая поддержка в IDE",
                "Мало документации",
                "Только на JVM",
                "Долгий старт",
                "Общая сырость, баги",
            )
            +note {
                """
                    Привести примеры - не работает подсветка в @import
                """.trimIndent()
            }
        }
    }

    slide {
        +title { "Ссылка на презентацию и полезные штуки" }
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