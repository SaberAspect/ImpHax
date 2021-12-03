package mod.imphack.event;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import mod.imphack.Client;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class ImpHackEventConnection extends ChannelDuplexHandler {

    private final ImpHackEventHandler eventHandler;
    
    final Minecraft mc = Minecraft.getMinecraft();

    public ImpHackEventConnection(ImpHackEventHandler eventHandler) {
        this.eventHandler = eventHandler;
        try {
            ChannelPipeline pipeline = Objects.requireNonNull(mc.getConnection()).getNetworkManager().channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler", this);
        } catch (Exception exception) {
            Client.addChatMessage("Connection: Error on attaching");
            exception.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (eventHandler.onPacket(packet, Side.IN)) {
            return;
        }
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (eventHandler.onPacket(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }

    public enum Side {
        IN,
        OUT
    }
}
