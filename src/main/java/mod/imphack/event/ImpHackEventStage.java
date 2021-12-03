package mod.imphack.event;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ImpHackEventStage extends Event
{
    private static int stage;
    
    public ImpHackEventStage() {
    }
    
    public ImpHackEventStage(final int stage) {
        this.stage = stage;
    }
    
    public static int getStage() {
        return stage;
    }
    
    public void setStage(final int stage) {
        this.stage = stage;
    }
}