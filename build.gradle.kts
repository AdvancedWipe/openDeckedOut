plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    checkstyle
}

group = "com.github.advancedwipe"
version = "1.0.0-SNAPSHOT"
description = "OpenDeckedOut plugin"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    implementation("cloud.commandframework:cloud-paper:1.8.4")
    implementation("cloud.commandframework:cloud-annotations:1.8.4")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.8.4")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")

    val scoreboardLibraryVersion = "2.0.0-RC12"
    implementation("com.github.megavexnetwork.scoreboard-library:scoreboard-library-api:$scoreboardLibraryVersion")
    runtimeOnly("com.github.megavexnetwork.scoreboard-library:scoreboard-library-implementation:$scoreboardLibraryVersion")
    runtimeOnly("com.github.megavexnetwork.scoreboard-library:scoreboard-library-modern:$scoreboardLibraryVersion")

    annotationProcessor("cloud.commandframework:cloud-annotations:1.8.4")}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

checkstyle {
    val archive = configurations.checkstyle.get().resolve().filter {
        it.name.startsWith("checkstyle")
    }
    config = resources.text.fromArchiveEntry(archive, "google_checks.xml")
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.20.1")
    }

    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
        val props = mapOf(
                "name" to project.name,
                "version" to project.version,
                "description" to project.description,
                "apiVersion" to "1.20"
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
