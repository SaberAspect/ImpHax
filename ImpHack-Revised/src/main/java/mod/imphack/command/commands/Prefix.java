package mod.imphack.command.commands;



import mod.imphack.Client;
import mod.imphack.Main;
import mod.imphack.command.Command;

public class Prefix extends Command {

	 public Prefix() {
	        super("prefix", new String[] { "<char>" });
	    }
	    
	    @Override
	    public void execute(final String[] commands) {
	        if (commands.length == 1) {
	            Command.sendMessage("Current prefix is " + Main.cmdManager.getPrefix());
	            return;
	        }
	        Main.cmdManager.setPrefix(commands[0]);
	        Client.addChatMessage("Prefix changed to " + commands[0]);
	    }
}
