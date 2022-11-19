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
                 c17.setData(newBuffer("FML", true));
                 break;
              }
              
              case "Lunar": {
                  c17.setChannel("REGISTER");
                  c17.setData(newBuffer("Lunar-Client", false));
                  break;
              }
              
              case "Geyser": {
                  packet.setData(newBuffer("Geyser", false));
                  break;
              }
         }
    }
    
    private PacketBuffer newBuffer(final String data, final boolean write) {
        if (write)
            return new PacketBuffer(Unpooled.buffer()).writeString(data);
        else
            return new PacketBuffer(Unpooled.wrappedBuffer(data.getBytes()));
    }
}
