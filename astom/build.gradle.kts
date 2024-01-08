plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("org.ajoberstar.grgit") version "4.1.0"
}

group = "dev.midka"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.akuleshov7:ktoml-core:0.4.1")
    implementation("com.akuleshov7:ktoml-file:0.5.1")
    implementation("com.github.Minestom.Minestom:Minestom:0672274")
    implementation("com.github.Moulberry:Minestand:master-SNAPSHOT")
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

    build { dependsOn(shadowJar) }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    withSourcesJar()
    withJavadocJar()
}