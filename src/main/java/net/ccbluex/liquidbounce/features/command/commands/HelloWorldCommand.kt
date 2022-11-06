/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.features.command.commands

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Listenable
import net.ccbluex.liquidbounce.event.Render2DEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.command.Command
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation

class HelloWorldCommand : Command("helloworld", emptyArray()), Listenable {
    private var toggle = false

    init {
        LiquidBounce.eventManager.registerListener(this)
    }

    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        ClientUtils.displayChatMessage("Never gonna give you up")
        mc.shutdown()
    }

    @EventTarget
    fun onRender2D(event: Render2DEvent) {

    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        
    }

    override fun handleEvents() = true

    override fun tabComplete(args: Array<String>): List<String> {
        return listOf("helloworld")
    }
}