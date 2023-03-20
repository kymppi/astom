plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("org.ajoberstar.grgit") version "4.1.0"
}

group = "dev.midka"
version = "1.0-SNAPSHOT"

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
    jvmTarget = "17"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.akuleshov7:ktoml-core:0.4.1")
    implementation("com.akuleshov7:ktoml-file:0.4.1")
    implementation("com.github.Minestom.Minestom:Minestom:067227421f")
    implementation("com.github.Moulberry:Minestand:-SNAPSHOT")
}

tasks {
    processResources {
        filesMatching("META-INF/*") {
            filter {
                it.replace("{git.commit}", grgit.head().id)
            }
        }
    }

    shadowJar {
        manifest {
            attributes (
                "Main-Class" to "dev.midka.astom.Server",
                "Multi-Release" to true
            )
        }

        archiveBaseName.set(project.name)
    }

    test { useJUnitPlatform() }
    build { dependsOn(shadowJar) }
}

tasks.test {
    useJUnitPlatform()
}
