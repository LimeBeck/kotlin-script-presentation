val build = job("build") {
    stage = Stage("build")
    image = Image.of("alpine:latest")
    script("echo 'Hello World from build'")
}
listOf("dev-0", "dev-1", "dev-2").forEach {
    job("deploy-$it") {
        stage = Stage("deploy")
        needs(build)
        image = Image.of("alpine:latest")
        script("echo Deployed $it")
    }
}