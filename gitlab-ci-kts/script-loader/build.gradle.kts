import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("java-library")
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

    api("org.jetbrains.kotlin:kotlin-scripting-common:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-scripting-jvm:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-scripting-jvm-host:$kotlinVersion")

    implementation("dev.otbe:gitlab-ci-dsl:0.2.0")
    implementation(project(":gitlab-ci-kts:script-definition"))
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
