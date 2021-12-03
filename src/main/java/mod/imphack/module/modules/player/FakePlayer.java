package mod.imphack.module.modules.player;

import com.mojang.authlib.GameProfile;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.StringSetting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FakePlayer extends Module {

	final StringSetting name = new StringSetting("FakePlayer name", this, "Jakethasnake52");

	public FakePlayer() {
		super("FakePlayer", "Spawns in a fake player", Category.PLAYER);
		addSetting(name);
	}

	public EntityOtherPlayerMP newPlayer;

	@Override
	public void onEnable() {
		if (mc.player != null && mc.world != null) {
			newPlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(null, name.value));
			newPlayer.copyLocationAndAnglesFrom(mc.player);
			newPlayer.rotationYawHead = mc.player.rotationYawHead;
			mc.world.addEntityToWorld(-100, newPlayer);
		} else {
			this.toggle();
		}
	}

	@Override
	public void onDisable() {
		if (mc.player != null && mc.world != null) {
			mc.world.removeEntity(newPlayer);
		}
	}

}