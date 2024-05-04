#!/usr/bin/env -S jbang revealkt@limebeck.dev run

import dev.limebeck.revealkt.core.RevealKt
import dev.limebeck.revealkt.utils.ID
import dev.limebeck.revealkt.utils.UuidGenerator
import kotlinx.html.*
import qrcode.color.Colors

title = "Kotlin Script: для кого, зачем и как"

configuration {
    touch = true
    slideNumber = RevealKt.Configuration.SlideNumber.Enable
    showSlideNumber = RevealKt.Configuration.ShowSlideNumber.ALL
    controlsLayout = RevealKt.Configuration.ControlsLayout.EDGES
    theme = RevealKt.Configuration.Theme.Predefined.BLACK
    additionalCssStyle = loadAsset("additional.css").decodeToString()
    controls = true
    controlsTutorial = true
}

meta {
    language = "ru"
}

val `$` = "$"

slides {
    verticalSlide {
        slide {
            +title { "Kotlin Script" }
            +title { "для кого, зачем и как" }
        }
        regularSlide {
            +smallTitle { "Обо мне" }
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
                        ),
                        fragmented = false
                    )
                }
            }
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

//        slide {
//            +smallTitle { "JetBrains Teamcity CI/CD DSL" }
//            +kotlinCode {
//                loadAsset("examples/teamcity.settings.kts").decodeToString()
//            }
//        }

        slide {
            +smallTitle { "JetBrains Space CI/CD DSL" }
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
            +smallTitle { "Простое CLI приложение" }
            +kotlinCode {
                loadAsset("test.main.kts").decodeToString()
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
            +smallTitle { "Позиционирование Kotlin Scripting От JetBrains" }
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
                fragmented = true
            )
            +note {
                """
                    Стоит заметить, что часть этих задумок еще не реализована
                    В частности - скрипты, которые компилируются с исходниками.
                    
                    Я так и не смог найти ни одного адекватного примера скриптов, которые
                    бы компилировались вместе с иходниками
                    
                    Объяснить, что это прямая цитата из KEEP JetBrains и что он не менялся 3 года
                """.trimIndent()
            }
        }
        slide {
            +smallTitle { "Обобщим" }
            +unorderedListOf(
                regular { "Read-Eval-Print Loop (REPL)" },
                regular { "замена BASH-скриптов" },
                regular { "встраивание скриптового движка" },
                regular { "компиляция скриптов с исходниками" },
                fragmented = false
            )
            +note {
                """
                    Называть причину по каждому пункту
                    К REPL можно отнести и Jupyter Kotlin
                """.trimIndent()
            }
        }
        slide {
            +smallTitle { "Обобщим" }
            +unorderedListOf(
                regular { "Read-Eval-Print Loop (REPL)" },
                regular { "замена BASH-скриптов" },
                regular { "встраивание скриптового движка" },
                strike { "компиляция скриптов с исходниками" },
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
                    +Встроен в jdk 
                    -нет зависимостей
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
                    -магия Groovy
                """.trimIndent()
            }
        }
        slide {
            +replTitle
            +smallTitle { "KScript (REPL mode)" }
            +kotlinCode {
                """
                    > kscript --interactive kscript.kts
                    [kscript] Resolving com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0...
                    [kscript] Creating REPL
                    Welcome to Kotlin version 1.9.22 (JRE 22+37)
                    Type :help for help, :quit for quit
                    >>> println("Hello, World!")
                    Hello, World!
                    >>> 
                """.trimIndent()
            }
            +regular { "kscript.kts" }
            +kotlinCode {
                """
                    @file:DependsOn("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
                """.trimIndent()
            }
        }
        slide {
            +smallTitle { "Kotlin REPL" }
        }
        slide {
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
            +smallTitle { "IJ IDEA Kotlin REPL" }
            +img("REPL.png") {
                stretch = true
            }
            +HtmlDslElement {
                br { }
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
        val jupyterTitle = smallTitle { "Kotlin для Jupyter Notebook" }
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
    val autoTitle = smallTitle { "Замена BASH-скриптов в автоматизации задач" }
    verticalSlide {
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
        }

        slide {
            +smallTitle { "Java 11 (JEP 330)" }
            +code(lang = "java") {
                loadAsset("examples/java/11/Prog.java").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./Prog.java
                    Hello!
                """.trimIndent()
            }
        }

        slide {
            +smallTitle { "Shebang обычный" }
            +code(lang = "java") {
                """
                    #!/usr/bin/env java
                """.trimIndent()
            }
            +smallTitle { "Shebang для .java" }
            +code(lang = "bash") {
                """
                    ///usr/bin/env java "$`$`0" "$`$`@" ; exit $`$`?
                """.trimIndent()
            }
        }

        slide {
            +smallTitle { "Java 22 (JEP 458)" }
            +code(lang = "java") {
                loadAsset("examples/java/22/Prog.java").decodeToString()
            }
            +code(lang = "java") {
                loadAsset("examples/java/22/Helper.java").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./Prog.java
                    Hello!
                """.trimIndent()
            }
        }

        slide {
            +smallTitle { "Java 22 (+ JEP 463)" }
            +code(lang = "java") {
                loadAsset("examples/java/22/ProgMain.java").decodeToString()
            }
            +code(lang = "java") {
                loadAsset("examples/java/22/Helper.java").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./ProgMain.java
                    Hello!
                """.trimIndent()
            }
        }

        slide {
            +smallTitle { "Groovy" }
            +code(lang = "groovy") {
                loadAsset("examples/ScriptWithDeps.groovy").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./ScriptWithDeps.groovy
                    Hello, World!
                """.trimIndent()
            }
        }

        slide {
            +smallTitle { "JBang (java)" }
            +code(lang = "java") {
                loadAsset("examples/jbang/JBangEx.java").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./JBangEx.java
                    [jbang] Resolving dependencies...
                    [jbang]    com.fasterxml.jackson.core:jackson-databind:2.17.0
                    [jbang] Dependencies resolved
                    [jbang] Building jar for JBangEx.java...
                    Hello, World!
                """.trimIndent()
            }
        }
        slide {
            +smallTitle { "JBang (Groovy)" }
            +code(lang = "groovy") {
                loadAsset("examples/jbang/JBangEx.groovy").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./JBangEx.groovy 
                    [jbang] Downloading Groovy 4.0.14. Be patient, this can take several minutes...
                    [jbang] Installing Groovy 4.0.14...
                    [jbang] Resolving dependencies...
                    [jbang]    com.fasterxml.jackson.core:jackson-databind:2.17.0
                    [jbang]    org.apache.groovy:groovy:4.0.14
                    [jbang] Dependencies resolved
                    [jbang] Building jar for JBangEx.groovy...
                    Hello, World!
                """.trimIndent()
            }
        }
        slide {
            +smallTitle { "JBang (Kotlin)" }
            +code(lang = "kotlin") {
                loadAsset("examples/jbang/JBangEx.kt").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./JBangEx.kt 
                    [jbang] Downloading Kotlin 1.8.22. Be patient, this can take several minutes...
                    [jbang] Installing Kotlin 1.8.22...
                    [jbang] Resolving dependencies...
                    [jbang]    com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0
                    [jbang] Dependencies resolved
                    [jbang] Building jar for JBangEx.kt...
                    Hello, World! 
                """.trimIndent()
            }
        }

        slide {
            +smallTitle { "KScript" }
            +code(lang = "kotlin") {
                loadAsset("examples/kscript.kts").decodeToString()
            }
            +code(lang = "bash") {
                """
                    > ./kscript.kts 
                    [kscript] Resolving com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0...
                    Hello, World!
                """.trimIndent()
            }
        }
    }

    verticalSlide {
        slide {
            +autoTitle
            +smallTitle { "Kotlin Script" }
        }
        slide {
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
                "Поддержка в IDE \"из коробки\"",
                fragmented = false
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
            +smallTitle { "плагинная архитектура" }
            +img("./microkernel-arch.svg") {
                stretch = true
            }
        }

        slide {
            +smallTitle { "кастомизация действий пользователем" }
            +img("./workflow-engine.svg") {
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

    verticalSlide {
        slide {
            +smallTitle { "Практика" }
        }
        slide {
            +smallTitle { "Gitlab-CI.kts" }
            +kotlinCode {
                """
                    val build = job("build") {
                        stage = Stage("build")
                        image = Image.of("alpine:latest")
                        script("echo 'Hello World from build'")
                    }
                    listOf("dev-0", "dev-1", "dev-2").forEach {
                        job("deploy-$`$`it") {
                            stage = Stage("deploy")
                            needs(build)
                            image = Image.of("alpine:latest")
                            script("echo Deployed $`$`it")
                        }
                    }
                """.trimIndent()
            }
            +code(lang = "bash") {
                """
                    > java -jar gitlab-cli.jar ./main.gitlab-ci.kts > .gitlab-ci.yaml
                """.trimIndent()
            }
        }
        slide {
            +regular { ".gitlab-ci.yaml" }
            +code(lang = "yaml") {
                """
                    stages:
                      - build
                      - deploy
                    build:
                      stage: build
                      image:
                        name: alpine:latest
                      script:
                        - echo 'Hello World from build'
                    deploy-dev-0:
                      stage: deploy
                      image:
                        name: alpine:latest
                      needs:
                        - build
                      script:
                        - echo Deployed dev-0
                    deploy-dev-1:
                      stage: deploy
                      image:
                        name: alpine:latest
                      needs:
                        - build
                      script:
                        - echo Deployed dev-1
                    deploy-dev-2:
                      stage: deploy
                      image:
                        name: alpine:latest
                      needs:
                        - build
                      script:
                        - echo Deployed dev-2
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
            +img("scripting-host.svg") {
                stretch = true
            }
        }
        slide {
            +componentsTitle
            +unorderedListOf(
                "Описание скрипта - Script Definition",
                "Исполнение скрипта - Scripting Host",
                fragmented = false
            )
        }
    }

    verticalSlide {
        slide {
            +smallTitle { "Script Definition" }
            +img("scripting-configuration.svg") {
                stretch = true
            }
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
                "Доступные в скрипте свойства",
                "Определение неявных (implicit) ресиверов",
            )
        }

        slide {
            +compilationTitle
            +exampleTitle
            +kotlinCode(
                lines = "|2-4|5-9|10|11|12|13"
            ) {
                """
                    object GitlabCiKtScriptCompilationConfiguration : ScriptCompilationConfiguration({
                        jvm { 
                            dependenciesFromClassContext(PipelineBuilder::class, wholeClasspath = true) 
                        }
                        defaultImports(
                            "dev.otbe.gitlab.ci.core.model.*",
                            "dev.otbe.gitlab.ci.dsl.*",
                            "dev.otbe.gitlab.ci.core.goesTo"
                        )
                        ide { acceptedLocations(ScriptAcceptedLocation.Everywhere) }
                        compilerOptions.append("-Xcontext-receivers")
                        providedProperties("propName" to String::class)
                        implicitReceivers(PipelineBuilder::class)
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
                        var stages: List<Stage> = mutableListOf()
                        fun stages(vararg stage: String) {
                            stages += stage.asList().map { Stage(it) }
                        }
                    }
                """.trimIndent()
            }
            +regular("example.gitlab-ci.kts")
            +kotlinCode {
                """
                    //this: PipelineBuilder
                    stages("build", "deploy")
                """.trimIndent()
            }
        }
        slide {
            +compilationTitle
            +exampleTitle
            +kotlinCode(lines = "4") {
                """
                    @KotlinScript(
                        fileExtension = "gitlab-ci.kts",
                        displayName = "Gitlab CI Kotlin configuration",
                        compilationConfiguration = GitlabCiKtScriptCompilationConfiguration::class,
                    )
                    abstract class GitlabCiKtScript
                """.trimIndent()
            }
        }
        val externalDeps = smallTitle { "Внешние зависимости" }
        slide {
            +compilationTitle
            +externalDeps
            +kotlinCode(lines = "2-10") {
                """
                    object GitlabCiKtScriptCompilationConfiguration : ScriptCompilationConfiguration({
                        defaultImports(DependsOn::class, Repository::class)
                        refineConfiguration {
                            onAnnotations(
                                DependsOn::class,
                                Repository::class,
                                // Обработчик аннотаций
                                handler = ::configureMavenDepsOnAnnotations
                            )
                        }
                        
                        jvm { 
                            dependenciesFromClassContext(PipelineBuilder::class, wholeClasspath = true) 
                        }
                        defaultImports(
                            "dev.otbe.gitlab.ci.core.model.*",
                            "dev.otbe.gitlab.ci.dsl.*",
                            "dev.otbe.gitlab.ci.core.goesTo"
                        )
                        ide { acceptedLocations(ScriptAcceptedLocation.Everywhere) }
                        compilerOptions.append("-Xcontext-receivers")
                        providedProperties("propName" to String::class)
                        implicitReceivers(PipelineBuilder::class)
                    }
                """.trimIndent()
            }
        }
        slide {
            +compilationTitle
            +externalDeps
            +regular("example.gitlab-ci.kts")
            +kotlinCode {
                """
                    @file:Repository("https://private-nexus.company.org/ci-templates/")
                    @file:DependsOn("org.company.ci.templates:jvm-jobs:1.0.0")

                    import org.company.ci.templates.jvm.jobs.*
                    
                    val appName = "kotlin-app"
                    gradleJob {
                        task = "build"
                        artifact = "./build/libs/$`$`{appName}.jar"
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
                "Передача созданных экземпляров implicit ресиверов",
                "Аргументы конструктора для базового класса скрипта",
                "Возможность разделения инстансов скрипта",
                "Просмотр истории запусков (для REPL)",
                "Возможность переопределения любых частей скрипта"
            )
        }
        slide {
            +evaluationTitle
            +exampleTitle
            +kotlinCode(lines = "1-4,10") {
                """
                    object GitlabCiKtEvaluationConfiguration : ScriptEvaluationConfiguration({
                        scriptsInstancesSharing(false)
                        implicitReceivers(PipelineBuilder())
                    })
                    
                    @KotlinScript(
                        fileExtension = "gitlab-ci.kts",
                        displayName = "Gitlab CI Kotlin configuration",
                        compilationConfiguration = GitlabCiKtScriptCompilationConfiguration::class,
                        evaluationConfiguration = GitlabCiKtEvaluationConfiguration::class,
                    )
                    abstract class GitlabCiKtScript
                """.trimIndent()
            }
        }
        slide {
            +evaluationTitle
            +exampleTitle
            +kotlinCode(lines = "3,12") {
                """
                    object GitlabCiKtEvaluationConfiguration : ScriptEvaluationConfiguration({
                        scriptsInstancesSharing(false)
                        constructorArgs("My Arg String")
                    })
                    
                    @KotlinScript(
                        fileExtension = "gitlab-ci.kts",
                        displayName = "Gitlab CI Kotlin configuration",
                        compilationConfiguration = GitlabCiKtScriptCompilationConfiguration::class,
                        evaluationConfiguration = GitlabCiKtEvaluationConfiguration::class,
                    )
                    abstract class GitlabCiKtScript(val arg: String)
                """.trimIndent()
            }
        }
    }

    val hostTitle = smallTitle { "Конфигурация хоста" }
    verticalSlide {
        slide {
            +hostTitle
        }
        slide {
            +hostTitle
            +exampleTitle
            +kotlinCode {
                """
                    object GitlabCiKtHostConfiguration : ScriptingHostConfiguration({
                        jvm {
                            val cacheBaseDir = findCacheBaseDir()
                            if (cacheBaseDir != null)
                                compilationCache(
                                    CompiledScriptJarsCache { script, scriptCompilationConfiguration ->
                                        cacheBaseDir
                                            .resolve(
                                                compiledScriptUniqueName(
                                                    script, 
                                                    scriptCompilationConfiguration
                                                )
                                            )
                                    }
                                )
                        }
                    })
                """.trimIndent()
            }
        }
        slide {
            +hostTitle
            +exampleTitle
            +kotlinCode(lines = "6") {
                """
                    @KotlinScript(
                        displayName = "Gitlab CI Kotlin configuration",
                        fileExtension = "gitlab-ci.kts",
                        compilationConfiguration = GitlabCiKtScriptCompilationConfiguration::class,
                        evaluationConfiguration = GitlabCiKtEvaluationConfiguration::class,
                        hostConfiguration = GitlabCiKtHostConfiguration::class,
                    )
                    abstract class GitlabCiKtScript
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
                            createJvmCompilationConfigurationFromTemplate<GitlabCiKtScript> {}
                        val evaluationConfiguration = 
                            createJvmEvaluationConfigurationFromTemplate<GitlabCiKtScript> {}
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
            +regular("META-INF/kotlin/script/templates/\ndev.limebeck.ci.gitlab.scripts.GitlabCiKtScript.classname")
        }
        slide {
            +ideTitle
            +img("IDE_scriptDefPath.png") {
                stretch = true
            }
        }
        slide {
            +ideTitle
            +img("IDE.png") {
                stretch = true
            }
        }
        slide {
            +ideTitle
            +img("IDE_closeup.png") {
                stretch = true
            }
        }
        slide {
            +ideTitle
            +unorderedListOf(
                "Работает только в IntelliJ IDEA / Fleet",
                "Нужен проект Gradle/Maven для работы",
                fragmented = false
            )
        }
    }

    //JSR223
    verticalSlide {
        slide {
            +smallTitle { "Java Scripting API" }
            +smallTitle { "(JSR223)" }
        }
        slide {
            +smallTitle { "Абстракция в JVM для исполнения скриптов" }
        }
        slide {
            +smallTitle { "Kotlin Script + JSR223" }
            +regular { "Обертка поверх BasicJvmScriptingHost" }
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
        }
    }

    verticalSlide {
        slide {
            +smallTitle { "Недостатки встраивания" }
            +unorderedListOf(
                "Компилятор Kotlin в приложении",
                "Поддержка IDE - JetBrains only",
                "Мало документации",
                "Только на JVM",
                "Долгий старт",
            )
        }
        slide {
            +smallTitle { "Преимущества встраивания" }
            +unorderedListOf(
                "Нативно для Kotlin",
                "Расширяемость",
                "Поддержка собственных DSL",
                "Поддержка IDE",
            )
        }
    }

    slide {
        +smallTitle { "Резюмируя" }
    }
    slide {
        +smallTitle { "Резюмируя" }
        +unorderedListOf(
            "REPL - быстрая обратная связь",
            "Замена BASH - разработчикам и DevOps",
            "Встраивание - гибкая конфигурация",
        )
    }

    slide {
        +smallTitle { "Итого по Kotlin Script" }
        +unorderedListOf(
            "Зрелое решение (>6 лет)",
            "Развивается",
            "Лучше всего - со своим DSL",
            "Упрощает поддержку скриптов",
        )
    }

    slide {
        +title { "Ссылка на презентацию и полезные штуки" }
        +qr("https://github.com/LimeBeck/kotlin-script-presentation")
    }
}

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

head {
    script(type = "text/javascript") {
        unsafe {
            raw(
                """
                   (function(m,e,t,r,i,k,a){m[i]=m[i]||function(){(m[i].a=m[i].a||[]).push(arguments)};
                   m[i].l=1*new Date();
                   for (var j = 0; j < document.scripts.length; j++) {if (document.scripts[j].src === r) { return; }}
                   k=e.createElement(t),a=e.getElementsByTagName(t)[0],k.async=1,k.src=r,a.parentNode.insertBefore(k,a)})
                   (window, document, "script", "https://mc.yandex.ru/metrika/tag.js", "ym");

                   ym(97198873, "init", {
                        clickmap:true,
                        trackLinks:true,
                        accurateTrackBounce:true,
                        trackHash: true
                   });
                """.trimIndent()
            )
        }
    }
}