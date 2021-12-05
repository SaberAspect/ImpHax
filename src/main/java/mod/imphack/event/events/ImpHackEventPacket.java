package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public class ImpHackEventPacket extends ImpHackEventCancellable {
	private final Packet<?> packet;

	public ImpHackEventPacket(Packet<?> packet) {
		super();

		this.packet = packet;
	}

	 public <T extends Packet<?>> T getPacket() {
	        return (T)this.packet;
	    }
	 public Packet get_Packet() {
	        return this.packet;
	    }

	public static class ReceivePacket extends ImpHackEventPacket {
		public ReceivePacket(Packet<?> packet) {
			super(packet);
		}
	}

	public static class SendPacket extends ImpHackEventPacket {
		public SendPacket(Packet<?> packet) {
			super(packet);
		}

		
	}
	    	    @Cancelable
    public static class Send extends ImpHackEventPacket
	    {
	        public Send(final Packet<?> packet) {
	            super(packet);
	        }
	    }
	    
	    @Cancelable
	    public static class Receive extends ImpHackEventPacket
	    {
	        public Receive(final Packet<?> packet) {
	            super( packet);
	        }
	    }
	    
	    public static class Receive_new
	    extends ImpHackEventPacket {
	        public Receive_new(Packet packet) {
	            super(packet);
	        }
	    }
	    
	    public static class Send_new extends ImpHackEventPacket {
			public Send_new(Packet packet) {
				super(packet);
			}
		}
	}