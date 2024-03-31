#!/usr/bin/env kotlin
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.6")

import kotlinx.cli.*; import java.io.File;

val parser = ArgParser("copyIndexed")
val input by parser.option(ArgType.String, shortName = "i",
    description = "Input file").required()
parser.parse(args)
val file = File(input)
file.useLines {
    it.mapIndexed { index, line -> "$index - $line" }
        .forEach { File("${file.name}-copy").appendText(it) }
}