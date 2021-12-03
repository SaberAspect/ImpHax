package mod.imphack.module.modules.movement;

import me.zero.alpine.event.type.Cancellable;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.event.events.ImpHackEventPacket;
import mod.imphack.event.events.ImpHackEventPush;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {

	
	FloatSetting horizontal = new FloatSetting("Horizontal", this, 0.0f);
	FloatSetting vertical = new FloatSetting("Vertical", this, 0.0f);
	BooleanSetting noPush = new BooleanSetting("No Push", this, true);
	public Velocity() {
		super("Velocity", "Stops Knockback", Category.MOVEMENT);
		addSetting(horizontal,vertical,noPush);
	}

	@EventHandler
	private final Listener<ImpHackEventPacket.ReceivePacket> PacketEvent = new Listener<>(p_Event -> {
		if (p_Event.getPacket() instanceof SPacketEntityVelocity) {
			final SPacketEntityVelocity packet = (SPacketEntityVelocity) p_Event.getPacket();
			if (packet.getEntityID() == mc.player.getEntityId()) {
				p_Event.cancel();
				return;
			}
		}
		if (p_Event.getPacket() instanceof SPacketExplosion) {
			p_Event.cancel();
		}
	});

	@EventHandler
	private final Listener<ImpHackEventPush> PushEvent = new Listener<>(Cancellable::cancel);

	
	 public void onPush(ImpHackEventPush event) {
	        if (event.stage == 0 && this.noPush.isEnabled() && event.entity.equals(mc.player)) {
	            event.cancel();
	        }
	    }
}
