package mod.imphack.command.commands;

import mod.imphack.Client;
import mod.imphack.Main;
import mod.imphack.command.Command;
import mod.imphack.module.Module;

public class Toggle extends Command {

	public Toggle() {
		super("toggle", new String[] {"<Module>"});
	}
	
	
	public String getSyntax() {
		return ".toggle [Module]";
	}

 @Override
    public void execute(final String[] commands) {
		for (Module m : Main.moduleManager.getModuleList()) {
			if (commands[0].equalsIgnoreCase(m.getName())) {
				m.toggle();
				Client.addChatMessage(commands[0] + " has been toggled");
				return;
			}
		}
		Client.addChatMessage("Module " + commands[0] + " was not found");
		Client.addChatMessage(this.getSyntax());
	}

}
