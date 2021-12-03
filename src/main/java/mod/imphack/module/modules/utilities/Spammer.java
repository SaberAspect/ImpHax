package mod.imphack.module.modules.utilities;

import mod.imphack.Client;
import mod.imphack.config.Config;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.util.Timer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Spammer extends Module {

	final IntSetting delay = new IntSetting("Delay(s)", this, 300);

	public Spammer() {
		super("Spammer", "Spams in the Chat", Category.UTILITIES);

		addSetting(delay);
	}

	private Path spammerFile = null;

	final Timer timer = new Timer();

	@Override
	public void onEnable() {
		spammerFile = Paths.get(Config.modFolder.toString() + File.separator + "spammer.txt");
		try {
			Files.createDirectories(Config.modFolder);
			if (!Files.exists(spammerFile))
				Files.createFile(spammerFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.spammerFile.toFile()));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			this.toggle();
			spammerFile = Paths.get(Config.modFolder.toString() + File.separator + "spammer.txt");
			Client.addChatMessage("couldn't find the spammer file, turning spammer off.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		timer.reset();
	}

	final ArrayList<String> lines = new ArrayList<>();
	int index = 0;

	@Override
	public void onUpdate() {
		if (spammerFile == null) {
			spammerFile = Paths.get(Config.modFolder.toString() + File.separator + "spammer.txt");
			return;
		}

		if (lines == null || lines.isEmpty()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(this.spammerFile.toFile()));
				String line = reader.readLine();
				while (line != null) {
					lines.add(line);
					line = reader.readLine();
				}
				reader.close();
			} catch (FileNotFoundException e) {
				this.toggle();
				spammerFile = Paths.get(Config.modFolder.toString() + File.separator + "spammer.txt");
				Client.addChatMessage("couldn't find the spammer file, turning spammer off.");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (timer.getPassedTimeMs() / 1000 >= delay.value) {
				timer.reset();
				if (index == lines.size())
					index = 0;
				mc.player.sendChatMessage(lines.get(index));
				index++;
			}
		}
	}
}
