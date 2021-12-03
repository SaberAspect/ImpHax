package mod.imphack.setting.settings;


import mod.imphack.Main;
import mod.imphack.module.Module;
import mod.imphack.setting.Setting;

public class IntSetting extends Setting {
	public int value;

	public IntSetting(String name, Module parent, int value) {
		this.name = name;
		this.parent = parent;
		if (!Main.configLoaded)
			this.value = value;
	}

	

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;

		if (Main.config != null) {
			Main.config.Save();
		}
	}
}