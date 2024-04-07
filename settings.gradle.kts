rootProject.name = "kotlin-script"

rootDir.resolve("gitlab-ci-kts")
    .list()
    ?.filter { rootDir.resolve("gitlab-ci-kts").resolve(it).isDirectory }
    ?.forEach { include(":gitlab-ci-kts:$it") }