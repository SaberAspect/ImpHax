package mod.imphack.module.modules.utilities;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.IntSetting;
import net.minecraft.client.multiplayer.ServerData;

public class Reconnect extends Module {

	public final IntSetting timer = new IntSetting("Timer", this, 5000);

	public ServerData serverData;

	public Reconnect() {
		super("Reconnect", "Reconnects You Automatically", Category.UTILITIES);

		addSetting(timer);
	}
}
