package mod.imphack.module.modules.utilities;

import mod.imphack.container.ImpHackInventory;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.util.BlockInteractionUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ConcreteBot extends Module {

	final IntSetting x = new IntSetting("X", this, 0);
	final IntSetting y = new IntSetting("Y", this, 0);
	final IntSetting z = new IntSetting("Z", this, 0);

	final GuiButton setPos = new GuiButton(10001, 0, 0, 0, mc.fontRenderer.FONT_HEIGHT + 2, "Set Current Position");

	@Override
	public void actionPerformed(GuiButton b) {
		if (b.id == 10001) {
			x.value = mc.player.getPosition().getX();
			y.value = mc.player.getPosition().getY();
			z.value = mc.player.getPosition().getZ();
		}
	}

	public ConcreteBot() {
		super("ConcreteBot", "Turns Powder Into Concrete", Category.UTILITIES);

		addSetting(x);
		addSetting(y);
		addSetting(z);

		addButton(setPos);
	}

	private boolean breakBlock;

	@Override
	public void onUpdate() {

		int stage;

		if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe)) {
			for (int i = 0; i < 9; i++) {
				ItemStack stack = mc.player.inventory.getStackInSlot(i);
				if (stack.getItem() instanceof ItemPickaxe) {
					mc.player.inventory.currentItem = i;
					break;
				}
			}
		}

		if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe)) {
			this.toggle();
			return;
		}

		if (!(mc.player.getHeldItem(EnumHand.OFF_HAND)
				.getDisplayName().equals(new ItemStack(Item.getItemFromBlock(Blocks.CONCRETE_POWDER)).getDisplayName()))) {
			for (int i = 0; i < 36; i++) {
				ItemStack stack = mc.player.inventory.getStackInSlot(i);
				Item block = Item.getItemFromBlock(Blocks.CONCRETE_POWDER);

				if (stack.getDisplayName().equals(new ItemStack(block).getDisplayName())) {
					ImpHackInventory.putInOffhand(stack);
					break;
				}
			}
		}

		if (!(mc.player.getHeldItem(EnumHand.OFF_HAND)
				.getDisplayName().equals(new ItemStack(Item.getItemFromBlock(Blocks.CONCRETE_POWDER)).getDisplayName()))) {
			this.toggle();
			return;
		}

		if (mc.world.getBlockState(new BlockPos(x.value + 0.5, y.value + 0.5, z.value + 0.5)).getBlock()
				.equals(Blocks.CONCRETE)) {
			stage = 0;
		} else {
			stage = 1;
		}

		if (stage == 0) {
			float[] rotation = BlockInteractionUtil.getRotationsForPosition(x.value + 0.5, y.value + 0.5,
					z.value + 0.5);

			mc.player.rotationYaw = rotation[0];
			mc.player.rotationPitch = rotation[1];
			mc.player.rotationYawHead = rotation[0];

			breakBlock = true;

			return;
		}
		if (stage == 1) {
			float[] rotation = BlockInteractionUtil.getRotationsForPosition(x.value + 0.5, y.value + 0.5,
					z.value + 0.5);

			mc.player.rotationYaw = rotation[0];
			mc.player.rotationPitch = rotation[1];
			mc.player.rotationYawHead = rotation[0];

			mc.playerController.processRightClickBlock(mc.player, mc.world,
					new BlockPos(x.value + 0.5, y.value + 0.5, z.value + 0.5), EnumFacing.DOWN,
					mc.objectMouseOver.hitVec, EnumHand.OFF_HAND);

		}
	}

	@SubscribeEvent
	public void onTick(TickEvent event) {
		if (event.type != TickEvent.Type.CLIENT) {
			return;
		}
		if (mc.world == null || mc.player == null) {
			this.toggle();
			return;
		}

		if (breakBlock && mc.world.getBlockState(new BlockPos(x.value + 0.5, y.value + 0.5, z.value + 0.5)).getBlock()
				.equals(Blocks.CONCRETE)) {
			RayTraceResult trace = mc.objectMouseOver;
			boolean isBlockTrace = trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK;

			if (isBlockTrace) {
				if (mc.playerController.onPlayerDamageBlock(trace.getBlockPos(), trace.sideHit)) {
					mc.player.swingArm(EnumHand.MAIN_HAND);
				}
			}
		}
	}
}
