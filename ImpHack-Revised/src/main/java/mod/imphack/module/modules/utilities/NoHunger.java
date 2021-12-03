package mod.imphack.module.modules.utilities;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.event.events.ImpHackEventPacket;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.ModeSetting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraft.network.play.client.CPacketEntityAction.Action.START_SPRINTING;
import static net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SPRINTING;

public class NoHunger extends Module {

   ModeSetting mode = new ModeSetting("Mode", this, "Packet", "Packet", "Vanilla");


	public NoHunger() {
		super("NoHunger", "Prevents You From Losing Hunger", Category.UTILITIES);

		addSetting(mode);
	}

	@Override
	public void onUpdate() {
        if (mode.getMode() == "Vanilla")
            mc.player.getFoodStats().setFoodLevel(20);
    }

	@EventHandler
	private final Listener<ImpHackEventPacket.ReceivePacket> PacketEvent = new Listener<>(p_Event -> {
        if (mode.getMode() == "Packet") {
            if (p_Event.getPacket() instanceof CPacketPlayer)
                ((CPacketPlayer) p_Event.getPacket()).onGround = (mc.player.fallDistance > 0 || mc.playerController.isHittingBlock);

            if (p_Event.getPacket() instanceof CPacketEntityAction && ((CPacketEntityAction) p_Event.getPacket()).getAction() == START_SPRINTING || ((CPacketEntityAction) p_Event.getPacket()).getAction() == STOP_SPRINTING)
                p_Event.setCanceled(true);
        }
    });
}
