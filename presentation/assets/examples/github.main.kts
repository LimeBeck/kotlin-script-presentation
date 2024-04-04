#!/usr/bin/env kotlin
@file:DependsOn("io.github.typesafegithub:github-workflows-kt:1.13.0")

import ...

workflow(name = "Build", on = listOf(PullRequest()), sourceFile = __FILE__.toPath()) {
    job(id = "build", runsOn = UbuntuLatest) {
        uses(action = CheckoutV4())
        uses(action = SetupJavaV3())
        uses(
            name = "Build",
            action = GradleBuildActionV2(
                arguments = "build",
            )
        )
    }
}.writeToFile()