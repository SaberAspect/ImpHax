package mod.imphack.setting;

import mod.imphack.Main;
import mod.imphack.module.Module;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class SettingManager extends GuiScreen {
	private final ArrayList<Setting> settings;

	public SettingManager() {
		this.settings = new ArrayList <>();
	}

	public void addSetting(Setting in) {
		this.settings.add(in);
	}

	public ArrayList<Setting> getSettings() {
		return this.settings;
	}

	public ArrayList<Setting> getSettingsByMod(Module mod) {
		ArrayList<Setting> out = new ArrayList <>();
		for (Setting s : getSettings()) {
			if (s.parent.equals(mod)) {
				out.add(s);
			}
		}
		if (out.isEmpty()) {
			return null;
		}
		return out;
	}

	public Setting getSettingByName(Module mod, String name) {
		for (Module m : Main.moduleManager.modules) {
			for (Setting set : m.settings) {
				if (set.name.equalsIgnoreCase(name) && set.parent == mod) {
					return set;
				}
			}
		}
		return null;
	}

	public void clearSettings() {
		this.settings.clear();
	}
}
