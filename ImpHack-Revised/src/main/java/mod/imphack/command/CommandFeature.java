package mod.imphack.command;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import mod.imphack.misc.TextManager;
import mod.imphack.module.Module;
import mod.imphack.setting.Setting;
import mod.imphack.util.Wrapper;

public class CommandFeature implements Wrapper {
	  public List<Setting> settings;
	    public TextManager renderer;
	    private String name;
	    
	    public CommandFeature() {
	        this.settings = new ArrayList<Setting>();
	    }
	    
	    public CommandFeature(final String name) {
	        this.settings = new ArrayList<Setting>();
	        this.name = name;
	    }
	    
	    public static boolean nullCheck() {
	        return CommandFeature.mc.player == null;
	    }
	    
	    public static boolean fullNullCheck() {
	        return CommandFeature.mc.player == null || CommandFeature.mc.world == null;
	    }
	    
	    public String getName() {
	        return this.name;
	    }
	    
	    public List<Setting> getSettings() {
	        return this.settings;
	    }
	    
	    public boolean hasSettings() {
	        return !this.settings.isEmpty();
	    }
	 	    
	    public void clearSettings() {
	        this.settings = new ArrayList<Setting>();
	    }
	}
