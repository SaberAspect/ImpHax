package mod.imphack.util;


import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;
import java.util.concurrent.LinkedBlockingQueue;

import mod.imphack.event.events.ImpHackEventPacket;
import mod.imphack.event.events.ImpHackEventRotation;
import mod.imphack.misc.Rotation;
import mod.imphack.misc.Rotation.RotationMode;
import mod.imphack.misc.RotationPriority;

public class RotationUtil {
	   private static float yaw;
	   private static float pitch;
	static Minecraft mc = Minecraft.getMinecraft();

    public RotationUtil() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static LinkedBlockingQueue<Rotation> rotationQueue = new LinkedBlockingQueue<>();
    public static Rotation serverRotation = null;
    public static Rotation currentRotation = null;

    public static float yawleftOver = 0;
    public static float pitchleftOver = 0;

    public static int tick = 5;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        rotationQueue.stream().sorted(Comparator.comparing(rotation -> rotation.rotationPriority.getPriority()));

        if (currentRotation != null)
            currentRotation = null;

        if (!rotationQueue.isEmpty()) {
            currentRotation = rotationQueue.poll();
            currentRotation.updateRotations();
        }

        tick++;
    }

    @SubscribeEvent
    public void onRotate(ImpHackEventRotation event) {
        try {
            if (currentRotation != null && currentRotation.mode.equals(RotationMode.Packet)) {
                event.setCanceled(true);

                if (tick == 1) {
                    event.setYaw(currentRotation.yaw + yawleftOver);
                    event.setPitch(currentRotation.pitch + pitchleftOver);
                }

                else {
                    event.setYaw(currentRotation.yaw);
                    event.setPitch(currentRotation.pitch);
                }
            }
        } catch (Exception ignored) {

        }
    }

    @SubscribeEvent
    public void onPacketSend(ImpHackEventPacket.SendPacket event) {
        if (currentRotation != null && !rotationQueue.isEmpty() && event.getPacket() instanceof CPacketPlayer) {
            if (((CPacketPlayer) event.getPacket()).rotating)
                serverRotation = new Rotation(((CPacketPlayer) event.getPacket()).yaw, ((CPacketPlayer) event.getPacket()).pitch, RotationMode.Packet, RotationPriority.Lowest);
        }
    }
    
    public static void updateRotations() {
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;
     }

     public static void restoreRotations() {
        mc.player.rotationYaw = yaw;
        mc.player.rotationYawHead = yaw;
        mc.player.rotationPitch = pitch;
     }


    public static void resetTicks() {
        tick = 0;
    }
    
    public static void setPlayerRotations(final float yaw, final float pitch) {
        mc.player.rotationYaw = yaw;
        mc.player.rotationYawHead = yaw;
        mc.player.rotationPitch = pitch;
     }
}