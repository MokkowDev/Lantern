package net.ccbluex.liquidbounce.utils;

import org.lwjgl.opengl.Display;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.LiquidBounce;

public final class TitleUtils {

    // Example: Lantern client v6.9
    public static void setDefaultTitle() {
        try {
            Display.setTitle("Lantern client v" + LiquidBounce.CLIENT_VERSION);
        } catch(Exception e) {
        	ClientUtils.getLogger().error("Failed to set default title.");
        }
    }
   
    // Custom title, Example: Example title
    public static void setTitle(String title) {
        try {
            Display.setTitle(title);
        } catch(Exception e) {
        	ClientUtils.getLogger().error("Failed to set custom title.");
        }
    }
}