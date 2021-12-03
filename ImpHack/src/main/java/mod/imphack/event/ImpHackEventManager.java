package mod.imphack.event;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import mod.imphack.Client;
import mod.imphack.Main;
import mod.imphack.command.Command;
import mod.imphack.event.events.ImpHackEventGameOverlay;
import mod.imphack.event.events.ImpHackEventPacket;
import mod.imphack.event.events.ImpHackEventTotemPop;
import mod.imphack.module.Module;
import mod.imphack.module.modules.utilities.Reconnect;
import mod.imphack.ui.clickgui.ClickGuiController;
import mod.imphack.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

public class ImpHackEventManager implements Listenable {
	private final Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.isCanceled()) {
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null) {
			return;
		}

		Main.moduleManager.update();
	}

	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (event.isCanceled()) {
			return;
		}
		Main.moduleManager.render(event);
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {

		if (event.isCanceled()) {
			return;
		}

		ImpHackEventBus.EVENT_BUS.post(new ImpHackEventGameOverlay(event.getPartialTicks(), new ScaledResolution(mc)));

		RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.EXPERIENCE;

		if (!mc.player.isCreative() && mc.player.getRidingEntity() instanceof AbstractHorse) {
			target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
		}

		if (event.getType() == target) {
		}
	}

	@SubscribeEvent
	public void onInputUpdate(InputUpdateEvent event) {
		ImpHackEventBus.EVENT_BUS.post(event);
	}

	@SubscribeEvent
	public void key(KeyInputEvent k) {
		if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null)
			return;
		try {
			if (Keyboard.isCreated()) {
				if (Keyboard.getEventKeyState()) {
					int keyCode = Keyboard.getEventKey();
					if (keyCode <= 0)
						return;
					for (Module m : Main.moduleManager.modules) {
						if (m.toggled)
							m.onKeyPress();
						if (m.getBind() == keyCode && keyCode > 0) {
							m.toggle();
							return;
						}
					}
					if (keyCode == Keyboard.KEY_PERIOD) {
						mc.displayGuiScreen(new GuiChat("."));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void tickKeybind(TickEvent event) {
		if (Client.getNextKeyPressForKeybinding) {
			for (int i = 0; i < Keyboard.getKeyCount(); i++) {
				if (Keyboard.isKeyDown(i)) {
					Client.getNextKeyPressForKeybinding = false;
					Client.keybindModule.setBind(i);
					Client.keybindModule = null;
					ClickGuiController.INSTANCE.settingController.refresh(false);
					Main.config.Save();
					return;
				}
			}
		}
	}

	 @SubscribeEvent(priority = EventPriority.HIGHEST)
	    public void onChatSent(final ClientChatEvent event) {
	        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
	            event.setCanceled(true);
	            try {
	                Wrapper.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
	                if (event.getMessage().length() > 1) {
	                    Main.cmdManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
	                }
	                else {
	                   Client.addChatMessage("Please enter a command.");
	                }
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	                Client.addChatMessage("An error occurred while running this command. Check the log!");
	            }
	        }
	    }

	@EventHandler
	private final Listener<ImpHackEventPacket.ReceivePacket> PacketRecvEvent = new Listener<>(p_Event -> {
		
		if (mc.player != null || mc.world != null) {
            if (p_Event.getPacket() instanceof SPacketEntityStatus) {
                SPacketEntityStatus packet = (SPacketEntityStatus) p_Event.getPacket();
                if (packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                    MinecraftForge.EVENT_BUS.post(new ImpHackEventTotemPop((EntityPlayer) packet.getEntity(mc.world)));
                }
            } else if (p_Event.getPacket() instanceof SPacketPlayerListItem) {
                SPacketPlayerListItem packet = (SPacketPlayerListItem) p_Event.getPacket();
                if (packet.getAction() != SPacketPlayerListItem.Action.ADD_PLAYER && packet.getAction() != SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                    return;
                }
                
            }
		}
	});

	@EventHandler
	private final Listener<ImpHackEventPacket.SendPacket> PacketSendEvent = new Listener<>(p_Event -> {
	});

	@SubscribeEvent
	public void sendPacket(GuiOpenEvent event) {
		if (event.getGui() instanceof GuiDisconnected) {
			ServerData data = Minecraft.getMinecraft().getCurrentServerData();
			if (data != null) {
				((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData = data;
			}
		}
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		ServerData data = Minecraft.getMinecraft().getCurrentServerData();
		Module freecam = Main.moduleManager.getModule("freecam");
		if (freecam.isToggled())
			freecam.setToggled(false);
		if (data != null) {
			((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData = data;
		}
	}
}
