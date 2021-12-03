package mod.imphack.module.modules.movement;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
* made by hausemasterissue, 21/10/2021
*/

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", "Prevents slowdown effects", Category.MOVEMENT);
	    
	this.addSetting(sneak);
    }
  
    final BooleanSetting sneak = new BooleanSetting("AirStrict", this, true);
  
    private boolean sneaking = false;
  
    public void onDisable() {
        if(sneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            sneaking = false; 
        }
    }
  
    public void onUpdate() {
        if(!mc.player.isHandActive() && sneak.isEnabled() && sneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            sneaking = false;
        }
    }
  
    @SubscribeEvent
	  public void onUpdateInput(final InputUpdateEvent event) {
		    if (mc.player.isHandActive() && !mc.player.isRiding() && !sneak.isEnabled()) {
			      event.getMovementInput().moveForward *= 5.0f;
			      event.getMovementInput().moveStrafe *= 5.0f;
		    }
	  }
  
    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        if (!sneaking && sneak.isEnabled()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            sneaking = true;
        }
    }
  
}
