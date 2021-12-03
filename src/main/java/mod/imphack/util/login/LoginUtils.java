package mod.imphack.util.login;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.lang.reflect.Field;
import java.net.Proxy;

public class LoginUtils{
	
	public static final String session = isNotObfuscated() ? "session" : "field_71449_j";
	
    public static String loginAlt(String email, String password) {
        YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication) authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        String displayText;

        try {
            authentication.logIn();   
            try {
            	Field f = Minecraft.class.getDeclaredField(session);
            	f.setAccessible(true);
				f.set(Minecraft.getMinecraft(), new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang"));
				displayText = "Logged [License]: " + Minecraft.getMinecraft().getSession().getUsername();
			} catch (Exception e) {
				displayText = "Unknown error.";
				e.printStackTrace();
			}
            
        } catch (AuthenticationUnavailableException e) {
            displayText = "Cannot contact authentication server!";
        } catch (AuthenticationException e)
        {
            if (e.getMessage().contains("Invalid username or password.") || e.getMessage().toLowerCase().contains("account migrated")) {
                displayText = "Wrong password!";
            } else {
                displayText = "Cannot contact authentication server!";
            }
        } catch (NullPointerException e) {
            displayText = "Wrong password!";
        }

        return displayText;
    }

    public static String getName(String email, String password) {
        YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication) authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        try {
            authentication.logIn();
            return authentication.getSelectedProfile().getName();
        } catch (Exception e) {
            return null;
        }
    }

    public static void changeCrackedName(String name) {
        try {
        	Field f = Minecraft.class.getDeclaredField(session);
        	f.setAccessible(true);
			f.set(Minecraft.getMinecraft(), new Session(name, "", "", "mojang"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static boolean isNotObfuscated() {
        try { return Minecraft.class.getDeclaredField("instance") != null;
        } catch (Exception ex) { return false; }
    }

}
