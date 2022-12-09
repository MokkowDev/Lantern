/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

import java.awt.*;

@ModuleInfo(name = "WaveyCape", description = "idk bro", category = ModuleCategory.RENDER)
public class WaveyCape extends Module {

    public final ListValue capeStyle = new ListValue("Style", new String[] {"Blocky", "Smooth"}, "Smooth");
    public final ListValue windMode = new ListValue("WindMode", new String[] {"Waves", "None"}, "None");
    public final ListValue capeMovement = new ListValue("Movement", new String[] {"Vanila", "Simulation"}, "Simulation");
    public final IntegerValue capeGravity = new IntegerValue("Gravity", 25, 5, 32);
	public final IntegerValue capeHeight = new IntegerValue("HeightMultiplier", 6, 4, 16);
	
    @Override
    public void onEnable() {
        
    }
}
