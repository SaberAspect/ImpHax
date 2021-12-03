package mod.imphack.container;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class ImpHackInventory {

	public static void putInOffhand(ItemStack stack) {
		int slot = Minecraft.getMinecraft().player.inventory.getSlotFor(stack);
		Minecraft.getMinecraft().playerController.windowClick(
				Minecraft.getMinecraft().player.inventoryContainer.windowId, slot < 9 ? slot + 36 : slot, 0,
				ClickType.PICKUP, Minecraft.getMinecraft().player);
		Minecraft.getMinecraft().playerController.windowClick(
				Minecraft.getMinecraft().player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP,
				Minecraft.getMinecraft().player);
		Minecraft.getMinecraft().playerController.windowClick(
				Minecraft.getMinecraft().player.inventoryContainer.windowId, slot < 9 ? slot + 36 : slot, 0,
				ClickType.PICKUP, Minecraft.getMinecraft().player);

		Minecraft.getMinecraft().playerController.updateController();
	}
}
