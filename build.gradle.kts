val revealKtVersion = "0.2.2"

group = "dev.limebeck"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.9.10"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("dev.limebeck:revealkt-script-definition:$revealKtVersion")
}

//sourceSets {}
