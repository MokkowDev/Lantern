/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.discord

import com.jagrosh.discordipc.IPCClient
import com.jagrosh.discordipc.IPCListener
import com.jagrosh.discordipc.entities.RichPresence
import com.jagrosh.discordipc.entities.pipe.PipeStatus
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import org.json.JSONObject
import java.io.IOException
import java.time.OffsetDateTime
import kotlin.concurrent.thread

import org.lwjgl.opengl.Display

class ClientRichPresence : MinecraftInstance() {

    var showRichPresenceValue = true

    // IPC Client
    private var ipcClient: IPCClient? = null

    private var appID = 0L
    private val assets = mutableMapOf<String, String>()
    private val timestamp = OffsetDateTime.now()

    // Status of running
    private var running: Boolean = false

    /**
     * Setup Discord RPC
     */
    fun setup() {
        try {
            running = true

            loadConfiguration()

            ipcClient = IPCClient(appID)
            ipcClient?.setListener(object : IPCListener {

                /**
                 * Fired whenever an [IPCClient] is ready and connected to Discord.
                 *
                 * @param client The now ready IPCClient.
                 */
                override fun onReady(client: IPCClient?) {
                    thread {
                        while (running) {
                            update()

                            try {
                                Thread.sleep(1000L)
                            } catch (ignored: InterruptedException) {
                            }
                        }
                    }
                }

                /**
                 * Fired whenever an [IPCClient] has closed.
                 *
                 * @param client The now closed IPCClient.
                 * @param json A [JSONObject] with close data.
                 */
                override fun onClose(client: IPCClient?, json: JSONObject?) {
                    running = false
                }

            })
            ipcClient?.connect()
        } catch (e: Throwable) {
            ClientUtils.getLogger().error("Failed to setup Discord RPC.", e)
        }

    }

    /**
     * Update rich presence
     */
    fun update() {
        val builder = RichPresence.Builder()

        // Set playing time
        builder.setStartTimestamp(timestamp)

        // Check assets contains logo and set logo
        if (assets.containsKey("idk"))
            builder.setLargeImage(assets["idk"], "using version: ${LiquidBounce.CLIENT_VERSION}")

        val serverData = mc.currentServerData

        // Set display infos
        builder.setDetails(if (Display.isActive()) (if (mc.isIntegratedServerRunning || serverData != null) "Playing" else "Idle...") else "AFK")
        builder.setState("Name: ${mc.session.username}")

        if (serverData != null) {
            if(mc.isIntegratedServerRunning) builder.setSmallImage(assets["playing"], "Playing on Singleplayer")
            if(serverData.serverIP = "np.coldpvp.com || serverData.serverIP = "coldpvp.com") builder.setSmallImage(assets["coldpvp"], "Playing on ColdPvP")
            if(serverData.serverIP = "hypixel.net") builder.setSmallImage(assets["hypixel"], "Playing on Hypixel")
            if(serverData.serverIP = "blocksmc.com" || serverData.serverIP = "premium.blocksmc.com") builder.setSmallImage(assets["blocksmc"], "Playing on BlocksMC")
            if(serverData.serverIP = "mc.minebox.es") builder.setSmallImage(assets["minebox"], "Playing on MineBox")
            if(serverData!!.serverIP != "hypixel.net" || serverData!!.serverIP != "coldpvp.com" || serverData!!.serverIP != "np.coldpvp.com" || serverData!!.serverIP != "mc.minebox.es" || serverData!!.serverIP != "blocksmc.com" | serverData!!.serverIP != "premium.blocksmc.com") builder.setSmallImage(assets["playing"], "Playing on ${serverData.serverIP}")
         }
         
        // Check ipc client is connected and send rpc
        if (ipcClient?.status == PipeStatus.CONNECTED) ipcClient?.sendRichPresence(builder.build())
    }

    /**
     * Shutdown ipc client
     */
    fun shutdown() {
        if (ipcClient?.status != PipeStatus.CONNECTED) {
            return
        }
        
        try {
            ipcClient?.close()
        } catch (e: Throwable) {
            ClientUtils.getLogger().error("Failed to close Discord RPC.", e)
        }
    }

    private fun loadConfiguration() {
        appID = 814702169080987649L
        assets["coldpvp"] = "coldpvp"
        assets["hypixel"] = "hypixel"
        assets["blocksmc"] = "blocksmc"
        assets["minebox"] = "minebox"
        assets["mineplex"] = "mineplex"
        assets["idk"] = "idk"
        assets["playing"] = "playing"
    }
}
