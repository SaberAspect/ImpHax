package mod.imphack.mixin;

import io.netty.channel.ChannelHandlerContext;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// External.

@Mixin(value = NetworkManager.class)
public class ImpHackMixinNetworkManager {
	// Receive packet.
	@Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
	private void receive(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callback) {
		ImpHackEventPacket event_packet = new ImpHackEventPacket.ReceivePacket(packet);

		ImpHackEventBus.EVENT_BUS.post(event_packet);

		if (event_packet.isCancelled()) {
			callback.cancel();
		}
	}

	// Send packet.
	@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
	private void send(Packet<?> packet, CallbackInfo callback) {
		ImpHackEventPacket event_packet = new ImpHackEventPacket.SendPacket(packet);

		ImpHackEventBus.EVENT_BUS.post(event_packet);

		if (event_packet.isCancelled()) {
			callback.cancel();
		}
	}

	// Exception packet.
	@Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
	private void exception(ChannelHandlerContext exc, Throwable exc_, CallbackInfo callback) {
		if (exc_ instanceof Exception) {
			callback.cancel();
		}
	}
}
