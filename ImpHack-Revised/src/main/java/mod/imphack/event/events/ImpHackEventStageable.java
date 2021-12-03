package mod.imphack.event.events;
public class ImpHackEventStageable {

    private EventStage stage;

    public ImpHackEventStageable() {

    }

    public ImpHackEventStageable(EventStage stage) {
        this.stage = stage;
    }

    public EventStage getStage() {
        return stage;
    }

    public void setStage(EventStage stage) {
        this.stage = stage;
    }

    public enum EventStage {
        PRE, POST
    }

}