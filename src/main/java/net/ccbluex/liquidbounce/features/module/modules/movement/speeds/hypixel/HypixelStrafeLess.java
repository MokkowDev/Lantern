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

public class HypixelStrafeLess extends SpeedMode {

    public HypixelStrafeLess() {
        super("HypixelStrafeLess");
    }

    @Override
    public void onMotion() {

    }

    @Override
    public void onUpdate() {
        mc.timer.timerSpeed = 1F;
        final Speed speed = LiquidBounce.moduleManager.getModule(Speed.class);
        if(speed == null) return;

        if(MovementUtils.isMoving() && !(mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) && !mc.gameSettings.keyBindJump.isKeyDown()) {
            double moveSpeed = Math.max(MovementUtils.getBaseMoveSpeed() * speed.baseStrengthValue.get(), MovementUtils.getSpeed());

            if (mc.thePlayer.onGround) {
                if(MovementUtils.isMoving()) mc.thePlayer.jump();
                if (speed.recalcValue.get()) moveSpeed = Math.max(MovementUtils.getBaseMoveSpeed() * speed.baseStrengthValue.get(), MovementUtils.getSpeed());
                if(speed.strafeOnDmg.get() && mc.thePlayer.hurtTime > 2) MovementUtils.strafe());
                moveSpeed *= speed.moveSpeedValue.get();
            }  
        } 
    }

    @Override
    public void onMove(MoveEvent event) {
        //haha
    }
}
