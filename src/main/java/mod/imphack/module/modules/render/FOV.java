package mod.imphack.module.modules.render;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.IntSetting;
import net.minecraftforge.common.MinecraftForge;

public class FOV extends Module {

	public FOV() {
		super("FOV", "Changes your players view", Category.RENDER);
		addSetting(custom_fov);
	}

	IntSetting custom_fov = new IntSetting("Fov", this, 120);

	float fovOld;

	  private float fov;

	    @Override
	    protected void onEnable() {
	        fov = mc.gameSettings.fovSetting;
	        MinecraftForge.EVENT_BUS.register(this);
	    }

	    @Override
	    protected void onDisable() {
	        mc.gameSettings.fovSetting = fov;
	        MinecraftForge.EVENT_BUS.unregister(this);
	    }

	    @Override
	    public void onUpdate() {
	        mc.gameSettings.fovSetting = custom_fov.getValue();
	    }
}
