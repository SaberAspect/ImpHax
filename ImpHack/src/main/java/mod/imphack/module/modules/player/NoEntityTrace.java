package mod.imphack.module.modules.player;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class NoEntityTrace extends Module {
    public NoEntityTrace() {
        super("NoEntityTrace", "Allows you to mine through entities", Category.PLAYER);
    }

    boolean focus = false;

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null)
            return;

        mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> mc.player == entity).map(entity -> (EntityLivingBase) entity).filter(entity -> !(entity.isDead)).forEach(this::processHit);
        RayTraceResult normalResult = mc.objectMouseOver;

        if (normalResult != null)
            focus = normalResult.typeOfHit == RayTraceResult.Type.ENTITY;
    }

    private void processHit(EntityLivingBase event) {
        RayTraceResult bypassResult = event.rayTrace(6, mc.getRenderPartialTicks());

        if (bypassResult != null && focus) {
            if (bypassResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = bypassResult.getBlockPos();

                if (mc.gameSettings.keyBindAttack.isKeyDown())
                    mc.playerController.onPlayerDamageBlock(pos, EnumFacing.UP);
            }
        }
    }
}