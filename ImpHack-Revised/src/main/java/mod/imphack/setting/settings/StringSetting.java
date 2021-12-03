package mod.imphack.setting.settings;

import mod.imphack.Main;
import mod.imphack.module.Module;
import mod.imphack.setting.Setting;

public class StringSetting extends Setting {
	public String value;

	public StringSetting(String name, Module parent, String value) {
		this.name = name;
		this.parent = parent;
		if (value == null)
			this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;

		if (Main.config != null) {
			Main.config.Save();
		}
	}
}