listOf("dev-0", "dev-1", "dev-2").forEach {
    job("build-$it") {
        image = Image.of("alpine:latest")
        script("echo 'Hello World from $it'")
    }
    job("deploy-$it") {
        image = Image.of("alpine:latest")
        script("echo Deployed $it")
    }
}