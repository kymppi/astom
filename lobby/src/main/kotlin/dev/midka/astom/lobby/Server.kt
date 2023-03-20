package dev.midka.astom.lobby

import dev.midka.astom.Astom
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.block.Block

fun main() = Astom.start() {
    val instanceManager = MinecraftServer.getInstanceManager()
    val instanceContainer = instanceManager.createInstanceContainer()
    instanceContainer.setGenerator { unit -> unit.modifier().fillHeight(0, 40, Block.GRAY_CONCRETE)}

    val globalEventHandler = MinecraftServer.getGlobalEventHandler()
    globalEventHandler.addListener(PlayerLoginEvent::class.java) { event -> run {
        val player = event.player
        player.sendMessage("Welcome to Lobby!")
        event.setSpawningInstance(instanceContainer)
        player.respawnPoint = Pos(0.0, 42.0, 0.0)
    }}
}