package mod.imphack.module.modules.render;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.ModeSetting;


public class NoRender extends Module{
    public static NoRender INSTANCE;

	  public final BooleanSetting hurtCamera = new BooleanSetting("Hurt Camera", this, false);
	    public final BooleanSetting fire = new BooleanSetting("Fire", this, false);
	    public final BooleanSetting particles = new BooleanSetting("Particles", this, false);
	    public final BooleanSetting totems = new BooleanSetting("Totems", this, false);
	    public final BooleanSetting blocks = new BooleanSetting("Blocks", this, false);
	    public final BooleanSetting weather = new BooleanSetting("Weather", this, false);
	    public final BooleanSetting fog = new BooleanSetting("Fog", this, false);
	    public final BooleanSetting pumpkins = new BooleanSetting("Pumpkins", this, false);
	    public final BooleanSetting potions = new BooleanSetting("Potions", this, false);
	    public final BooleanSetting scoreboard = new BooleanSetting("Scoreboard", this, false);
	    public final BooleanSetting advancements = new BooleanSetting("Advancements", this, false);
	    public final BooleanSetting xp = new BooleanSetting("XP", this, false);
	    public final BooleanSetting portals = new BooleanSetting("Portals", this, false);
	    public final ModeSetting armor = new ModeSetting("Armor", this, "None", "None", "Glint", "Remove");
	    public final ModeSetting bossbar = new ModeSetting("Bossbar", this, "None", "None", "Remove", "Stack");

	    public NoRender() {
	    	super("NoRender", "Stops rendering stuff", Category.RENDER);
	        INSTANCE = this;

	    	
	        addSetting(hurtCamera, fire, particles, totems, blocks, weather, fog, pumpkins, potions, scoreboard, advancements, xp, portals, armor, bossbar);

	    }

	    
	}