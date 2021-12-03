package mod.imphack.setting.settings;

import mod.imphack.Main;
import mod.imphack.module.Module;
import mod.imphack.setting.Setting;
import mod.imphack.util.render.ColorUtil;

public class ColorSetting extends Setting {
	public int red;
	public int green;
	public int blue;
	public int alpha;
	private boolean rainbow;
	private ColorUtil value;
	
	

	public ColorSetting (String name, Module parent, final ColorUtil value) {
		this.name = name;
		this.parent = parent;
		this.value = value;
		if (!Main.configLoaded) {
			this.value = value;
		}
	}
	
	public ColorUtil getValue() {
		if (rainbow) {
			return getRainbow(0, this.getColor().getAlpha());
		}
		return this.value;
	}

	public static ColorUtil getRainbow(int incr, int alpha) {
		ColorUtil color =  ColorUtil.fromHSB(((System.currentTimeMillis() + incr * 200)%(360*20))/(360f * 20),0.5f,1f);
		return new ColorUtil(color.getRed(), color.getBlue(), color.getGreen(), alpha);
	}


	public void setValue (boolean rainbow, final ColorUtil value) {
		this.rainbow = rainbow;
		this.value = value;
	}

	public long toInteger() {
		return this.value.getRGB();
	}

	public void fromInteger (long number) {
		this.value = new ColorUtil(Math.toIntExact(number),true);
	}
	
	public ColorUtil getColor() {
		return this.value;
	}
}
