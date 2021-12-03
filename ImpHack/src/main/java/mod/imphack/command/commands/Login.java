package mod.imphack.command.commands;

import mod.imphack.Client;
import mod.imphack.command.Command;
import mod.imphack.util.login.LoginUtils;
import net.minecraft.client.Minecraft;

public class Login extends Command{

	public Login() {
		super("login", new String [] {"<email>", "<password>"});
	}
	
	final Minecraft mc = Minecraft.getMinecraft();
	
	public String getSyntax() {
		return ".login | .login [username] [password]";
				

	}
	  
    @Override
    public void execute(final String[] commands) {
			try
			{
				if(commands.length > 1 || commands[0].contains(":")) {
					String email;
					String password;
					if(commands[0].contains(":")) {
						String[] split = commands[0].split(":", 2);
						email = split[0];
						password = split[1];
					}
					else
					{
						email = commands[0];
						password = commands[1];
					}
					String log = LoginUtils.loginAlt(email, password);
					Client.addChatMessage(log);
				} 
				else 
				{
					LoginUtils.changeCrackedName(commands[0]);
					Client.addChatMessage("Logged [Cracked]: " + mc.getSession().getUsername());
				}
			}
			catch(Exception e)
			{
				Client.addChatMessage("Usage: " + getSyntax());
			}
		}
	}
