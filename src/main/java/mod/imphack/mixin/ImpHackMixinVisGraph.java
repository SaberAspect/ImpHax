package mod.imphack.mixin;

import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventSetOpaqueCube;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VisGraph.class)
public class ImpHackMixinVisGraph {
	@Inject(method = "setOpaqueCube", at = @At("HEAD"), cancellable = true)
	public void setOpaqueCube(BlockPos pos, CallbackInfo info) {
		ImpHackEventSetOpaqueCube l_Event = new ImpHackEventSetOpaqueCube(); /// < pos is unused
		ImpHackEventBus.EVENT_BUS.post(l_Event);
		if (l_Event.isCancelled())
			info.cancel();
	}
}