package mod.imphack.event.events;
public class ImpHackEventCanceled extends ImpHackEventStageable {

    private boolean canceled;

    public ImpHackEventCanceled() {
    }

    public ImpHackEventCanceled(EventStage stage) {
        super(stage);
    }

    public ImpHackEventCanceled(EventStage stage, boolean canceled) {
        super(stage);
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}