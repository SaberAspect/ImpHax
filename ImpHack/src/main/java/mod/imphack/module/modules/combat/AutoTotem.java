package mod.imphack.module.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.Client;
import mod.imphack.container.ImpHackInventory;
import mod.imphack.event.events.ImpHackEventTotemPop;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.IntSetting;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class AutoTotem extends Module {

	final IntSetting delay = new IntSetting("Delay (ms)", this, 100);
	final BooleanSetting chat = new BooleanSetting("Chat Totem Pop", this, true);

	long timer = System.currentTimeMillis();

	public AutoTotem() {
		super("AutoTotem", "Places Totems In Offhand", Category.COMBAT);
		addSetting(delay);
		addSetting(chat);
	}

	@Override
	public void onUpdate() {
		if (timer < System.currentTimeMillis() - delay.getValue()) {
			if (mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
				for (int i = 36; i >= 0; i--) {
					final Item item = mc.player.inventory.getStackInSlot(i).getItem();
					if (item == Items.TOTEM_OF_UNDYING) {
						ImpHackInventory.putInOffhand(mc.player.inventory.getStackInSlot(i));
					}
				}
				timer = System.currentTimeMillis();
			}
		}
	}

	int totemCount = 0;

	@EventHandler
	private final Listener<ImpHackEventTotemPop> totemPopEvent = new Listener<>(p_Event -> {
		totemCount = 0;
		if (chat.enabled) {
			for (int i = 36; i >= 0; i--) {
				final Item item = mc.player.inventory.getStackInSlot(i).getItem();
				if (item == Items.TOTEM_OF_UNDYING) {
					totemCount++;
				}
			}

			if (System.currentTimeMillis() - timer <= 200)
				return;

			timer = System.currentTimeMillis();

			Client.addChatMessage("Totem Popped, " + totemCount + " totems left");
		}
	});

}
