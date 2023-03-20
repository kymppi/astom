package dev.midka.astom.commands

import dev.midka.astom.utils.Versioning
import net.gauntletmc.command.annotations.Alias
import net.gauntletmc.command.annotations.DefaultCommand
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.event.ClickEvent.openUrl
import net.kyori.adventure.text.event.HoverEvent.showText
import net.kyori.adventure.text.format.NamedTextColor.*
import net.kyori.adventure.text.format.TextDecoration.UNDERLINED
import net.minestom.server.entity.Player


@Alias("info", "about")
class InfoCommand {
    @DefaultCommand
    fun thisIsDefault(sender: Player) {
        val message = text().append(newline()).append(text("Running on ", GRAY)).append(
            text("Astom", RED)
                .decorate(UNDERLINED)
                .clickEvent(openUrl("https://github.com/kymppi/astom"))
                .hoverEvent(
                    showText(text("https://github.com/kymppi/astom", GRAY))
                )
        )
            .append(text(" based on ", GRAY))
            .append(
                text("Minestom", RED)
                    .decorate(UNDERLINED)
                    .clickEvent(openUrl("https://minestom.net"))
                    .hoverEvent(
                        showText(text("https://minestom.net", GRAY))
                    )
            )
            .append(newline()).append(newline())
            .append(text("Version: ", GRAY))
            .append(text(Versioning("/META-INF/commit.txt").get(), GREEN))
            .append(newline())

        sender.sendMessage(message)
    }
}