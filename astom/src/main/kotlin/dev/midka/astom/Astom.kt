package dev.midka.astom

import com.akuleshov7.ktoml.TomlOutputConfig
import com.akuleshov7.ktoml.file.TomlFileReader
import com.akuleshov7.ktoml.file.TomlFileWriter
import dev.midka.astom.commands.InfoCommand
import dev.midka.astom.config.Configuration
import dev.midka.astom.utils.TabPerformanceDisplay
import kotlinx.serialization.serializer
import net.gauntletmc.command.Minestand
import net.minestom.server.MinecraftServer
import net.minestom.server.extras.MojangAuth
import net.minestom.server.extras.PlacementRules
import net.minestom.server.extras.bungee.BungeeCordProxy
import net.minestom.server.extras.velocity.VelocityProxy
import java.io.File


object Astom {
    fun start(callback: AstomContext.() -> Unit = {}) {
        val context = AstomContext(
            dataDirectory = File("data/").apply(File::mkdirs)
        )

        val configFile = File("astom.toml")
        val configuration = if (!configFile.exists()) {
            TomlFileWriter(outputConfig = TomlOutputConfig.compliant()).encodeToFile(serializer(), Configuration(), configFile.path)
            Configuration()
        } else {
            TomlFileReader.decodeFromFile(serializer(), configFile.path)
        }

        System.setProperty("minestom.chunk-view-distance", configuration.chunkViewDistance.toString())
        System.setProperty("minestom.entity-view-distance", configuration.entityViewDistance.toString())

        val minecraftServer = MinecraftServer.init()
        MinecraftServer.setBrandName("Astom (Minestom)")

        if (configuration.mojangAuth) MojangAuth.init()
        if (configuration.bungeeConfiguration.enabled) BungeeCordProxy.enable()
        if (configuration.velocityConfiguration.enabled) VelocityProxy.enable(configuration.velocityConfiguration.secretKey)

        PlacementRules.init()

        Minestand.register(InfoCommand::class.java)
        TabPerformanceDisplay.init(MinecraftServer.getGlobalEventHandler())

        with(context) {
            callback()
        }

        minecraftServer.start(configuration.host, configuration.port)
    }
}