package mod.imphack.command;

import mod.imphack.Client;
import mod.imphack.command.commands.*;

import java.util.ArrayList;
import java.util.LinkedList;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CommandManager extends CommandFeature{
	 private final ArrayList<Command> commands;
	    private String clientMessage;
	    private String prefix;
	    
	    public CommandManager() {
	        super("Command");
	        this.commands = new ArrayList<Command>();
	        this.prefix = ".";
	        this.commands.add(new Bind());
			this.commands.add(new Toggle());
			this.commands.add(new Help());
			this.commands.add(new Rotate());
			this.commands.add(new Login());
			this.commands.add(new Prefix());
	    }
	    
	    public static String[] removeElement(final String[] input, final int indexToDelete) {
	        final LinkedList<String> result = new LinkedList<String>();
	        for (int i = 0; i < input.length; ++i) {
	            if (i != indexToDelete) {
	                result.add(input[i]);
	            }
	        }
	        return result.toArray(input);
	    }
	    
	    private static String strip(final String str, final String key) {
	        if (str.startsWith(key) && str.endsWith(key)) {
	            return str.substring(key.length(), str.length() - key.length());
	        }
	        return str;
	    }
	    
	    public void executeCommand(final String command) {
	        final String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
	        final String name = parts[0].substring(1);
	        final String[] args = removeElement(parts, 0);
	        for (int i = 0; i < args.length; ++i) {
	            if (args[i] != null) {
	                args[i] = strip(args[i], "\"");
	            }
	        }
	        for (final Command c : this.commands) {
	            if (!c.getName().equalsIgnoreCase(name)) {
	                continue;
	            }
	            c.execute(parts);
	            return;
	        }
	        Client.addChatMessage("Command not found, type 'help' for the commands list.");
	    }
	    
	    public Command getCommandByName(final String name) {
	        for (final Command command : this.commands) {
	            if (!command.getName().equals(name)) {
	                continue;
	            }
	            return command;
	        }
	        return null;
	    }
	    
	    public ArrayList<Command> getCommands() {
	        return this.commands;
	    }
	    
	    public String getClientMessage() {
	        return this.clientMessage;
	    }
	    
	    public void setClientMessage(final String clientMessage) {
	        this.clientMessage = clientMessage;
	    }
	    
	    public String getPrefix() {
	        return this.prefix;
	    }
	    
	    public void setPrefix(final String prefix) {
	        this.prefix = prefix;
	    }
	}