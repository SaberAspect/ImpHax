package mod.imphack.util;

import java.util.LinkedList;

public final class FPSManager {

    private int fps;
    private final LinkedList frames = new LinkedList();

    public void update() {
        long time = System.nanoTime();

        this.frames.add(Long.valueOf(time));

        while (true) {
            long f = ((Long) this.frames.getFirst()).longValue();
            long ONE_SECOND = 1000000000L;

            if (time - f <= 1000000000L) {
                this.fps = this.frames.size();
                return;
            }

            this.frames.remove();
        }
    }

    public int getFPS() {
        return this.fps;
    }

    public float getFrametime() {
        return 1.0F / (float) this.fps;
    }
}