package mod.imphack.event.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ImpHackEventDeath extends Event {
    private final EntityPlayer player;

    public ImpHackEventDeath(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }
}