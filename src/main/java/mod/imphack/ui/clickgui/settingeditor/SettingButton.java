package mod.imphack.ui.clickgui.settingeditor;

import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventSettings;
import mod.imphack.misc.StringParser;
import mod.imphack.module.Module;
import mod.imphack.setting.Setting;
import mod.imphack.setting.settings.*;
import mod.imphack.ui.clickgui.ClickGuiController;
import mod.imphack.ui.clickgui.settingeditor.search.BlockSelectorGuiController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

public class SettingButton {
	private GuiTextField textField;

	// color setting only
	private GuiTextField cTextFieldRed;
	private GuiTextField cTextFieldGreen;
	private GuiTextField cTextFieldBlue;

	final int x;
	int y;
	final int width;
	final int height;

	final Module module;

	final SettingFrame parent;

	final Minecraft mc = Minecraft.getMinecraft();

	final Setting setting;

	ModeSetting mSetting;
	IntSetting iSetting;
	FloatSetting fSetting;
	BooleanSetting bSetting;
	StringSetting sSetting;
	ColorSetting cSetting;
	SearchBlockSelectorSetting blockSetting;

	BlockSelectorGuiController blockController;

	public SettingButton(Module module, Setting setting, int x, int y, SettingFrame parent) {

		this.module = module;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = parent.width;
		this.height = 14;
		this.setting = setting;

		if (setting instanceof ModeSetting) {
			this.mSetting = (ModeSetting) setting;
		} else if (setting instanceof IntSetting) {
			this.iSetting = (IntSetting) setting;
		} else if (setting instanceof FloatSetting) {
			this.fSetting = (FloatSetting) setting;
		} else if (setting instanceof StringSetting) {
			this.sSetting = (StringSetting) setting;
		} else if (setting instanceof BooleanSetting) {
			this.bSetting = (BooleanSetting) setting;
		} else if (setting instanceof ColorSetting) {
			this.cSetting = (ColorSetting) setting;
		} else if (setting instanceof SearchBlockSelectorSetting) {
			this.blockSetting = (SearchBlockSelectorSetting) setting;
		}
	}

	public void draw(int mouseX, int mouseY) {

		for (Map.Entry<GuiTextField, Module> entry : SettingController.textFields.entrySet()) {
			GuiTextField t = entry.getKey();
			if (entry.getValue() == module) {
				if (t.height == mc.fontRenderer.FONT_HEIGHT + 2
						&& t.width == 380 - mc.fontRenderer.getStringWidth(setting.name + ": ")
						&& t.x == x + 2 + mc.fontRenderer.getStringWidth(setting.name + ": ") && t.y == this.y) {
					textField = t;
				}
			}
		}

		for (Entry<GuiTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
			GuiTextField[] t = entry.getKey();
			GuiTextField r = t[0];
			GuiTextField g = t[1];
			GuiTextField b = t[2];
			if (entry.getValue().getKey() == module) {
				if (r.height == mc.fontRenderer.FONT_HEIGHT + 2
						&& r.width == 380 - mc.fontRenderer.getStringWidth(setting.name + ": ")
						&& r.x == x + 2 + mc.fontRenderer.getStringWidth(setting.name + ": ")) {
					cTextFieldRed = r;
				}
				if (g.height == mc.fontRenderer.FONT_HEIGHT + 2
						&& g.width == 380 - mc.fontRenderer.getStringWidth(setting.name + ": ")
						&& g.x == x + 2 + mc.fontRenderer.getStringWidth(setting.name + ": ")) {
					cTextFieldGreen = g;
				}
				if (b.height == mc.fontRenderer.FONT_HEIGHT + 2
						&& b.width == 380 - mc.fontRenderer.getStringWidth(setting.name + ": ")
						&& b.x == x + 2 + mc.fontRenderer.getStringWidth(setting.name + ": ")) {
					cTextFieldBlue = b;
				}
			}
		}

		if (setting instanceof ModeSetting) {
			mc.fontRenderer.drawString(mSetting.name + ": " + mSetting.getMode(), x + 2, y + 2,
					new Color(255, 255, 255).getRGB());
		} else if (setting instanceof IntSetting) {
			mc.fontRenderer.drawString(iSetting.name + ": ", x + 2, y + 2, new Color(255, 255, 255).getRGB());
			int text = mc.fontRenderer.drawString(Integer.toString(iSetting.value),
					x + 2 + mc.fontRenderer.getStringWidth(iSetting.name + ": "), y + 2,
					new Color(255, 255, 255).getRGB());
			if (this.textField == null) {
				this.textField = new GuiTextField(text, mc.fontRenderer,
						x + 2 + mc.fontRenderer.getStringWidth(iSetting.name + ": "), y,
						380 - mc.fontRenderer.getStringWidth(iSetting.name + ": "), mc.fontRenderer.FONT_HEIGHT + 2);
				textField.setEnabled(true);
				SettingController.textFields.put(textField, module);
				textField.setText(Integer.toString(iSetting.value));
			}
			if (textField.isFocused()) {
				if (StringParser.isInteger(textField.getText())) {
					iSetting.value = Integer.parseInt(textField.getText());
					textField.setTextColor(new Color(0, 255, 0).getRGB());

				} else {
					textField.setTextColor(new Color(255, 0, 0).getRGB());
				}
			}
		} else if (setting instanceof FloatSetting) {
			mc.fontRenderer.drawString(fSetting.name + ": ", x + 2, y + 2, new Color(255, 255, 255).getRGB());
			int text = mc.fontRenderer.drawString(Float.toString(fSetting.value),
					x + 2 + mc.fontRenderer.getStringWidth(fSetting.name + ": "), y + 2,
					new Color(255, 255, 255).getRGB());
			if (this.textField == null) {
				this.textField = new GuiTextField(text, mc.fontRenderer,
						x + 2 + mc.fontRenderer.getStringWidth(fSetting.name + ": "), y,
						380 - mc.fontRenderer.getStringWidth(fSetting.name + ": "), mc.fontRenderer.FONT_HEIGHT + 2);
				textField.setEnabled(true);
				SettingController.textFields.put(textField, module);
				textField.setText(Float.toString(fSetting.value));
			}
			if (textField.isFocused()) {
				if (StringParser.isFloat(textField.getText())) {
					fSetting.value = Float.parseFloat(textField.getText());
					textField.setTextColor(new Color(0, 255, 0).getRGB());
				} else {
					textField.setTextColor(new Color(255, 0, 0).getRGB());
				}
			}
		} else if (setting instanceof StringSetting) {
			mc.fontRenderer.drawString(sSetting.name + ": ", x + 2, y + 2, new Color(255, 255, 255).getRGB());
			int text = mc.fontRenderer.drawString(sSetting.value,
					x + 2 + mc.fontRenderer.getStringWidth(sSetting.name + ": "), y + 2,
					new Color(255, 255, 255).getRGB());
			if (this.textField == null) {
				this.textField = new GuiTextField(text, mc.fontRenderer,
						x + 2 + mc.fontRenderer.getStringWidth(sSetting.name + ": "), y,
						380 - mc.fontRenderer.getStringWidth(sSetting.name + ": "), mc.fontRenderer.FONT_HEIGHT + 2);
				textField.setEnabled(true);
				SettingController.textFields.put(textField, module);
				textField.setText(sSetting.value);
			}
			if (textField.isFocused()) {
				sSetting.value = textField.getText();
				textField.setTextColor(new Color(0, 255, 0).getRGB());
			}
		} else if (setting instanceof BooleanSetting) {
			if (bSetting.enabled) {
				mc.fontRenderer.drawString(bSetting.name, x + 2, y + 2, new Color(255, 150, 50).getRGB());
			} else {
				mc.fontRenderer.drawString(bSetting.name, x + 2, y + 2, new Color(180, 240, 255).getRGB());
			}
		} else if (setting instanceof ColorSetting) {
			mc.fontRenderer.drawString(cSetting.name + ": ", x + 2, y + 2,
					new Color(cSetting.red, cSetting.green, cSetting.blue).getRGB());
			int textRed = mc.fontRenderer.drawString(Integer.toString(cSetting.red),
					x + 2 + mc.fontRenderer.getStringWidth(cSetting.name + ": "), y + 2, new Color(255, 0, 0).getRGB());
			int textGreen = mc.fontRenderer.drawString(Integer.toString(cSetting.green),
					x + 2 + mc.fontRenderer.getStringWidth(cSetting.name + ": ") + 125, y + 2,
					new Color(0, 255, 0).getRGB());
			int textBlue = mc.fontRenderer.drawString(Integer.toString(cSetting.blue),
					x + 2 + mc.fontRenderer.getStringWidth(cSetting.name + ": ") + 250, y + 2,
					new Color(0, 0, 255).getRGB());
			if (cTextFieldRed == null && cTextFieldGreen == null && cTextFieldBlue == null) {
				cTextFieldRed = new GuiTextField(textRed, mc.fontRenderer,
						x + 2 + mc.fontRenderer.getStringWidth(cSetting.name + ": "), y, 50,
						mc.fontRenderer.FONT_HEIGHT + 2);
				cTextFieldGreen = new GuiTextField(textGreen, mc.fontRenderer,
						x + 2 + mc.fontRenderer.getStringWidth(cSetting.name + ": ") + 125, y, 50,
						mc.fontRenderer.FONT_HEIGHT + 2);
				cTextFieldBlue = new GuiTextField(textBlue, mc.fontRenderer,
						x + 2 + mc.fontRenderer.getStringWidth(cSetting.name + ": ") + 250, y, 50,
						mc.fontRenderer.FONT_HEIGHT + 2);
				cTextFieldRed.setTextColor(new Color(255, 0, 0).getRGB());
				cTextFieldGreen.setTextColor(new Color(0, 255, 0).getRGB());
				cTextFieldBlue.setTextColor(new Color(0, 0, 255).getRGB());
				cTextFieldRed.setEnabled(true);
				cTextFieldGreen.setEnabled(true);
				cTextFieldBlue.setEnabled(true);
				GuiTextField[] array = { cTextFieldRed, cTextFieldGreen, cTextFieldBlue };
				SettingController.cTextFields.put(array, new AbstractMap.SimpleEntry<>(module, setting));
				cTextFieldRed.setText(Integer.toString(cSetting.red));
				cTextFieldGreen.setText(Integer.toString(cSetting.green));
				cTextFieldBlue.setText(Integer.toString(cSetting.blue));

				if (cTextFieldRed.isFocused()) {
					if (StringParser.isInteger(cTextFieldRed.getText())
							&& Integer.parseInt(cTextFieldRed.getText()) <= 255
							&& Integer.parseInt(cTextFieldRed.getText()) >= 0)
						cSetting.red = Integer.parseInt(cTextFieldRed.getText());
				}
				if (cTextFieldGreen.isFocused()) {
					if (StringParser.isInteger(cTextFieldGreen.getText())
							&& Integer.parseInt(cTextFieldGreen.getText()) <= 255
							&& Integer.parseInt(cTextFieldGreen.getText()) >= 0)
						cSetting.green = Integer.parseInt(cTextFieldGreen.getText());
				}
				if (cTextFieldBlue.isFocused()) {
					if (StringParser.isInteger(cTextFieldBlue.getText())
							&& Integer.parseInt(cTextFieldBlue.getText()) <= 255
							&& Integer.parseInt(cTextFieldBlue.getText()) >= 0)
						cSetting.blue = Integer.parseInt(cTextFieldBlue.getText());
				}
			}
		} else if (setting instanceof SearchBlockSelectorSetting) {
			mc.fontRenderer.drawString(blockSetting.name, x + 2, y + 2, new Color(255, 255, 255).getRGB());
		}
	}

	// mc.fontRenderer.drawString(module.getName(), x + 2, y + 2, new Color(255,
	// 255, 255).getRGB());

	public void onClick(int x, int y, int button) {

		if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {

			ImpHackEventBus.EVENT_BUS.post(new ImpHackEventSettings(setting, setting.parent));

			if (setting instanceof ModeSetting) {
				mSetting.cycle();
				ClickGuiController.INSTANCE.settingController.refresh(false);
			} else if (setting instanceof IntSetting) {
				// NOOP
			} else if (setting instanceof FloatSetting) {
				// NOOP
			} else if (setting instanceof StringSetting) {
				// NOOP
			} else if (setting instanceof BooleanSetting) {
				// we need to thin this down a bit so it doesn't overlap with other buttons
				if (y >= this.y - 5 && y <= this.y + this.height - 5 && x >= this.x
						&& x <= this.x + mc.fontRenderer.getStringWidth(bSetting.name)) {
					bSetting.setEnabled(!bSetting.enabled);
				}
				ClickGuiController.INSTANCE.settingController.refresh(false);
			} else if (setting instanceof ColorSetting) {
				mc.fontRenderer.drawString(cSetting.name + ": ", x + 2, y + 2,
						new Color(cSetting.red, cSetting.green, cSetting.blue).getRGB());
			} else if (setting instanceof SearchBlockSelectorSetting) {
				GuiScreen last = mc.currentScreen;
				assert mc.currentScreen != null;
				mc.currentScreen.onGuiClosed();
				this.blockController = new BlockSelectorGuiController(last, blockSetting.colorSettings, blockSetting);
				mc.displayGuiScreen(this.blockController);
			}
		} else {
			if (textField != null && textField.isFocused()) {
				this.textField.setTextColor(new Color(255, 255, 255).getRGB());
				this.textField.setFocused(false);
			}
			if (textField != null) {
				this.textField.setTextColor(new Color(255, 255, 255).getRGB());
				if (setting instanceof IntSetting) {
					this.textField.setText(Integer.toString(iSetting.value));
				} else if (setting instanceof FloatSetting) {
					this.textField.setText(Float.toString(fSetting.value));
				} else if (setting instanceof StringSetting) {
					this.textField.setText(sSetting.value);
				}
			}
		}

		if (cTextFieldRed != null && x <= cTextFieldRed.x && x >= cTextFieldRed.x + cTextFieldRed.width && y <= this.y
				&& y >= this.y + this.height) {
			if (cTextFieldRed != null) {
				cTextFieldRed.setFocused(false);
				cTextFieldRed.setText(Integer.toString(cSetting.red));
			}
		} else {
			for (Entry<GuiTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
				GuiTextField r = entry.getKey()[0];
				GuiTextField g = entry.getKey()[1];
				GuiTextField b = entry.getKey()[2];

				if (r != this.cTextFieldRed) {
					r.setFocused(false);
				}
				g.setFocused(false);
				b.setFocused(false);
			}
		}

		if (cTextFieldGreen != null && x <= cTextFieldGreen.x && x >= cTextFieldGreen.x + cTextFieldGreen.width
				&& y <= this.y && y >= this.y + this.height) {
			if (cTextFieldGreen != null) {
				cTextFieldGreen.setFocused(false);
				cTextFieldGreen.setText(Integer.toString(cSetting.green));
			}
		} else {
			for (Entry<GuiTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
				GuiTextField r = entry.getKey()[0];
				GuiTextField g = entry.getKey()[1];
				GuiTextField b = entry.getKey()[2];

				r.setFocused(false);
				if (g != this.cTextFieldGreen) {
					g.setFocused(false);
				}
				b.setFocused(false);
			}
		}

		if (cTextFieldBlue != null && x <= cTextFieldBlue.x && x >= cTextFieldBlue.x + cTextFieldBlue.width
				&& y <= this.y && y >= this.y + this.height) {
			if (cTextFieldBlue != null) {
				cTextFieldBlue.setFocused(false);
				cTextFieldBlue.setText(Integer.toString(cSetting.blue));
			}
		} else {
			for (Entry<GuiTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
				GuiTextField r = entry.getKey()[0];
				GuiTextField g = entry.getKey()[1];
				GuiTextField b = entry.getKey()[2];

				r.setFocused(false);
				g.setFocused(false);
				if (b != this.cTextFieldBlue) {
					b.setFocused(false);
				}
			}
		}
		ClickGuiController.INSTANCE.settingController.refresh(false);
	}
}
