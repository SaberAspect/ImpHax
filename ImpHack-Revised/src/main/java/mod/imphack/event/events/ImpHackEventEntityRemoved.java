package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import net.minecraft.entity.Entity;

public class ImpHackEventEntityRemoved extends ImpHackEventCancellable {
    
    private final Entity entity;

    public ImpHackEventEntityRemoved(Entity entity) {
        this.entity = entity;
    }

    public Entity get_entity() {
        return this.entity;
    }
}