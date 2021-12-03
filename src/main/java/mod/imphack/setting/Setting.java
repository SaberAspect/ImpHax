package mod.imphack.setting;

import mod.imphack.module.Module;

public class Setting {
	public String name;
	public Module parent;
	public boolean focused;
	
	public boolean is(String name) {
		return this.name.equalsIgnoreCase(name);
	}
}
