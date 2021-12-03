package mod.imphack.ui.clickgui.settingeditor.search;

import mod.imphack.Main;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventSettings;
import mod.imphack.misc.StringParser;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.SearchBlockSelectorSetting;
import mod.imphack.util.TileEntityFakeWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map.Entry;

public class BlockButton {

	private final TileEntityFakeWorld world;

	final int x;
	int y;
	final int width;
	final int height;

	final Module module;

	final BlockSelectorGuiFrame parent;

	final Minecraft mc = Minecraft.getMinecraft();

	final Block block;

	GuiTextField textFieldRed;
	GuiTextField textFieldGreen;
	GuiTextField textFieldBlue;

	public BlockButton(Module module, int x, int y, BlockSelectorGuiFrame parent, Block block) {

		this.module = module;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = mc.fontRenderer.getStringWidth(block.getLocalizedName() + 100);
		this.height = mc.fontRenderer.FONT_HEIGHT;
		this.block = block;
		this.world = new TileEntityFakeWorld(null, this.mc.world.provider);
	}

	public void draw() {
		if (((SearchBlockSelectorSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Search"),
				"Select Blocks")).blocks.contains(block)) {
			mc.fontRenderer.drawString(block.getLocalizedName(), x + 20, y + 2, new Color(255, 150, 50).getRGB());
		} else {
			mc.fontRenderer.drawString(block.getLocalizedName(), x + 20, y + 2, new Color(180, 240, 255).getRGB());
		}

		displayBlockFlat(x + 2, y + 2, block);

		if (((SearchBlockSelectorSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Search"),
				"Select Blocks")).colorSettings && ForgeRegistries.BLOCKS.getValuesCollection().contains(block)) {
			int textRed = mc.fontRenderer
					.drawString(
							Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
									.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks"))
											.getColors().get(block)).getRed()),
							mc.displayWidth / 2, y + 2, new Color(255, 0, 0).getRGB());
			int textGreen = mc.fontRenderer.drawString(
					Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColors()
									.get(block)).getGreen()),
					mc.displayWidth / 2 + 125, y + 2, new Color(0, 255, 0).getRGB());
			int textBlue = mc.fontRenderer.drawString(
					Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColors()
									.get(block)).getBlue()),
					mc.displayWidth / 2 + 250, y + 2, new Color(0, 0, 255).getRGB());
			if (textFieldRed == null && textFieldGreen == null && textFieldBlue == null) {
				textFieldRed = new GuiTextField(textRed, mc.fontRenderer, this.x + 200, this.y + 4, 50,
						this.mc.fontRenderer.FONT_HEIGHT + 4);
				textFieldGreen = new GuiTextField(textGreen, mc.fontRenderer, this.x + 265, this.y + 4, 50,
						this.mc.fontRenderer.FONT_HEIGHT + 4);
				textFieldBlue = new GuiTextField(textBlue, mc.fontRenderer, this.x + 330, this.y + 4, 50,
						this.mc.fontRenderer.FONT_HEIGHT + 4);
			}
			assert textFieldRed != null;
			textFieldRed.setTextColor(new Color(255, 0, 0).getRGB());
			textFieldGreen.setTextColor(new Color(0, 255, 0).getRGB());
			textFieldBlue.setTextColor(new Color(0, 0, 254).getRGB());
			textFieldRed.setEnabled(true);
			textFieldGreen.setEnabled(true);
			textFieldBlue.setEnabled(true);
			if (!textFieldRed.isFocused())
				textFieldRed.setText(Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
						.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColor(block))
								.getRed()));
			if (!textFieldGreen.isFocused())
				textFieldGreen.setText(Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
						.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColor(block))
								.getGreen()));
			if (!textFieldBlue.isFocused())
				textFieldBlue.setText(Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
						.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColor(block))
								.getBlue()));

			textFieldRed.drawTextBox();
			textFieldGreen.drawTextBox();
			textFieldBlue.drawTextBox();

			if (textFieldRed.isFocused()) {
				if (StringParser.isInteger(textFieldRed.getText()) && Integer.parseInt(textFieldRed.getText()) <= 255
						&& Integer.parseInt(textFieldRed.getText()) >= 0) {
					for (Entry<Block, Integer> e : ((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).colors
									.entrySet()) {
						if (e.getKey() == block) {
							e.setValue(new Color(Integer.parseInt(textFieldRed.getText()),
									Integer.parseInt(textFieldGreen.getText()), Integer.parseInt(textFieldBlue.getText()))
											.getRGB());
						}
					}
				} else {
					textFieldRed.setText(Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColor(block))
									.getRed()));
				}
			}
			if (textFieldGreen.isFocused()) {
				if (StringParser.isInteger(textFieldGreen.getText()) && Integer.parseInt(textFieldGreen.getText()) <= 255
						&& Integer.parseInt(textFieldGreen.getText()) >= 0) {
					for (Entry<Block, Integer> e : ((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).colors
									.entrySet()) {
						if (e.getKey() == block) {
							e.setValue(new Color(Integer.parseInt(textFieldRed.getText()),
									Integer.parseInt(textFieldGreen.getText()), Integer.parseInt(textFieldBlue.getText()))
											.getRGB());
						}
					}
				} else {
					textFieldGreen.setText(Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColor(block))
									.getGreen()));
				}
			}
			if (textFieldBlue.isFocused()) {
				if (StringParser.isInteger(textFieldBlue.getText()) && Integer.parseInt(textFieldBlue.getText()) <= 255
						&& Integer.parseInt(textFieldBlue.getText()) >= 0) {
					for (Entry<Block, Integer> e : ((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).colors
									.entrySet()) {
						if (e.getKey() == block) {
							e.setValue(new Color(Integer.parseInt(textFieldRed.getText()),
									Integer.parseInt(textFieldGreen.getText()), Integer.parseInt(textFieldBlue.getText()))
											.getRGB());
						}
					}
				} else {
					textFieldBlue.setText(Integer.toString(new Color(((SearchBlockSelectorSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).getColor(block))
									.getBlue()));
				}
			}
		}
	}

	// Display block face at given coords in GUI
	public void displayBlockFlat(int x, int y, Block block) {
		IBlockState state = block.getDefaultState();
		try {
			state = state.withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH);
		} catch (IllegalArgumentException ignored) {
		}
		try {
			state = state.withProperty(BlockDirectional.FACING, EnumFacing.SOUTH);
		} catch (IllegalArgumentException ignored) {
		}

		// Better than default state rendering
		if (block instanceof BlockWall) {
			state = state.withProperty(BlockWall.UP, Boolean.TRUE);
		} else if (block == Blocks.BED) {
			state = state.withProperty(BlockHorizontal.FACING, EnumFacing.NORTH);
		}

		this.displayBlockFlat(x, y, state);
	}

	// Display block state at given coords in GUI
	public void displayBlockFlat(int x, int y, IBlockState state) {
		this.displayBlockFlat(x, y, state, null);
	}

	// Display block state with tileentity at given coords in GUI
	public void displayBlockFlat(int x, int y, IBlockState state, TileEntity tile) {
		if (state.getBlock() == Blocks.AIR)
			return;

		ItemStack stack = new ItemStack(state.getBlock());

		IBakedModel model = this.mc.getBlockRendererDispatcher().getModelForState(state);
		if (model == null || model == this.mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager()
				.getMissingModel()) {
			// model =
			// mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getModel(new
			// ModelResourceLocation(Block.REGISTRY.getNameForObject(block).getResourcePath(),
			// "inventory"));
			model = this.parent.controller.itemRender.getItemModelWithOverrides(stack, null, this.mc.player);
		}

		GlStateManager.pushMatrix();
		this.mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		this.mc.renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		RenderHelper.enableGUIStandardItemLighting();

		GlStateManager.translate(x, y, 150f);
		GlStateManager.translate(8.0F, 8.0F, 0f);
		GlStateManager.scale(1.0F, -1.0F, 1.0F);
		GlStateManager.scale(16.0F, 16.0F, 16.0F);

		GlStateManager.disableLighting();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);

		if (model == null || model.isBuiltInRenderer() || model == this.mc.getBlockRendererDispatcher()
				.getBlockModelShapes().getModelManager().getMissingModel()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			if (tile == null)
				tile = this.createTileEntity(state);
			// stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
			if (tile != null)
				this.renderByTileEntity(tile, state);
		} else {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
			for (EnumFacing enumfacing : EnumFacing.values()) {
				this.parent.controller.itemRender.renderQuads(bufferbuilder, model.getQuads(state, enumfacing, 4242L),
						-1, stack);
			}
			this.parent.controller.itemRender.renderQuads(bufferbuilder, model.getQuads(state, null, 4242L), -1, stack);
			tessellator.draw();
		}

		GlStateManager.popMatrix();

		GlStateManager.disableAlpha();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		mc.renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	}

	// Special render for tileentties
	private void renderByTileEntity(TileEntity tile, IBlockState state) {
		// Use custom world to set blockstate, used by tileentities for rendering
		this.world.setBlockState(null, state, 0);
		World save = TileEntityRendererDispatcher.instance.world;
		TileEntityRendererDispatcher.instance.world = this.world; // for Shulker box rendering

		if (tile instanceof TileEntityBanner || tile instanceof TileEntityEnderChest || tile instanceof TileEntityBed
				|| tile instanceof TileEntityChest || tile instanceof TileEntityShulkerBox) {
			TileEntityRendererDispatcher.instance.render(tile, 0d, 0d, 0d, 0f, 1f);
		} else if (tile instanceof TileEntitySkull) {
			if (TileEntitySkullRenderer.instance != null) {
				GlStateManager.pushMatrix();
				GlStateManager.disableCull();
				TileEntitySkullRenderer.instance.renderSkull(0f, 0f, 0f,
						state.getValue(BlockDirectional.FACING),
						(((TileEntitySkull) tile).getSkullRotation() * 360) / 16.0F,
						((TileEntitySkull) tile).getSkullType(), this.mc.getSession().getProfile(), -1, 0f);
				GlStateManager.enableCull();
				GlStateManager.popMatrix();
			}
		} else {
			TileEntitySpecialRenderer<TileEntity> renderer;
			if (tile instanceof TileEntityEndGateway)
				renderer = TileEntityRendererDispatcher.instance.getRenderer(TileEntityEndPortal.class); // Avoid
																											// glitchy
																											// gateway
																											// beam
																											// rendering
			else
				renderer = TileEntityRendererDispatcher.instance
						.getRenderer(tile.getClass());
			if (renderer != null)
				renderer.render(tile, 0d, 0d, 0d, 0f, -1, 0f);
		}

		TileEntityRendererDispatcher.instance.world = save;
	}

	// Create tileentity for blockstate, associate it to fake world
	public TileEntity createTileEntity(IBlockState state) {
		TileEntity tile = state.getBlock().createTileEntity(this.mc.world, state);
		if (tile == null)
			return null;
		tile.setWorld(this.world);
		return tile;
	}

	public void onClick(int mouseX, int mouseY, int button) {

		ArrayList<Block> removeDuplicates = new ArrayList<>();
		for (Block b : ((SearchBlockSelectorSetting) Main.settingManager
				.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).blocks) {
			if (!removeDuplicates.contains(b)) {
				removeDuplicates.add(b);
			}
		}

		((SearchBlockSelectorSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Search"),
				"Select Blocks")).blocks = removeDuplicates;

		if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) {
			if (((SearchBlockSelectorSetting) Main.settingManager
					.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).blocks
							.contains(block)) {
				((SearchBlockSelectorSetting) Main.settingManager
						.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).blocks
								.remove(block);
			} else {
				((SearchBlockSelectorSetting) Main.settingManager
						.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks")).blocks.add(block);
			}

			Main.config.Save();
			parent.controller.refresh();

			ImpHackEventBus.EVENT_BUS.post(new ImpHackEventSettings(
					Main.settingManager.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks"),
					Main.moduleManager.getModule("Search")));
		}

		if (textFieldRed != null)
			textFieldRed.mouseClicked(mouseX, mouseY, button);
		if (textFieldGreen != null)
			textFieldGreen.mouseClicked(mouseX, mouseY, button);
		if (textFieldBlue != null)
			textFieldBlue.mouseClicked(mouseX, mouseY, button);
	}
}
