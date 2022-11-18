/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.command.Command
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.SettingsUtils
import net.ccbluex.liquidbounce.utils.misc.StringUtils
import java.io.File
import java.io.IOException

class LocalAutoSettingsCommand : Command("config", arrayOf("localsetting", "localsettings", "localconfig", "localautosettings")) {
    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        if (args.size > 1) {
            when {
                args[1].equals("load", ignoreCase = true) -> {
                    if (args.size > 2) {
                        val scriptFile = File(LiquidBounce.fileManager.settingsDir, args[2])

                        if (scriptFile.exists()) {
                            try {
                                chat("§9Loading Config " + args[2])
                                val settings = scriptFile.readText()
                                chat("§9Setting up...")
                                SettingsUtils.executeScript(settings)
                                chat("§6Config " + args[2] + " loaded.")
                                LiquidBounce.hud.addNotification(Notification("Loaded Config.", Notification.Type.SUCCESS))
                                playEdit()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                            return
                        }

                        chat("§cConfig " + args[2] + " does not exist!")
                        return
                    }

                    chatSyntax("config load <name>")
                    return
                }

                args[1].equals("save", ignoreCase = true) -> {
                    if (args.size > 2) {
                        val scriptFile = File(LiquidBounce.fileManager.settingsDir, args[2])

                        try {
                            if (scriptFile.exists())
                                if (scriptFile.delete()) {
                                    scriptFile.createNewFile()
                                    chat("§aSuccessfully deleted old file.")
                                } else {
                                    chat("§cFailed to delete old file.")
                                    return
                                }

                            val option = if (args.size > 3) StringUtils.toCompleteString(args, 3).toLowerCase() else "values states"
                            val values = option.contains("all") || option.contains("values")
                            val binds = option.contains("all") || option.contains("binds")
                            val states = option.contains("all") || option.contains("states")
                            if (!values && !binds && !states) {
                                chatSyntaxError()
                                return
                            }

                            chat("§9Creating Config...")
                            val settingsScript = SettingsUtils.generateScript(values, binds, states)
                            chat("§9Saving Config...")
                            scriptFile.writeText(settingsScript)
                            chat("§6Config " + args[2] + " created successfully.")
                        } catch (throwable: Throwable) {
                            chat("§cFailed to create config: §3${throwable.message}")
                            ClientUtils.getLogger().error("Failed to create config.", throwable)
                        }
                        return
                    }

                    chatSyntax("config save <name> [all/values/binds/states]...")
                    return
                }

                args[1].equals("delete", ignoreCase = true) -> {
                    if (args.size > 2) {
                        val scriptFile = File(LiquidBounce.fileManager.settingsDir, args[2])

                        if (scriptFile.exists()) {
                            scriptFile.delete()
                            chat("§6Config " + args[2] + " deleted successfully.")
                            return
                        }

                        chat("§cConfig " + args[2] + " does not exist!")
                        return
                    }

                    chatSyntax("config delete <name>")
                    return
                }

                args[1].equals("list", ignoreCase = true) -> {
                    chat("§cConfig:")

                    val settings = this.getLocalSettings() ?: return

                    for (file in settings)
                        chat("- " + file.name)
                    return
                }
            }
        }
        chatSyntax("config <load/save/list/delete>")
    }

    override fun tabComplete(args: Array<String>): List<String> {
        if (args.isEmpty()) return emptyList()

        return when (args.size) {
            1 -> listOf("delete", "list", "load", "save").filter { it.startsWith(args[0], true) }
            2 -> {
                when (args[0].toLowerCase()) {
                    "delete", "load" -> {
                        val settings = this.getLocalSettings() ?: return emptyList()

                        return settings
                            .map { it.name }
                            .filter { it.startsWith(args[1], true) }
                    }
                }
                return emptyList()
            }
            3 -> {
                if (args[0].equals("save", true)) {
                    return listOf("all", "states", "binds", "values").filter { it.startsWith(args[2], true) }
                }
                return emptyList()
            }
            else -> emptyList()
        }
    }

    private fun getLocalSettings(): Array<File>? = LiquidBounce.fileManager.settingsDir.listFiles()
}