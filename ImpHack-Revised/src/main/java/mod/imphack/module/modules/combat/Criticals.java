package mod.imphack.module.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.event.events.ImpHackEventPacket;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Criticals extends Module {

	public Criticals() {
		super("Criticals", "Makes normal hits critical hits", Category.COMBAT);

		this.addSetting(mode);
	}

	final ModeSetting mode = new ModeSetting("Mode", this, "NCPStrict", "NCPStrict", "Packet", "Jump");

	final Minecraft mc = Minecraft.getMinecraft();
	CPacketUseEntity packet;

	

	@EventHandler
	private final Listener<ImpHackEventPacket.SendPacket> listener = new Listener<>(event -> {
		if (event.getPacket() instanceof CPacketUseEntity) {

			CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();

            if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {

                	if (mode.is("NCPStrict")) {
    					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
    							mc.player.posY + 0.062602401692772D, mc.player.posZ, false));
    					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
    							mc.player.posY + 0.0726023996066094D, mc.player.posZ, false));
    					mc.player.connection.sendPacket(
    							new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
    					
                	}
                	if(mode.is("Jump")) {
                		mc.player.jump();
    					mc.player.connection.sendPacket(
    							new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.05, mc.player.posZ, false));
    					mc.player.connection.sendPacket(
    							new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                	}

                	if(mode.is("Packet")) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                	}

                }
            }
		}

	});
}
