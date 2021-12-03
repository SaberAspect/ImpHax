package mod.imphack.command.commands;

import mod.imphack.Client;
import mod.imphack.Main;
import mod.imphack.command.Command;
import mod.imphack.module.Module;
import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

public class Bind extends Command {

	 public Bind() {
	        super("bind", new String[] { "<module>", "<bind>" });
	    }
	    
	    @Override
	    public void execute(final String[] commands) {
	        if (commands.length == 1) {
	            Client.addChatMessage("Please specify a module.");
	            return;
	        }
	        final String rkey = commands[1];
	        final String moduleName = commands[0];
	        final Module module = Main.moduleManager.getModule(moduleName);
	        if (module == null) {
	            Client.addChatMessage("Unknown module '" + module + "'!");
	            return;
	        }
	        if (rkey == null) {
	            Client.addChatMessage(module.getName() + " is bound to "  + module.getBind());
	            return;
	        }
	        int key = Keyboard.getKeyIndex(rkey.toUpperCase());
	        if (rkey.equalsIgnoreCase("none")) {
	            key = -1;
	        }
	        if (key == 0) {
	            Client.addChatMessage("Unknown key '" + rkey + "'!");
	            return;
	        }
	        module.setBind(key);
	        Client.addChatMessage("Bind for "  + module.getName()  + " set to " +  rkey.toUpperCase());
	    }
	}