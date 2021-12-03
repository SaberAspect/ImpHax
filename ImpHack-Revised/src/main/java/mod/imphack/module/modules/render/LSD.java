package mod.imphack.module.modules.render;

import mod.imphack.Main;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class LSD extends Module {
	public LSD() {
		super("LSD", "Drugz", Category.RENDER);
	}

	@Override
	public void onEnable() {
		mc.renderGlobal.loadRenderers();
		if (OpenGlHelper.shadersSupported)
			if (mc.getRenderViewEntity() instanceof EntityPlayer) {
				if (mc.entityRenderer.getShaderGroup() != null)
					mc.entityRenderer.getShaderGroup().deleteShaderGroup();

				mc.entityRenderer.shaderIndex = 19;

				if (mc.entityRenderer.shaderIndex != EntityRenderer.SHADER_COUNT)
					mc.entityRenderer.loadShader(EntityRenderer.SHADERS_TEXTURES[19]);
				else
					mc.entityRenderer.shaderGroup = null;
			}

		MinecraftForge.EVENT_BUS.register(this);

		ImpHackEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();
	}

	@Override
	public void onDisable() {
		if (mc.entityRenderer.getShaderGroup() != null) {
			mc.entityRenderer.getShaderGroup().deleteShaderGroup();
			mc.entityRenderer.shaderGroup = null;
		}
		mc.gameSettings.smoothCamera = false;

		MinecraftForge.EVENT_BUS.unregister(this);

		ImpHackEventBus.EVENT_BUS.unsubscribe(this);

		Main.config.Save();
	}

	public void onUpdate(EntityPlayerSP e) {
		mc.gameSettings.smoothCamera = true;
	}

}
