package mod.imphack.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import mod.imphack.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class ImpHackDiscordRichPresence {
	
	 private static final DiscordRPC rpc;
	    public static DiscordRichPresence presence;
	    private static Thread thread;
	    private static int index;

	    static {
	        index = 1;
	        rpc = DiscordRPC.INSTANCE;
	        presence = new DiscordRichPresence();
	    }

	public static void start() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
			rpc.Discord_Initialize("911730341708967946", handlers, true, "");

			ImpHackDiscordRichPresence.presence.startTimestamp = Main.startTimeStamp / 1000L;
			ImpHackDiscordRichPresence.presence.details = Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu ? "In the main menu." : "Playing " + (Minecraft.getMinecraft().currentServerData != null ? (mod.imphack.module.modules.client.DiscordRPC.instance.showIp.isEnabled() ? "on " + Minecraft.getMinecraft().currentServerData.serverIP + "." : " multiplayer.") : " singleplayer.");
			ImpHackDiscordRichPresence.presence.state = mod.imphack.module.modules.client.DiscordRPC.instance.state.getValue();
			ImpHackDiscordRichPresence.presence.largeImageKey = "impbase";
			ImpHackDiscordRichPresence.presence.largeImageText = Reference.NAME + " " + Reference.RELEASE_VERSION;
			ImpHackDiscordRichPresence.presence.smallImageKey = "skyrim";
			ImpHackDiscordRichPresence.presence.smallImageText = Reference.NAME + " " + Reference.RELEASE_VERSION;
			rpc.Discord_UpdatePresence(presence);
		
			  thread = new Thread(() -> {
		            while (!Thread.currentThread().isInterrupted()) {
		                rpc.Discord_RunCallbacks();
		                ImpHackDiscordRichPresence.presence.details = Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu ? "In the main menu." : "Playing " + (Minecraft.getMinecraft().currentServerData != null ? (mod.imphack.module.modules.client.DiscordRPC.instance.showIp.isEnabled() ? "on " + Minecraft.getMinecraft().currentServerData.serverIP + "." : " multiplayer.") : " singleplayer.");
		                ImpHackDiscordRichPresence.presence.state = mod.imphack.module.modules.client.DiscordRPC.instance.state.getValue();
		               
		                rpc.Discord_UpdatePresence(presence);
		                try {
		                    Thread.sleep(2000L);
		                } catch (InterruptedException interruptedException) {
		                }
		            }
		        }, "RPC-Callback-Handler");
		        thread.start();
		}

	 public static void stop() {
	        if (thread != null && !thread.isInterrupted()) {
	            thread.interrupt();
	        }
	        rpc.Discord_Shutdown();
	    }
	}
