#!/usr/bin/env kotlin
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