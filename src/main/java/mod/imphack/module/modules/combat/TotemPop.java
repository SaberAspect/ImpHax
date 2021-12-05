package mod.imphack.module.modules.combat;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.Client;
import mod.imphack.event.events.ImpHackEventPacket;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

import java.util.HashMap;

import com.mojang.realmsclient.gui.ChatFormatting;


public class TotemPop extends Module {
    
    

    public TotemPop() {
		super("TotemPop", "TotemPop", Category.COMBAT);
		// TODO Auto-generated constructor stub
	}

	public static final HashMap<String, Integer> totem_pop_counter = new HashMap<String, Integer>();
    
    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.GREEN;
    public static ChatFormatting gold = ChatFormatting.GOLD;
    public static ChatFormatting grey = ChatFormatting.GRAY;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;

    
    
    
    @EventHandler
    private final Listener<ImpHackEventPacket.ReceivePacket> packet_event = new Listener<>(event -> {

        if (event.getPacket() instanceof SPacketEntityStatus) {

            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();

            if (packet.getOpCode() == 35) {

                Entity entity = packet.getEntity(mc.world);

                int count = 1;

                if (totem_pop_counter.containsKey(entity.getName())) {
                    count = totem_pop_counter.get(entity.getName());
                    totem_pop_counter.put(entity.getName(), ++count);
                } else {
                    totem_pop_counter.put(entity.getName(), count);
                }

                if (entity == mc.player) return;

                Client.addChatMessage( red + "" + bold + " TotemPop " + reset + grey + " > " + reset + "dude, " + bold + red + entity.getName() + reset + " has popped " + bold + count + reset + " totems. what a loser", false);
                }

            }

    });

    @Override
	public void onUpdate() {
        
        for (EntityPlayer player : mc.world.playerEntities) {

            if (!totem_pop_counter.containsKey(player.getName())) continue;

            if (player.isDead || player.getHealth() <= 0) {

                int count = totem_pop_counter.get(player.getName());

                totem_pop_counter.remove(player.getName());

                if (player == mc.player) continue;

                    Client.addChatMessage( red + "" + bold + " TotemPop " + reset + grey + " > " + reset + "dude, " + bold + red + player.getName() + reset + " just fucking DIED after popping " + bold + count + reset + " totems", false);

            }

        }

	}

}