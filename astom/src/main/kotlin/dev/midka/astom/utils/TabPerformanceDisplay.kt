package dev.midka.astom.utils

import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.adventure.audience.Audiences
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.server.ServerTickMonitorEvent
import net.minestom.server.monitoring.TickMonitor
import net.minestom.server.timer.Task
import net.minestom.server.utils.MathUtils
import net.minestom.server.utils.time.TimeUnit

object TabPerformanceDisplay {

    private var task: Task? = null
    private var lastTick: TickMonitor? = null

    fun init(eventNode: EventNode<Event>) {
        if (task != null) {
            return
        }

        eventNode.addListener(ServerTickMonitorEvent::class.java) {
            lastTick = it.tickMonitor
        }

        MinecraftServer.getSchedulerManager().buildTask {
            if (lastTick == null) {
                return@buildTask
            }

            val runtime = Runtime.getRuntime()
            val ramMax = runtime.totalMemory() / 1024 / 1024
            val ramUsage = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024

            val tickTime = MathUtils.round(lastTick!!.tickTime, 2)

            val footer = newline()
                .append(text("RAM USAGE: $ramUsage/$ramMax MB", NamedTextColor.GRAY))
                .append(newline())
                .append(text("TICK TIME: ${tickTime}ms", NamedTextColor.GRAY))
                .append(newline())

            Audiences.players().sendPlayerListFooter(footer)
        }.repeat(10, TimeUnit.SERVER_TICK).schedule()
    }

    fun stop() {
        task?.cancel()
        task = null
    }

}