package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import mod.imphack.module.Module;
import mod.imphack.setting.Setting;

public class ImpHackEventSettings extends ImpHackEventCancellable {
	public Setting setting;
	public Module module;

	public ImpHackEventSettings(Setting setting, Module module) {
		super();
		setting = this.setting;
		module = this.module;
	}
}
