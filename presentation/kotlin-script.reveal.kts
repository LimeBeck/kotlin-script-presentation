#!/usr/bin/env -S jbang revealkt@limebeck.dev run

import dev.limebeck.revealkt.core.RevealKt
import dev.limebeck.revealkt.utils.ID
import dev.limebeck.revealkt.utils.UuidGenerator
import kotlinx.html.br
import qrcode.color.Colors

title = "Kotlin Script: для кого, зачем и как"

fun kotlinCode(
    id: ID = UuidGenerator.generateId(),
    lines: String? = null,
    block: () -> String
) = Code(id = id, lang = "kotlin", lines = lines, codeProvider = block)

fun qr(data: String) = qrCode(data) {
    stretch = true
    transformBuilder {
        val logo = loadAsset("logo2.png")
        it.withSize(20)
            .withColor(Colors.css("#B125EA"))
            .withLogo(logo, 150, 150, clearLogoArea = true)
    }
}

val `$` = "$"

configuration {
    theme = RevealKt.Configuration.Theme.Predefined.BLACK
    additionalCssStyle = loadAsset("additional.css").decodeToString()
}

slides {
    verticalSlide {
        slide {
            +title { "Kotlin Script" }
            +title { "для кого, зачем и как" }
        }
    }

    verticalSlide {
        regularSlide {
            +mediumTitle { "Кто я такой?" }
            +row {
                column {
                    +img(src = "me.jpg") {
                        height = 400
                    }
                }
                column {
                    +unorderedListOf(
                        listOf(
                            "Техлид JVM Backend в Банке Центр-инвест",
                            "Пишу на Kotlin больше 5 лет",
                            "Фанат Kotlin",
                            "Написал эту презентацию на Kotlin"
                        ),
                        fragmented = false
                    )
                }
            }
        }
        slide {
            +title { "Тут вступление и т.д." }
        }
    }

    verticalSlide {
        slide {
            +smallTitle { "Примеры использования" }
        }

        slide {
            +smallTitle { "build.gradle.kts" }
            +kotlinCode {
                loadAsset("examples/build.gradle.kts").decodeToString()
            }
        }

        slide {
            +smallTitle { "Простое CLI приложение" }
            +kotlinCode {
                loadAsset("test.main.kts").decodeToString()
            }
        }

//        slide {
//            +smallTitle { "Jetbrains Teamcity CI/CD DSL" }
//            +kotlinCode {
//                loadAsset("examples/teamcity.settings.kts").decodeToString()
//            }
//        }

        slide {
            +smallTitle { "Jetbrains Space CI/CD DSL" }
            +kotlinCode {
                loadAsset("examples/example.space.kts").decodeToString()
            }
        }

        slide {
            +smallTitle { "Github Workflows Kt" }
            +kotlinCode {
                loadAsset("examples/github.main.kts").decodeToString()
            }
        }

        slide {
            +smallTitle { "Live-Plugin" }
            +smallTitle { "(простые плагины для IDEA)" }
            +kotlinCode(id = ID("live-plugin-code")) {
                loadAsset("examples/liveplugin.kts").decodeToString()
            }
        }

        slide {
            +smallTitle { "Презентации с Reveal-Kt" }
            +kotlinCode {
                loadAsset("examples/example.reveal.kts").decodeToString()
            }
        }
    }

    verticalSlide {
        slide {
            +smallTitle { "Позиционирование Kotlin Scripting От Jetbrains" }
        }

        slide {
            +title { "KEEP: Kotlin Scripting support" }
            +smallTitle { "Applications" }
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
                    
                    Объяснить, что это прямая цитата из KEEP Jetbrains и что он не менялся 3 года
                """.trimIndent()
            }
        }
        slide {
            +smallTitle { "Обобщим" }
            +unorderedListOf(
                "замена BASH-скриптов",
                "Read-Eval-Print Loop (REPL)",
                "компиляция скриптов с исходниками",
                "встраивание скриптового движка",
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

    //REPL
    verticalSlide {
        val replTitle = smallTitle { "Read-Eval-Print Loop (REPL)" }
        val why = smallTitle { "Зачем он нужен?" }
        slide {
            +replTitle
        }
        slide {
            +replTitle
            +unorderedListOf(
                "Читает (парсит)",
                "Исполняет",
                "Выводит ответ",
                "И снова",
                fragmented = false
            )
        }
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
            +smallTitle { "Альтернативы" }
        }
        slide {
            +replTitle
            +smallTitle { "JShell" }
            +code(lang = "java") {
                """
                    > jshell 
                    |  Welcome to JShell -- Version 17.0.4.1
                    |  For an introduction type: /help intro
                    
                    jshell> var hello = "Hello, world!"
                    hello ==> "Hello, world!"
                    
                    jshell> System.out.println(hello)
                    Hello, world!
                """.trimIndent()
            }
            +note {
                """
                    +Встроен в jdk, -нет зависимостей
                """.trimIndent()
            }
        }
        slide {
            +replTitle
            +smallTitle { "Groovy Shell" }
            +code(lang = "groovy") {
                """
                    > groovysh
                    Groovy Shell (4.0.20, JVM: 17.0.4.1)
                    Type ':help' or ':h' for help.
                    -------------------------------------------------
                    groovy:000> hello = 'Hello, World!'
                    ===> Hello, World!
                    groovy:000> println hello
                    Hello, World!
                    ===> null
                """.trimIndent()
            }
            +note {
                """
                    +есть зависимости
                    +проще писать благодаря Groovy
                    -нужно тянуть Groovy
                """.trimIndent()
            }
        }
        slide {
            +replTitle
            +smallTitle { "Kotlin SHELL" }
            +kotlinCode {
                """
                    > kotlin
                    Welcome to Kotlin version 1.9.22 (JRE 17.0.4.1+1-LTS)
                    Type :help for help, :quit for quit
                    >>> val hello = "Hello, World!"
                    >>> println(hello)
                    Hello, World!
                """.trimIndent()
            }
        }
        slide {
            +replTitle
            +smallTitle { "IJ IDEA Kotlin REPL" }
            +img("REPL.png") {
                stretch = true
            }
            +HtmlDslElement {
                br {  }
            }
            +regular { "IJ IDEA > Tools > Kotlin > Kotlin REPL (Experimental)" }
            +note {
                """
                    Зачем нужен REPL: максимально быстро получить обратную связь по коду
                """.trimIndent()
            }
        }
    }

    //JUPYTER
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
                    Jupyter-ноутбук — это среда разработки, где сразу можно видеть 
                    результат выполнения кода и его отдельных фрагментов.
                    Упомянуть про доклад Нозика
                """.trimIndent()
            }
        }
        slide {
            +img("./kotlin-notebooks-conf.png") {
                stretch = true
            }
        }
    }

    //BASH
    verticalSlide {
        val autoTitle = smallTitle { "Замена BASH-скриптов в автоматизации задач" }
        slide {
            +autoTitle
        }

        val why = smallTitle { "Зачем заменять BASH?" }
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
            +regular("Bash")
            +code(lang = "bash") {
                """
                    # Нумеруем строки в файле
                    sed = a.txt | sed 'N; s/^/ /; s/ *\(.\{4,\}\)\n/\1 /'
                """.trimIndent()
            } // сразу тут пример на котлине + массив файлов
            +regular("Kotlin")
            +kotlinCode {
                """
                    import java.io.File

                    File("a.txt").useLines { 
                        it.forEachIndexed { i, l -> println("$`$`i: $`$`l") } 
                    }
                """.trimIndent()
            }
        }
        slide {
            +why
            +unorderedListOf(
                "Сложные скрипты на Bash - боль",
                "Bash - write-only код",
                "Зависимости на Bash?",
            )
            +thatsWhyNote
        }

        slide {
            +smallTitle { "Почему именно Kotlin Script" }
            +unorderedListOf(
                "Богатая экосистема JVM",
                "Зависимости не в системе",
                "Удобство Kotlin DSL",
                "Типобезопасность на уровне компиляции",
            )
        }

        slide {
            +smallTitle { "Альтернативы" }
            +unorderedListOf(
                "Java 22",
                "Groovy",
                "JBang (Java, Kotlin, Groovy)",
                "KScript",
            )
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
                "Подключение репозиториев и библиотек",
                "Конфигурация комплятора в самом скрипте",
                "Кэширование между запусками",
                "Удобная работа \"из коробки\"",
            )
        }
        slide {
            +mainKtsTitle
            +kotlinCode(lines = "|2|4,7-10|11-15") {
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

    //Embed
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
            +smallTitle { "конфигурация через" }
            +smallTitle { "\"Typesafe DSL\"" }
            +kotlinCode {
                loadAsset("examples/build.gradle.kts").decodeToString()
            }
        }
        slide {
            +smallTitle { "конфигурация через" }
            +smallTitle { "\"Typesafe DSL\"" }
            +kotlinCode {
                loadAsset("examples/example.reveal.kts").decodeToString()
            }
        }

        slide {
            +smallTitle { "микроядерная архитектура" }
            +smallTitle { "(плагинная архитектура)" }
            +img("./plugin-arch.png") {
                stretch = true
            }
        }

        slide {
            +smallTitle { "кастомизация действий пользователем" }
            +img("./workflow-engine.png") {
                stretch = true
            }
        }

        slide {
            +smallTitle { "кастомизация действий пользователем" }
            +regular("FIND USER")
            +kotlinCode {
                """
                    val userId = ctx["USER_ID"] as? String 
                        ?: throw NoSuchPropertyException("USER_ID was not found")
                    
                    ctx["USER_DATA"] = ctx.withDatabaseConnectionOf("USERS_DB") { db ->
                        db.select()
                            .from(UsersTable)
                            .where(UsersTable.ID eq userId)
                            .fetchOne { r ->
                                User(
                                    id = r[UsersTable.ID], 
                                    name = r[UsersTable.NAME]
                                 )
                            }
                    }
                """.trimIndent()
            }
        }
    }

    //JSR223
//    verticalSlide {
//        slide {
//            +mediumTitle { "Java Scripting API" }
//            +mediumTitle { "(JSR223)" }
//        }
//        slide {
//            +mediumTitle { "Абстракция в JVM для исполнения скриптов" }
//        }
//        slide {
//            +mediumTitle { "Kotlin Script > JSR223" }
//        }
//        slide {
//            +mediumTitle { "Kotlin Script" }
//            +unorderedListOf(
//                "Компиляция",
//                "Исполнение",
//                "Работа с IDE"
//            )
//        }
//    }

    verticalSlide {
        slide {
            +smallTitle { "Практика" }
        }
        slide {
            +smallTitle { "Gitlab-CI.kts" }
            +kotlinCode {
                """
                    listOf("dev-0", "dev-1", "dev-2").forEach {
                        job("build-$`$`it") {
                            image = Image.of("alpine:latest")
                            script("echo 'Hello World from $`$`it'")
                        }
                        job("deploy-$`$`it") {
                            image = Image.of("alpine:latest")
                            script("echo Deployed $`$`it")
                        }
                    }
                """.trimIndent()
            }
        }
    }
    verticalSlide {
        val componentsTitle = title { "Основные компоненты скриптинга" }
        slide {
            +componentsTitle
        }
        slide {
            +componentsTitle
            +img("./scripting-components.svg") {
                stretch = true
            }
        }
        slide {//Добавить схему
            +componentsTitle
            +unorderedListOf(
                listOf(
                    "Описание скрипта - Script Definition", //Визуализация и ассоциация
                    "Исполнение скрипта - Scripting Host",
                )
            )
        }
    }

    verticalSlide {
        slide {
            +smallTitle { "Описание скрипта" }
            +smallTitle { "(Script Definition)" }
        }
        slide {
            +smallTitle { "Script Definition" }
            +img("./img_1.png") {
                stretch = true
            }
            //TODO: Host configuration
        }
        val baseExampleTitle = smallTitle { "Базовый пример" }
        val gitlabCiId = ID("gitlab-ci.kts")
        slide {
            +baseExampleTitle
            +kotlinCode(gitlabCiId, lines = "|1,4-6|2|3|") {
                """
                    @KotlinScript(
                        fileExtension = "gitlab-ci.kts",
                        displayName = "Gitlab CI Kotlin configuration",
                    )
                    abstract class GitlabCiKtScript
                    //Класс обязательно должен быть открытым или абстрактным
                """.trimIndent()
            }
        }
        slide {
            +baseExampleTitle
            +kotlinCode(gitlabCiId, lines = "") {
                """
                    @KotlinScript(
                        fileExtension = "gitlab-ci.kts",
                        displayName = "Gitlab CI Kotlin configuration",
                    )
                    abstract class GitlabCiKtScript {
                        val myProperty = 123
                    }
                """.trimIndent()
            }
        }
        slide {
            +baseExampleTitle
            +kotlinCode {
                """
                    println(myProperty) 
                    //123
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

        slide {
            +compilationTitle
            +exampleTitle
            +kotlinCode(
                lines = "|2|3-8|9|10"
            ) {
                """
                    object GitlabCiKtScriptCompilationConfiguration : ScriptCompilationConfiguration({
                        jvm { dependenciesFromClassContext(PipelineBuilder::class, wholeClasspath = true) }
                        defaultImports(DependsOn::class, Repository::class)
                        defaultImports(
                            "dev.otbe.gitlab.ci.core.model.*",
                            "dev.otbe.gitlab.ci.dsl.*",
                            "dev.otbe.gitlab.ci.core.goesTo"
                        )
                        implicitReceivers(PipelineBuilder::class)
                        ide { acceptedLocations(ScriptAcceptedLocation.Everywhere) }
                    })
                """.trimIndent()
            }
        }
        slide {
            +compilationTitle
            +exampleTitle
            +regular("PipelineBuilder.kt") //TODO: Написать свой DSL - будет проще с примерами
            +kotlinCode {
                """
                    class PipelineBuilder {
                        private var stages: List<Stage> = emptyList()
                        fun stages(vararg stage: Stage) {
                            stages = stage.asList()
                        }
                    }
                """.trimIndent()
            }
            +regular("example.shiny.kts")
            +kotlinCode {
                """
                    //this: MyShinyBuilder
                    shinyProperty = "Hello from my script"
                    shinyPrint()
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
        slide {
            +compilationTitle
            +smallTitle { "Внешние зависимости" } //Добавить пример
            +regular("example.shiny.kts")
            +kotlinCode {
                """
                    @file:Repository("https://repo1.maven.org/maven2/") //По умолчанию уже подключен
                    @file:DependsOn("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.6")

                    import kotlinx.cli.ArgParser
                    import kotlinx.cli.ArgType
                    import kotlinx.cli.default
                    import kotlinx.cli.required

                    val parser = ArgParser("example")
                    val input by parser.option(ArgType.String, shortName = "i",
                        description = "Input file").required()
                    val debug by parser.option(ArgType.Boolean, shortName = "d",
                        description = "Turn on debug mode").default(false)
                    parser.parse(args)
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
                            createJvmCompilationConfigurationFromTemplate<MyShinyScript> {}
                        val evaluationConfiguration = 
                            createJvmEvaluationConfigurationFromTemplate<MyShinyScript> {}
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
        val securityTitle = smallTitle { "Безопасность в Kotlin Script" }
        slide {
            +securityTitle
        }
        slide {
            +securityTitle
            +unorderedListOf(
                "Ограничение по модулям JVM",
                "Ограничение по доступным классам"
            )
            //Безопасности нет - раскрыть
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
                "Компилятор Kotlin в приложении",
                "Слабая поддержка в IDE",
                "Мало документации",
                "Только на JVM",
                "Долгий старт",
                "Общая сырость, баги",
                "Проблемы с применением плагинов компилятора"
            )
            +note {
                """
                    Привести примеры - не работает подсветка c @import
                    Проблемы с необходимостью проекта gradle для подсветки
                """.trimIndent()
            }
        }
    }

    //Выводы

    slide {
        +title { "Ссылка на презентацию и полезные штуки" }
        +qr("https://github.com/LimeBeck/kotlin-script-presentation")
    }
}