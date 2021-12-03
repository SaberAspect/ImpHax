package mod.imphack.module.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.event.events.ImpHackEventHorseSaddled;
import mod.imphack.event.events.ImpHackEventSteerEntity;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import net.minecraft.util.MovementInput;

public class EntityRide extends Module {
	final BooleanSetting jesus = new BooleanSetting("Jesus", this, true);
	final BooleanSetting control = new BooleanSetting("Control", this, true);
	final FloatSetting speed = new FloatSetting("Speed", this, 0.5f);

	public EntityRide() {
		super("EntityRide", "Utilities For Rideable Entities", Category.MOVEMENT);

		addSetting(jesus);
		addSetting(control);
		addSetting(speed);
	}

	@EventHandler
	private final Listener<ImpHackEventSteerEntity> OnSteerEntity = new Listener<>(p_Event -> {
		if (control.enabled)
			p_Event.cancel();
	});

	@EventHandler
	private final Listener<ImpHackEventHorseSaddled> OnHorseSaddled = new Listener<>(p_Event -> {
		if (control.enabled)
			p_Event.cancel();
	});

	@Override
	public void onUpdate() {
		if (mc.world == null || mc.player.getRidingEntity() == null) {
			return;
		}
		if (mc.player.isRiding()) {
			MovementInput movementInput = mc.player.movementInput;
			double forward = movementInput.moveForward;
			double strafe = movementInput.moveStrafe;
			float yaw = mc.player.rotationYaw;
			if ((forward == 0.0D) && (strafe == 0.0D)) {
				mc.player.getRidingEntity().motionX = 0.0D;
				mc.player.getRidingEntity().motionZ = 0.0D;
			} else {
				if (forward != 0.0D) {
					if (strafe > 0.0D) {
						yaw += (forward > 0.0D ? -45 : 45);
					} else if (strafe < 0.0D) {
						yaw += (forward > 0.0D ? 45 : -45);
					}
					strafe = 0.0D;
					if (forward > 0.0D) {
						forward = 1.0D;
					} else if (forward < 0.0D) {
						forward = -1.0D;
					}
				}
				mc.player
						.getRidingEntity().motionX = (forward * speed.getValue() * Math.cos(Math.toRadians(yaw + 90.0F))
								+ strafe * speed.getValue() * Math.sin(Math.toRadians(yaw + 90.0F)));
				mc.player
						.getRidingEntity().motionZ = (forward * speed.getValue() * Math.sin(Math.toRadians(yaw + 90.0F))
								- strafe * speed.getValue() * Math.cos(Math.toRadians(yaw + 90.0F)));
			}
		}
		if (jesus.enabled && (mc.player.getRidingEntity().isInWater())) {
			mc.player.getRidingEntity().motionY += 0.03999999910593033D;
		}
	}
}
