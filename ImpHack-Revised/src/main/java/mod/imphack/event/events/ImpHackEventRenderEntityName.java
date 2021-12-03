package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import net.minecraft.client.entity.AbstractClientPlayer;

public class ImpHackEventRenderEntityName extends ImpHackEventCancellable {
	public final AbstractClientPlayer Entity;
	public double X;
	public double Y;
	public double Z;
	public final String Name;
	public final double DistanceSq;

	public ImpHackEventRenderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
		super();

		Entity = entityIn;
        x = X;
        y = Y;
        z = Z;
        Name = name;
        DistanceSq = distanceSq;
	}

}