package mod.imphack.module.modules.render;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.IntSetting;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class ExtraTab extends Module {
	public final IntSetting size = new IntSetting("Size", this, 1000);

	public ExtraTab() {
		super("ExtraTab", "Extends Tab Menu", Category.RENDER);

		addSetting(size);
	}

	public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
		return networkPlayerInfoIn.getDisplayName() != null
				? networkPlayerInfoIn.getDisplayName().getFormattedText()
				: ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(),
						networkPlayerInfoIn.getGameProfile().getName());
	}
}