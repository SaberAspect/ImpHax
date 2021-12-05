package mod.imphack.module.modules.client;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.setting.settings.StringSetting;
import mod.imphack.util.ImpHackDiscordRichPresence;

public class DiscordRPC extends Module {

	public static DiscordRPC instance;
	
	public DiscordRPC() {
		super("DiscordRPC", "Rich Presence For Discord", Category.CLIENT);
		instance = this;

		addSetting(showIp, state);
	}
	
	public final BooleanSetting showIp = new BooleanSetting("ShowIP", this, true);
    public StringSetting state = new StringSetting("State", this, "ImpHaX");



	@Override
	public void onEnable() {
			
			ImpHackDiscordRichPresence.start();
		
	}

	@Override
	public void onDisable() {
		ImpHackDiscordRichPresence.stop();
	}
}
