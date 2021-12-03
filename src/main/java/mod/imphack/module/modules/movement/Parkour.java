package mod.imphack.module.modules.movement;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Parkour extends Module {
	public Parkour() {
		super("Parkour", "Jump off the edge of blocks", Category.MOVEMENT);
	}

	@SubscribeEvent
	public void onUpdateInput(final InputUpdateEvent event) {
		if (Minecraft.getMinecraft().player.onGround && !Minecraft.getMinecraft().player.isSneaking()
				&& !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed()
				&& Minecraft.getMinecraft().world
				.getCollisionBoxes(Minecraft.getMinecraft().player, Minecraft.getMinecraft().player
						.getEntityBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001))
				.isEmpty()) {
			Minecraft.getMinecraft().player.jump();
		}
	}
}
