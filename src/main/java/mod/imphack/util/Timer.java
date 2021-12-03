package mod.imphack.util;

import java.util.LinkedList;

public class Timer {
	private long time;
	private long lastMS = 0L;
	private long current;

	public Timer() {
		this.time = -1L;
		this.current = System.currentTimeMillis();

		
	}

	public boolean getPassedSeconds(final double s) {
		return this.getPassedMillis((long) s * 1000L);
	}

	public boolean getPassedMillis(final long ms) {
		return this.getPassedNanos(this.convertToNano(ms));
	}

	public void setMillis(final long ms) {
		this.time = System.nanoTime() - this.convertToNano(ms);
	}

	public boolean getPassedNanos(final long ns) {
		return System.nanoTime() - this.time >= ns;
	}

	public long getPassedTimeMs() {
		return this.convertToMillis(System.nanoTime() - this.time);
	}

	public
	void reset() {
		this.time = System.nanoTime();
	}
	public
	void resetCurrent() {
		this.current = System.currentTimeMillis();
	}
	
	
	public long convertToMillis(final long time) {
		return time / 1000000L;
	}

	public long convertToNano(final long time) {
		return time * 1000000L;
	}
	public boolean isDelay(long delay) {
        return System.currentTimeMillis() - lastMS >= delay;
    }
	
    public long getCurrentMS(){
		return System.nanoTime() / 1000000L;
	}
	
    public void setLastMS(long lastMS) {
		this.lastMS = lastMS;
	}
	
    public void setLastMS() {
		this.lastMS = System.currentTimeMillis();
	}
    
    public long getTimePassed(){
		return System.currentTimeMillis() - this.current;
	}
    public boolean passedMs(final long ms) {
        return this.convertToMillis(System.nanoTime() - this.time) >= ms;
    }
       
}