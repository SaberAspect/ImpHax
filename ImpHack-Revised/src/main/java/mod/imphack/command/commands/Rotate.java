package mod.imphack.command.commands;

import mod.imphack.Client;
import mod.imphack.command.Command;
import net.minecraft.client.Minecraft;

public class Rotate extends Command {

	public Rotate() {
		super("rotate", new String[] { "<pitch>", "<yaw>" });
	}

	
	public String getSyntax() {
		return ".rotate [pitch] [yaw]";
	}
	
	  @Override
	    public void execute(final String[] commands) {
		if (commands[0].isEmpty() || commands[1].isEmpty()) {
			Client.addChatMessage("No arguments found");
			Client.addChatMessage(this.getSyntax());
		} else {
			Minecraft.getMinecraft().player.rotationPitch = Float.parseFloat(commands[0]);
			Minecraft.getMinecraft().player.rotationYaw = Float.parseFloat(commands[1]);
			Minecraft.getMinecraft().player.rotationYawHead = Float.parseFloat(commands[1]);
			Client.addChatMessage("Rotated player");
		}

	}

}
