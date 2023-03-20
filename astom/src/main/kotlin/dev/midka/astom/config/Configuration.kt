package dev.midka.astom.config

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val host: String = "0.0.0.0",
    val port: Int = 25565,
    val mojangAuth: Boolean = false,
    val chunkViewDistance: Int = 8,
    val entityViewDistance: Int = 8,
    val bungeeConfiguration: BungeeConfiguration = BungeeConfiguration(),
    val velocityConfiguration: VelocityConfiguration = VelocityConfiguration(),
)

@Serializable
data class BungeeConfiguration(
    val enabled: Boolean = false,
)

@Serializable
data class VelocityConfiguration(
    val enabled: Boolean = false,
    val secretKey: String = "<SUPER SECURE SECRET>"
)