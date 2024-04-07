import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

val kotlinVersion: String by project
val kotlinCoroutinesVersion: String by project

group = "dev.limebeck"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinCoroutinesVersion}") {
        version {
            strictly(kotlinCoroutinesVersion)
        }
    }

    implementation("com.github.ajalt.clikt:clikt:4.2.2")
    implementation("dev.otbe:gitlab-ci-dsl:0.2.0")
    implementation(project(":gitlab-ci-kts:script-loader"))
}


sourceSets {
    test {}
}

java {
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}