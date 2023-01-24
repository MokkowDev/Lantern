/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.hypixel;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.potion.Potion;

public class HypixelStrafeLess extends SpeedMode {

    public HypixelStrafeLess() {
        super("HypixelStrafeLess");
    }

    @Override
    public void onMotion() {

    }

    @Override
    public void onUpdate() {
        final Speed speed = LiquidBounce.moduleManager.getModule(Speed.class);
        if(speed == null) return;

        if(MovementUtils.isMoving() && !(mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) && !mc.gameSettings.keyBindJump.isKeyDown()) {
            double moveSpeed = Math.max(MovementUtils.getBaseMoveSpeed() + 0.20, MovementUtils.getSpeed());

        if(mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			moveSpeed += 0.08 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.063;
        }

            if (mc.thePlayer.onGround) {
                if(MovementUtils.isMoving()) mc.thePlayer.jump();
                if(speed.strafeOnDmg.get() && mc.thePlayer.hurtTime > 8) MovementUtils.strafe();
                MovementUtils.strafe((float) moveSpeed); //moment
            }  else if(!mc.thePlayer.onGround) {
            	// hank
           }
        } 
    }

    @Override
    public void onMove(MoveEvent event) {
    	final Speed speed = LiquidBounce.moduleManager.getModule(Speed.class);
        if(speed == null) return;

        if(Math.round(event.getY() * 1000) / 1000.0 == 0.165 && speed.fastFall.get()) {
			event.setY(mc.thePlayer.motionY = 0);
        }

        if(mc.thePlayer.onGround && speed.fastFall.get() && Math.abs(event.getY()) < 0.005) {
        	event.setY(mc.thePlayer.motionY = -0.11);
        }
    }
}
