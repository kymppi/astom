plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "dev.midka"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.akuleshov7:ktoml-core:0.4.1")
    implementation("com.akuleshov7:ktoml-file:0.4.1")
    implementation("com.github.Minestom:Minestom:067227421f")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}