# Kotlin Script: кому, зачем и как

Доклад на JPoint.ru: [тыц](https://jpoint.ru/talks/4f1950e6c4144d0ea1ac306d0545217a/)

Доклад на YouTube: [тыц](https://www.youtube.com/watch?v=zVqqE-yntiE)

Я в Telegram: [@limebeck](https://t.me/limebeck)

Открыть презентацию в браузере можно по ссылке: https://kotlin-script.static.limebeck.dev/

Презентация написана с использованием [Reveal-Kt](https://github.com/LimeBeck/reveal-kt) 
- обертки в виде Kotlin DSL поверх [reveal.js](https://revealjs.com/)

Для запуска локально необходимо установить [JBang](https://jbang.dev) и выполнить

```bash
./presentation/kotlin-script.reveal.kts
```

или же запустить `presentation/kotlin-script.reveal.kts` напрямую из IDE

Управление в презентации:
- `Space` - вперёд
- `P` - назад
- `O` - обзор структуры
- стрелками - перемещение по структуре

## Полезные ссылки

* [KEEP: Kotlin Scripting support](https://github.com/Kotlin/KEEP/blob/master/proposals/scripting-support.md#embedded-scripting)
* [Обсуждение KEEP на GitHub](https://github.com/Kotlin/KEEP/issues/75)
* [KotlinConf 2019: Implementing the Gradle Kotlin DSL by Rodrigo Oliveira](https://www.youtube.com/watch?v=OEFwnWxoazI) - доклад от авторов Gradle
* [Roadmap развития Kotlin](https://kotlinlang.org/docs/roadmap.html)
* [Официальные примеры встраивания Kotlin Script](https://github.com/Kotlin/kotlin-script-examples)
* [The Java Scripting API](https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/prog_guide/api.html)
* [Ядро Kotlin для Jupyter Notebook](https://github.com/Kotlin/kotlin-jupyter)
* [Александр Нозик — Ноутбуки Kotlin для обучения и прототипирования](https://www.youtube.com/watch?v=4Sg2Qju67kE) 
* [Issue с применением компиляторных плагинов в скриптах](https://youtrack.jetbrains.com/issue/KT-47384)
* [Script Definition для MainKts](https://github.com/JetBrains/kotlin/blob/master/libraries/tools/kotlin-main-kts/src/org/jetbrains/kotlin/mainKts/scriptDef.kt)

## Примеры проектов с использованием Kotlin Script

* https://www.jetbrains.com/help/space/automation-faq.html
* https://github.com/LimeBeck/reveal-kt
* https://github.com/way-zer/ScriptAgent4MindustryExt/tree/7.5
* https://hshell.hydraulic.dev/14.0/
* https://github.com/dkandalov/live-plugin
* https://github.com/typesafegithub/github-workflows-kt
* https://github.com/kscripting/kscript
* https://gitlab.com/otbe-dev/gitlab-ci-dsl
* https://github.com/JetBrains/kotlin/tree/master/libraries/tools/kotlin-main-kts
