package mod.imphack.module.modules.render;

import mod.imphack.event.events.ImpHackEventRender;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.ColorSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.util.EntityUtil;
import mod.imphack.util.MathUtil;
import mod.imphack.util.render.ColorUtil;
import mod.imphack.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class EntityTracers extends Module {

	IntSetting max = new IntSetting("Maximum Tracers", this, 50);
	final BooleanSetting monster = new BooleanSetting("Monsters", this, true);
	final BooleanSetting passive = new BooleanSetting("Passive Mobs", this, true);
	final BooleanSetting players = new BooleanSetting("Players", this, true);
	final BooleanSetting items = new BooleanSetting("Items", this, true);
	final BooleanSetting other = new BooleanSetting("Other Entities", this, true);
	final ColorSetting monsterColor = new ColorSetting("Monster Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting passiveColor = new ColorSetting("Passive Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting playerColor = new ColorSetting("Player Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting itemColor = new ColorSetting("Item Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting otherColor = new ColorSetting("Other Color", this, new ColorUtil(255, 255, 255, 255));
	final FloatSetting width = new FloatSetting("Tracer Width", this, 1f);

	public EntityTracers() {
		super("Tracers", "Traces a line to entities", Category.RENDER);

		addSetting(monster);
		addSetting(passive);
		addSetting(players);
		addSetting(items);
		addSetting(other);
		addSetting(monsterColor);
		addSetting(passiveColor);
		addSetting(playerColor);
		addSetting(itemColor);
		addSetting(otherColor);
		addSetting(width);
	}

	@Override
	public void render(ImpHackEventRender event) {
		if (mc.world == null)
			return;

		for (Entity entity : mc.world.loadedEntityList) {
			if (checkShouldRenderTracers(entity)) {
				final Vec3d pos = MathUtil.interpolateEntity(entity, event.get_partial_ticks()).subtract(
						mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY,
						mc.getRenderManager().renderPosZ);

				if (pos != null) {
					final boolean bobbing = mc.gameSettings.viewBobbing;
					mc.gameSettings.viewBobbing = false;
					mc.entityRenderer.setupCameraTransform(event.get_partial_ticks(), 0);
					final Vec3d forward = new Vec3d(0, 0, 1)
							.rotatePitch(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationPitch))
							.rotateYaw(-(float) Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));
					RenderUtil.drawLine3D((float) forward.x, (float) forward.y + mc.player.getEyeHeight(),
							(float) forward.z, (float) pos.x, (float) pos.y, (float) pos.z, width.value,
							getColor(entity));
					mc.gameSettings.viewBobbing = bobbing;
					mc.entityRenderer.setupCameraTransform(event.get_partial_ticks(), 0);
				}
			}
		}
	}

	private boolean checkShouldRenderTracers(Entity e) {
		if (e == Minecraft.getMinecraft().player)
			return false;
		if (e instanceof EntityPlayer) {
			return players.enabled;
		}
		if ((EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e))) {
			return monster.enabled;
		}
		if (EntityUtil.isPassive(e)) {
			return passive.enabled;
		}
		if (e instanceof EntityItem) {
			return items.enabled;
		}
		return other.enabled;
	}

	private int getColor(Entity e) {
		if (e instanceof EntityPlayer) {
			return new Color((float) playerColor.red / 255, (float) playerColor.green / 255,
					(float) playerColor.blue / 255, 0.5f).getRGB();
		}
		if ((EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e))) {
			return new Color((float) monsterColor.red / 255, (float) monsterColor.green / 255,
					(float) monsterColor.blue / 255, 0.5F).getRGB();
		}
		if (EntityUtil.isPassive(e)) {
			return new Color((float) passiveColor.red / 255, (float) passiveColor.green / 255,
					(float) passiveColor.blue / 255, 0.5F).getRGB();
		}
		if (e instanceof EntityItem) {
			return new Color((float) itemColor.red / 255, (float) itemColor.green / 255, (float) itemColor.blue / 255,
					0.5F).getRGB();
		}
		return new Color((float) otherColor.red / 255, (float) otherColor.green / 255, (float) otherColor.blue / 255,
				0.5F).getRGB();
	}
}
