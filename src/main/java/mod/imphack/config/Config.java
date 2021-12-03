package mod.imphack.config;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map.Entry;

import mod.imphack.Main;
import mod.imphack.module.Module;
import mod.imphack.module.modules.render.Search;
import mod.imphack.setting.Setting;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.ColorSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.setting.settings.SearchBlockSelectorSetting;
import mod.imphack.setting.settings.StringSetting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.registry.GameRegistry;

// config issue?

public class Config {
	public static Path modFolder;
	private final Path configFile;

	public Config() {
		modFolder = Minecraft.getMinecraft().gameDir.toPath().resolve("ImpHax");
		configFile = Paths.get(modFolder + File.separator + "moduleConfig.txt");
		try {
			Files.createDirectories(modFolder);
			if (!Files.exists(configFile))
				Files.createFile(configFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void Save() {

		ArrayList<String> save = new ArrayList<>();

		for (Module mod : Main.moduleManager.modules) {
			if (mod.getName().equalsIgnoreCase("ClickGUI")) {
				save.add("module:" + mod.getName() + ":false:" + mod.getBind());
			} else {
				save.add("module:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getBind());
			}
		}

		for (Module mod : Main.moduleManager.modules) {
			for (Setting setting : mod.settings) {
				if (setting instanceof BooleanSetting) {
					BooleanSetting bool = (BooleanSetting) setting;
					save.add("setting:" + mod.getName() + ":" + setting.name + ":" + bool.isEnabled());
				}

				if (setting instanceof IntSetting) {
					IntSetting num = (IntSetting) setting;
					save.add("setting:" + mod.getName() + ":" + setting.name + ":" + num.getValue());
				}

				if (setting instanceof FloatSetting) {
					FloatSetting num = (FloatSetting) setting;
					save.add("setting:" + mod.getName() + ":" + setting.name + ":" + num.getValue());
				}

				if (setting instanceof StringSetting) {
					StringSetting val = (StringSetting) setting;
					save.add("setting:" + mod.getName() + ":" + setting.name + ":" + val.getValue());
				}

				if (setting instanceof ModeSetting) {
					ModeSetting mode = (ModeSetting) setting;
					save.add("setting:" + mod.getName() + ":" + setting.name + ":" + mode.getMode());
				}

				if (setting instanceof ColorSetting) {
					ColorSetting col = (ColorSetting) setting;
					save.add("setting:" + mod.getName() + ":" + setting.name + ":" + col.red + "," + col.green + ","
							+ col.blue);
				}

				if (setting instanceof SearchBlockSelectorSetting) {
					SearchBlockSelectorSetting block = (SearchBlockSelectorSetting) setting;
					StringBuilder list = new StringBuilder();
					StringBuilder colorList = new StringBuilder();
					for (Block b : block.blocks) {
						list.append(b.getLocalizedName()).append("/");
					}
					for (Entry<Block, Integer> e : block.colors.entrySet()) {
						colorList.append(new Color(e.getValue()).getRed()).append(".")
								.append(new Color(e.getValue()).getGreen()).append(".")
								.append(new Color(e.getValue()).getBlue()).append(",")
								.append(e.getKey().getLocalizedName()).append("/");
					}
					save.add("setting:" + mod.getName() + ":" + setting.name + ":" + list + ";" + colorList);
				}
			}
		}

		try {
			PrintWriter pw = new PrintWriter(this.configFile.toFile());
			for (String str : save) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void Load() {
		ArrayList<String> lines = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.configFile.toFile()));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String s : lines) {
			String[] args = s.split(":");
			if (s.toLowerCase().startsWith("module:")) {
				Module m = Main.moduleManager.getModule(args[1]);
				try {
					m.setBind(Integer.parseInt(args[3]));
					m.setToggled(Boolean.parseBoolean(args[2]));
				} catch (NullPointerException e) {
					System.out.println("Module in config file does not exist");
					e.printStackTrace();
				}
			} else if (s.toLowerCase().startsWith("setting:")) {
				Module m = Main.moduleManager.getModule(args[1]);
				if (m != null) {
					Setting setting = Main.settingManager.getSettingByName(m, args[2]);
					if (setting != null) {
						Main.settingManager.addSetting(setting);
					}
				}
			}
		}
		ArrayList<String> save = new ArrayList<>(lines);
		for (Module mod : Main.moduleManager.getModuleList()) {
			for (Setting setting : mod.settings) {
				if (!Main.settingManager.getSettings().contains(setting)) {

					if (setting instanceof BooleanSetting) {
						BooleanSetting bool = (BooleanSetting) setting;
						save.add("setting:" + mod.getName() + ":" + setting.name + ":" + bool.isEnabled());
					}

					if (setting instanceof IntSetting) {
						IntSetting num = (IntSetting) setting;
						save.add("setting:" + mod.getName() + ":" + setting.name + ":" + num.getValue());
					}

					if (setting instanceof FloatSetting) {
						FloatSetting num = (FloatSetting) setting;
						save.add("setting:" + mod.getName() + ":" + setting.name + ":" + num.getValue());
					}

					if (setting instanceof StringSetting) {
						StringSetting val = (StringSetting) setting;
						save.add("setting:" + mod.getName() + ":" + setting.name + ":" + val.getValue());
					}

					if (setting instanceof ModeSetting) {
						ModeSetting mode = (ModeSetting) setting;
						save.add("setting:" + mod.getName() + ":" + setting.name + ":" + mode.getMode());
					}
					if (setting instanceof ColorSetting) {
						ColorSetting col = (ColorSetting) setting;
						save.add("setting:" + mod.getName() + ":" + setting.name + ":" + col.red + "," + col.green + ","
								+ col.blue);
					}

					if (setting instanceof SearchBlockSelectorSetting) {
						SearchBlockSelectorSetting block = (SearchBlockSelectorSetting) setting;
						StringBuilder list = new StringBuilder();
						StringBuilder colorList = new StringBuilder();
						for (Block b : block.blocks) {
							list.append(b.getLocalizedName()).append("/");
						}
						for (Entry<Block, Integer> e : block.colors.entrySet()) {
							colorList.append(new Color(e.getValue()).getRed()).append(".")
									.append(new Color(e.getValue()).getGreen()).append(".")
									.append(new Color(e.getValue()).getBlue()).append(",")
									.append(e.getKey().getLocalizedName()).append("/");
						}
						save.add("setting:" + mod.getName() + ":" + setting.name + ":" + list + ";" + colorList);
					}
				}
			}
		}
		try {
			PrintWriter pw = new PrintWriter(this.configFile.toFile());
			for (String str : save) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Main.settingManager.clearSettings();

		lines = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.configFile.toFile()));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String s : lines) {
			String[] args = s.split(":");
			if (s.toLowerCase().startsWith("module:")) {
				Module m = Main.moduleManager.getModule(args[1]);
				if (m != null) {
					m.setBind(Integer.parseInt(args[3]));
					m.setToggled(Boolean.parseBoolean(args[2]));
				}
			} else if (s.toLowerCase().startsWith("setting:")) {
				Module m = Main.moduleManager.getModule(args[1]);
				if (m != null) {
					Setting setting = Main.settingManager.getSettingByName(m, args[2]);
					if (setting != null) {
						Main.settingManager.addSetting(setting);
						if (setting instanceof BooleanSetting) {
							((BooleanSetting) setting).setEnabled(Boolean.parseBoolean(args[3]));
						}

						if (setting instanceof IntSetting) {
							((IntSetting) setting).setValue(Integer.parseInt(args[3]));
						}

						if (setting instanceof FloatSetting) {
							((FloatSetting) setting).setValue(Float.parseFloat(args[3]));
						}

						if (setting instanceof StringSetting) {
							((StringSetting) setting).setValue((args[3]));
						}

						if (setting instanceof ModeSetting) {
							((ModeSetting) setting).setMode(args[3]);
						}

						if (setting instanceof ColorSetting) {
							((ColorSetting) setting).red = Integer.parseInt(args[3].split(",")[0]);
							((ColorSetting) setting).green = Integer.parseInt(args[3].split(",")[1]);
							((ColorSetting) setting).blue = Integer.parseInt(args[3].split(",")[2]);
						}

						if (setting instanceof SearchBlockSelectorSetting) {
							if (args.length > 3) {
								try {
									if (args[3].contains(";")) {
										for (String str : args[3].split(";")[0].split("/")) {
											if (!str.isEmpty()) {
												for (Block b : GameRegistry.findRegistry(Block.class)
														.getValuesCollection()) {
													if (str.equalsIgnoreCase(b.getLocalizedName()))
														((SearchBlockSelectorSetting) setting).blocks.add(b);
												}
											}
										}
										for (String str : args[3].split(";")[1].split("/")) {
											if (!str.isEmpty()) {
												for (Block b : GameRegistry.findRegistry(Block.class)
														.getValuesCollection()) {
													if (str.split(",")[1].equalsIgnoreCase(b.getLocalizedName())) {
														String colors = str.split(",")[0];
														((SearchBlockSelectorSetting) setting).setColor(b,
																new Color(Integer.parseInt(colors.split("\\.")[0]),
																		Integer.parseInt(colors.split("\\.")[1]),
																		Integer.parseInt(
																				colors.split("\\.")[2].split(",")[0]))
																						.getRGB());
													}
												}
											}
										}
									} else {
										for (String str : args[3].split("/")) {
											if (!str.isEmpty()) {
												for (Block b : GameRegistry.findRegistry(Block.class)
														.getValuesCollection()) {
													if (str.equalsIgnoreCase(b.getLocalizedName()))
														((SearchBlockSelectorSetting) setting).blocks.add(b);
												}
											}
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									System.out.println(
											"Config issue for search module, reverting to default search config");
								}
							}
							((Search) setting.parent).to_search.clear();

							for (Block b : ((SearchBlockSelectorSetting) setting).getBlocks()) {
								int color;
								color = ((SearchBlockSelectorSetting) setting).getColor(b);
								((Search) setting.parent).to_search.put(b, color);
							}
						}
					}
				}
			}
		}
		Main.configLoaded = true;
	}
}
