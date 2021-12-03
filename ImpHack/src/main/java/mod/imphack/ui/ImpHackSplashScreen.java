package mod.imphack.ui;

import mod.imphack.Main;
import mod.imphack.util.font.FontUtils;
import mod.imphack.util.render.ColorUtil;
import mod.imphack.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ImpHackSplashScreen extends GuiScreen {

	private final ArrayList<ResourceLocation> backgrounds = new ArrayList<>();

	private ResourceLocation background;

	private int x;
	private int y;

	private float tempSound;

	@Override
	public void onGuiClosed() {
		mc.gameSettings.setSoundLevel(SoundCategory.MUSIC, tempSound);
	}

	@Override
	public void initGui() {
		tempSound = mc.gameSettings.getSoundLevel(SoundCategory.MUSIC);
		mc.gameSettings.setSoundLevel(SoundCategory.MUSIC, 0.0f);

		backgrounds.add(new ResourceLocation("textures/1.png"));
		backgrounds.add(new ResourceLocation("textures/2.png"));
		backgrounds.add(new ResourceLocation("textures/3.png"));
		backgrounds.add(new ResourceLocation("textures/4.jpg"));
		backgrounds.add(new ResourceLocation("textures/5.png"));
		backgrounds.add(new ResourceLocation("textures/6.png"));
		backgrounds.add(new ResourceLocation("textures/7.png"));
		backgrounds.add(new ResourceLocation("textures/8.png"));
		backgrounds.add(new ResourceLocation("textures/9.jpg"));

		Random random = new Random();
		this.background = backgrounds.get(random.nextInt(backgrounds.size()));

		mc.gameSettings.enableVsync = false;
		mc.gameSettings.limitFramerate = 200;
		this.playMusic();

		this.x = this.width / 32;
		this.y = this.height / 32 + 10;
		this.buttonList.add(new SplashScreenButton(0, this.x, this.y, "Singleplayer"));
		this.buttonList.add(new SplashScreenButton(1, this.x, this.y + 22, "Multiplayer"));
		this.buttonList.add(new SplashScreenButton(2, this.x, this.y + 44, "Settings"));
		this.buttonList.add(new SplashScreenButton(2, this.x, this.y + 66, "Exit"));
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.shadeModel(7425);
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	public static void drawCompleteImage(float posX, float posY, float width, float height) {
		GL11.glPushMatrix();
		GL11.glTranslatef(posX, posY, 0.0f);
		GL11.glBegin(7);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(0.0f, height, 0.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(width, height, 0.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(width, 0.0f, 0.0f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (ImpHackSplashScreen.isHovered(this.x, this.y,
				Minecraft.getMinecraft().fontRenderer.getStringWidth("Singleplayer"),
				Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.displayGuiScreen(new GuiWorldSelection(this));
		} else if (ImpHackSplashScreen.isHovered(this.x, this.y + 22,
				Minecraft.getMinecraft().fontRenderer.getStringWidth("Multiplayer"),
				Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		} else if (ImpHackSplashScreen.isHovered(this.x, this.y + 44,
				Minecraft.getMinecraft().fontRenderer.getStringWidth("Settings"),
				Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		} else if (ImpHackSplashScreen.isHovered(this.x, this.y + 66,
				Minecraft.getMinecraft().fontRenderer.getStringWidth("Exit"),
				Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.shutdown();
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.x = this.width / 32;
		this.y = this.height / 32 + 10;
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		this.mc.getTextureManager().bindTexture(this.background);
		ImpHackSplashScreen.drawCompleteImage(0.0f, 0.0f, this.width, this.height);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private static class SplashScreenButton extends GuiButton {

		public SplashScreenButton(int buttonId, int x, int y, String buttonText) {
			super(buttonId, x, y, Minecraft.getMinecraft().fontRenderer.getStringWidth(buttonText),
					Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, buttonText);
		}

		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				this.enabled = true;
				this.hovered = (float) mouseX >= (float) this.x && mouseY >= this.y && mouseX < this.x + this.width
						&& mouseY < this.y + this.height;
				FontUtils.drawStringWithShadow(false, this.displayString,  this.x + 1,
						 this.y, new ColorUtil(255,255,255,255));
				if (this.hovered) {
					RenderUtil.drawLine(this.x - 5f, this.y + 2 + mc.fontRenderer.FONT_HEIGHT,
							this.x + 5f + mc.fontRenderer.getStringWidth(this.displayString),
							this.y + 2 + mc.fontRenderer.FONT_HEIGHT, 2f, Color.BLUE.getRGB());
				}
			}
		}

		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
			return this.enabled && this.visible
					&& (float) mouseX >= (float) this.x
							- (float) Minecraft.getMinecraft().fontRenderer.getStringWidth(this.displayString) / 2.0f
					&& mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		}
	}

	public void playMusic() {
		mc.soundHandler.stopSounds();
		if (!mc.soundHandler.isSoundPlaying(Main.songManager.getMenuSong())) {
			mc.soundHandler.playSound(Main.songManager.getMenuSong());
		}
	}

}
