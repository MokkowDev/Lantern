package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.event.*;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

@ModuleInfo(name = "ClientSpoofer", spacedName = "Client Spoofer", description = "Spoofs your client.", category = ModuleCategory.MISC)
public class ClientSpoofer extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] {"Forge", "Lunar", "Geyser"}, "Lunar");

    @EventTarget
    public void onPacket(final PacketEvent event) {
    	if(event.getPacket() instanceof C17PacketCustomPayload) {
            final C17PacketCustomPayload c17 = (C17PacketCustomPayload) event.getPacket();      
            switch(modeValue.get().toLowerCase()) {
                case "Forge": {
                   c17.setData(newWriteBuffer("FML"));
                   break;
                }
              
                case "Lunar": {
                    c17.setData(newBytesBuffer("Lunar-Client"));
                    break;
                }
              
                case "Geyser": {
                    c17.setData(newBytesBuffer("Geyser"));
                    break;
                }
            }
        }
    }
    
    private PacketBuffer newWriteBuffer(final String data) {
         return new PacketBuffer(Unpooled.buffer()).writeString(data);
    }
    
    private PacketBuffer newBytesBuffer(final String data) {
         return new PacketBuffer(Unpooled.wrappedBuffer(data.getBytes()));
    }
}
