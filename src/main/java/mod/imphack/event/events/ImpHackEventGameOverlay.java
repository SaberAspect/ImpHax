package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import net.minecraft.client.gui.ScaledResolution;

public class ImpHackEventGameOverlay extends ImpHackEventCancellable {

	public final float partial_ticks;
	private final ScaledResolution scaled_resolution;

	public ImpHackEventGameOverlay(float partial_ticks, ScaledResolution scaled_resolution) {

		this.partial_ticks = partial_ticks;
		this.scaled_resolution = scaled_resolution;

	}

	public ScaledResolution get_scaled_resoltion() {
		return scaled_resolution;
	}

}