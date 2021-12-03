package mod.imphack;

import mod.imphack.command.CommandManager;
import mod.imphack.module.Module;
import mod.imphack.ui.clickgui.ClickGuiController;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

//class to interact with the client

public class Client {

	
    private static String prefix = ".";


	
	
	

	public Client() {
		new CommandManager();
		

	}

	public static void addChatMessage(String s, boolean doPrefixture) {
		String prefixture;
		if (doPrefixture) {
			prefixture = "\247b[ImpHax]: ";
		} else {
			prefixture = "";
		}
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(prefixture + s));
	}

	public static void addChatMessage(String s) {
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString("\247b[ImpHax]: " + s));
	}

	public static volatile boolean getNextKeyPressForKeybinding = false;

	public static Module keybindModule;

	public static void waitForKeybindKeypress(Module m) {
		keybindModule = m;
		getNextKeyPressForKeybinding = true;
	}

	public static void stopWaitForKeybindPress() {
		getNextKeyPressForKeybinding = false;
		keybindModule = null;
		ClickGuiController.INSTANCE.settingController.refresh(false);
	}
	
	 public static String getPrefix() {
	        return prefix;
	    }
	    
	  public static void setPrefix(final String prefix) {
	        Client.prefix = prefix;
	    }

	public static void addChatMessage(int entityId, String string, boolean prefix) {
		String prefixture;
		if (prefix) {
			prefixture = "\247b[ImpHax]: ";
		} else {
			prefixture = "";
		}
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(prefixture + string));
	}
}
