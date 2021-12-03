package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import net.minecraft.entity.MoverType;


public class ImpHackEventMove extends ImpHackEventCancellable {

	private MoverType move_type;

	public double x, y, z;

	public ImpHackEventMove(MoverType type, double x, double y, double z) {
		super();

		this.move_type = type;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	   

	public void set_move_type(MoverType type) {
		this.move_type = type;
	}

	public void set_x(double x) {
		this.x = x;
	}

	public void set_y(double y) {
		this.y = y;
	}

	public void set_z(double z) {
		this.z = z;
	}

	public MoverType get_move_type() {
		return this.move_type;
	}

	public double get_x() {
		return this.x;
	}

	public double get_y() {
		return this.y;
	}

	public double get_z() {
		return this.z;
	}
}