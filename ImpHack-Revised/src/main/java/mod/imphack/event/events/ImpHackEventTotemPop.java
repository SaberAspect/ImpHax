package mod.imphack.event.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ImpHackEventTotemPop extends Event {
	
	 private final EntityPlayer player;

	    public ImpHackEventTotemPop(EntityPlayer player) {
	        this.player = player;
	    }

	    public EntityPlayer getPlayer() {
	        return player;
	    }
	}
