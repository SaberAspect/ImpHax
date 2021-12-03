package mod.imphack.util.render;

import mod.imphack.Main;
import mod.imphack.misc.RenderBuilder;
import mod.imphack.misc.RenderBuilder.RenderMode;
import mod.imphack.util.EntityUtil;
import mod.imphack.util.MathUtil;
import mod.imphack.util.Timer;
import mod.imphack.util.font.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import java.util.List;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class RenderUtil extends Tessellator {

	private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

	public static Timer splashTimer = new Timer();
	public static int splashTickPos = 0;

	static final Minecraft mc = Minecraft.getMinecraft();

	public static final RenderUtil INSTANCE = new RenderUtil();
    public static Tessellator tessellator = Tessellator.getInstance();
    public static BufferBuilder bufferbuilder = tessellator.getBuffer();


	public static boolean isSplash = false;

	public RenderUtil() {
		super(0x200000);
	}

	public static void prepare(String mode_requested) {
		int mode = 0;

    	if (mode_requested.equalsIgnoreCase("quads")) {
    		mode = GL_QUADS;
    	} else if (mode_requested.equalsIgnoreCase("lines")) {
    		mode = GL_LINES;
    	} else if (mode_requested.equalsIgnoreCase("triangles")) {
    	    mode = GL_TRIANGLES;
        }

        prepare_gl();
        begin(mode);
    }


	public static void prepare_gl() {
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(1.5F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GlStateManager.color(1, 1, 1);
	}
	
	 public static void prepareGL() {
	        GL11.glBlendFunc(770, 771);
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        GlStateManager.glLineWidth(1.5f);
	        GlStateManager.disableTexture2D();
	        GlStateManager.depthMask(false);
	        GlStateManager.enableBlend();
	        GlStateManager.disableDepth();
	        GlStateManager.disableLighting();
	        GlStateManager.disableCull();
	        GlStateManager.enableAlpha();
	        GlStateManager.color(1.0f, 1.0f, 1.0f);
	    }
  
	public static void prepare_new() {
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);
	}

	public static void begin(int mode) {
		INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
	}

	public static void release() {
		render();
		release_gl();
	}

	public static void render() {
		INSTANCE.draw();
	}

	public static void release_gl() {
		GlStateManager.enableCull();
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableDepth();
	}
	

	public static void release_new() {
		GL11.glDisable(GL32.GL_DEPTH_CLAMP);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableAlpha();
		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.glLineWidth(1.0f);
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
	}

	public static void drawLine(float x, float y, float x1, float y1, float thickness, int hex) {
		float red = (float) (hex >> 16 & 0xFF) / 255.0f;
		float green = (float) (hex >> 8 & 0xFF) / 255.0f;
		float blue = (float) (hex & 0xFF) / 255.0f;
		float alpha = (float) (hex >> 24 & 0xFF) / 255.0f;
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		GL11.glLineWidth(thickness);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(x1, y1, 0.0).color(red, green, blue, alpha).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GL11.glDisable(2848);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}

	public static void draw_cube(BlockPos blockPos, int argb, String sides) {
		final int a = (argb >>> 24) & 0xFF;
		final int r = (argb >>> 16) & 0xFF;
		final int g = (argb >>> 8) & 0xFF;
		final int b = argb & 0xFF;
		draw_cube(blockPos, r, g, b, a, sides);
	}

	public static void draw_cube(float x, float y, float z, int argb, String sides) {
		final int a = (argb >>> 24) & 0xFF;
		final int r = (argb >>> 16) & 0xFF;
		final int g = (argb >>> 8) & 0xFF;
		final int b = argb & 0xFF;
		draw_cube(INSTANCE.getBuffer(), x, y, z, 1, 1, 1, r, g, b, a, sides);
	}

	public static void draw_cube(BlockPos blockPos, int r, int g, int b, int a, String sides) {
		draw_cube(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, 1, 1, r, g, b, a, sides);
	}

	public static void draw_cube_line(BlockPos blockPos, int argb, String sides) {
		final int a = (argb >>> 24) & 0xFF;
		final int r = (argb >>> 16) & 0xFF;
		final int g = (argb >>> 8) & 0xFF;
		final int b = argb & 0xFF;
		draw_cube_line(blockPos, r, g, b, a, sides);
	}

	public static void draw_cube_line(float x, float y, float z, int argb, String sides) {
		final int a = (argb >>> 24) & 0xFF;
		final int r = (argb >>> 16) & 0xFF;
		final int g = (argb >>> 8) & 0xFF;
		final int b = argb & 0xFF;
		draw_cube_line(INSTANCE.getBuffer(), x, y, z, 1, 1, 1, r, g, b, a, sides);
	}

	public static void draw_cube_line(BlockPos blockPos, int r, int g, int b, int a, String sides) {
		draw_cube_line(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, 1, 1, r, g, b, a,
				sides);
	}

	public static BufferBuilder get_buffer_build() {
		return INSTANCE.getBuffer();
	}

	public static void draw_cube(final BufferBuilder buffer, float x, float y, float z, float w, float h, float d,
			int r, int g, int b, int a, String sides) {
		if (Arrays.asList(sides.split("-")).contains("down") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("up") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("north") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
		}
	}

	public static void draw_cube_line(final BufferBuilder buffer, float x, float y, float z, float w, float h, float d,
			int r, int g, int b, int a, String sides) {
		if (Arrays.asList(sides.split("-")).contains("downwest") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("upwest") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("downeast") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("upeast") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("downnorth") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("upnorth") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("downsouth") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("upsouth") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("nortwest") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("norteast") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("southweast") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if (Arrays.asList(sides.split("-")).contains("southeast") || sides.equalsIgnoreCase("all")) {
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
		}
	}

	public static void Line(double sx, double sy, double sz, double ex, double ey, double ez, Vector3d plypos,
			Color color, float width) {
		float r = MathUtil.interpolate(color.getRed(), 0, 255, 0, 1);
		float g = MathUtil.interpolate(color.getGreen(), 0, 255, 0, 1);
		float b = MathUtil.interpolate(color.getBlue(), 0, 255, 0, 1);

		double rx = ex - sx;
		double ry = ey - sy;
		double rz = ez - sz;

		sx = -plypos.getX() + sx;
		sy = -plypos.getY() + sy;
		sz = -plypos.getZ() + sz;

		GL11.glPushMatrix();

		GL11.glTranslated(sx, sy, sz);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glLineWidth(width);

		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(r, g, b, 1f);

		GL11.glBegin(GL11.GL_LINES);

		GL11.glVertex3d(0, 0, 0);
		GL11.glVertex3d(rx, ry, rz);

		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);

		GL11.glPopMatrix();
	}

	public static void Prism(double x, double y, double z, double width, double height, double depth, Vector3d plypos,
			Color color) {
		float r = MathUtil.interpolate(color.getRed(), 0, 255, 0, 1);
		float g = MathUtil.interpolate(color.getGreen(), 0, 255, 0, 1);
		float b = MathUtil.interpolate(color.getBlue(), 0, 255, 0, 1);

		double wh = width / 2;
		double hh = height / 2;
		double dh = depth / 2;

		x = -plypos.getX() + x;
		y = -plypos.getY() + y;
		z = -plypos.getZ() + z;

		GL11.glPushMatrix();

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glTranslated(x, y, z);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glColor4f(r, g, b, 0.25f);

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex3d(wh, hh, -dh);
		GL11.glVertex3d(wh, -hh, -dh);
		GL11.glVertex3d(-wh, -hh, -dh);
		GL11.glVertex3d(-wh, hh, -dh);
		GL11.glVertex3d(wh, hh, dh);
		GL11.glVertex3d(wh, -hh, dh);
		GL11.glVertex3d(wh, -hh, -dh);
		GL11.glVertex3d(wh, hh, -dh);
		GL11.glVertex3d(-wh, hh, dh);
		GL11.glVertex3d(-wh, -hh, dh);
		GL11.glVertex3d(wh, -hh, dh);
		GL11.glVertex3d(wh, hh, dh);
		GL11.glVertex3d(-wh, hh, -dh);
		GL11.glVertex3d(-wh, -hh, -dh);
		GL11.glVertex3d(-wh, -hh, dh);
		GL11.glVertex3d(-wh, hh, dh);
		GL11.glVertex3d(wh, -hh, -dh);
		GL11.glVertex3d(wh, -hh, dh);
		GL11.glVertex3d(-wh, -hh, dh);
		GL11.glVertex3d(-wh, -hh, -dh);
		GL11.glVertex3d(-wh, hh, dh);
		GL11.glVertex3d(wh, hh, dh);
		GL11.glVertex3d(wh, hh, -dh);
		GL11.glVertex3d(-wh, hh, -dh);

		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);

		GL11.glPopMatrix();
	}

	public static void OutlinedPrism(double x, double y, double z, double width, double height, double depth,
			Vector3d plypos, Color color, float lwidth) {
		float r = MathUtil.interpolate(color.getRed(), 0, 255, 0, 1);
		float g = MathUtil.interpolate(color.getGreen(), 0, 255, 0, 1);
		float b = MathUtil.interpolate(color.getBlue(), 0, 255, 0, 1);

		double wh = width / 2;
		double hh = height / 2;
		double dh = depth / 2;

		x = -plypos.getX() + x;
		y = -plypos.getY() + y;
		z = -plypos.getZ() + z;

		GL11.glPushMatrix();

		GL11.glTranslated(x, y, z);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glLineWidth(lwidth);

		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(r, g, b, 1f);

		GL11.glBegin(GL11.GL_LINE_STRIP);

		GL11.glVertex3d(-wh, -hh, -dh);
		GL11.glVertex3d(-wh, hh, -dh);
		GL11.glVertex3d(wh, hh, -dh);
		GL11.glVertex3d(wh, -hh, -dh);
		GL11.glVertex3d(wh, -hh, dh);
		GL11.glVertex3d(wh, hh, dh);
		GL11.glVertex3d(-wh, hh, dh);
		GL11.glVertex3d(-wh, -hh, dh);
		GL11.glVertex3d(-wh, -hh, -dh);
		GL11.glVertex3d(wh, -hh, -dh);
		GL11.glVertex3d(wh, hh, -dh);
		GL11.glVertex3d(wh, hh, dh);
		GL11.glVertex3d(wh, -hh, dh);
		GL11.glVertex3d(-wh, -hh, dh);
		GL11.glVertex3d(-wh, hh, dh);
		GL11.glVertex3d(-wh, hh, -dh);

		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);

		GL11.glPopMatrix();
	}

	public static void Text(double x, double y, double z, String text, Vector3d plypos, Color color) {
		float r = MathUtil.interpolate(color.getRed(), 0, 255, 0, 1);
		float g = MathUtil.interpolate(color.getGreen(), 0, 255, 0, 1);
		float b = MathUtil.interpolate(color.getBlue(), 0, 255, 0, 1);

		RenderManager rm = Minecraft.getMinecraft().getRenderManager();

		x = -plypos.getX() + x;
		y = -plypos.getY() + y;
		z = -plypos.getZ() + z;

		GL11.glPushMatrix();

		GL11.glTranslated(x, y, z);

		GL11.glRotatef(-rm.playerViewY, 0f, 1f, 0f);
		GL11.glRotatef(rm.playerViewX, 1f, 0f, 0f);
		GL11.glScalef(-0.05f, -0.05f, 0.05f);

		// GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glColor4f(r, g, b, 1f);

		Minecraft.getMinecraft().fontRenderer.drawString(text,
				-Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2,
				-Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT / 2, color.getRGB());

		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);

		GL11.glPopMatrix();
	}

	public static void drawRect(final float x, final float y, final float w, final float h, final int color) {
		final float alpha = (color >> 24 & 0xFF) / 255.0f;
		final float red = (color >> 16 & 0xFF) / 255.0f;
		final float green = (color >> 8 & 0xFF) / 255.0f;
		final float blue = (color & 0xFF) / 255.0f;
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(x, h, 0.0).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(w, h, 0.0).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(w, y, 0.0).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawRect(final float x, final float y, final float w, final float h, final float r,
			final float g, final float b, final float a) {
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(x, h, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
		bufferbuilder.pos(w, h, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
		bufferbuilder.pos(w, y, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
		bufferbuilder.pos(x, y, 0.0).color(r / 255, g / 255, b / 255, a).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawLine3D(float x, float y, float z, float x1, float y1, float z1, float thickness, int hex) {
		float red = (hex >> 16 & 0xFF) / 255.0F;
		float green = (hex >> 8 & 0xFF) / 255.0F;
		float blue = (hex & 0xFF) / 255.0F;
		float alpha = (hex >> 24 & 0xFF) / 255.0F;

		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GL11.glLineWidth(thickness);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GlStateManager.disableDepth();
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(x, y, z).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableDepth();
		GL11.glDisable(GL32.GL_DEPTH_CLAMP);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}

	private static void drawBorderedRect(double x, double y, double x1, ColorUtil inside,
										 ColorUtil border) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		inside.glColor();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(x, 1, 0).endVertex();
		bufferbuilder.pos(x1, 1, 0).endVertex();
		bufferbuilder.pos(x1, y, 0).endVertex();
		bufferbuilder.pos(x, y, 0).endVertex();
		tessellator.draw();
		border.glColor();
		GlStateManager.glLineWidth((float) 1.8);
		bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(x, y, 0).endVertex();
		bufferbuilder.pos(x, 1, 0).endVertex();
		bufferbuilder.pos(x1, 1, 0).endVertex();
		bufferbuilder.pos(x1, y, 0).endVertex();
		bufferbuilder.pos(x, y, 0).endVertex();
		tessellator.draw();
	}

	

	// BlockOverlay

	public static void drawSolidBox() {

		drawSolidBox(DEFAULT_AABB);
	}

	public static void drawSolidBox(AxisAlignedBB bb) {

		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		}
		GL11.glEnd();
	}

	public static void drawOutlinedBox() {

		drawOutlinedBox(DEFAULT_AABB);
	}

	public static void drawOutlinedBox(AxisAlignedBB bb) {

		GL11.glBegin(GL11.GL_LINES);
		{
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		}
		GL11.glEnd();
	}

	public static void drawBlockESP(BlockPos pos, float red, float green, float blue) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		double renderPosX = mc.getRenderManager().viewerPosX;
		double renderPosY = mc.getRenderManager().viewerPosY;
		double renderPosZ = mc.getRenderManager().viewerPosZ;

		GL11.glTranslated(-renderPosX, -renderPosY, -renderPosZ);
		GL11.glTranslated(pos.getX(), pos.getY(), pos.getZ());

		GL11.glColor4f(red, green, blue, 0.30F);
		drawSolidBox();
		GL11.glColor4f(red, green, blue, 0.7F);
		drawOutlinedBox();

		GL11.glColor4f(1, 1, 1, 1);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glPopMatrix();
	}

	public static String DF(Number value, int maxvalue) {
		DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setMaximumFractionDigits(maxvalue);
		return df.format(value);
	}

	public static void drawBox(BlockPos blockPos, double height, ColorUtil color, int sides) {
		drawBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, height, 1, color, sides);
	}

	public static void drawBox(AxisAlignedBB bb, boolean check, double height, ColorUtil color, int sides) {
		if (check) {
			drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, bb.maxY - bb.minY, bb.maxZ - bb.minZ, color, sides);
		} else {
			drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, height, bb.maxZ - bb.minZ, color, sides);
		}
	}

	public static void drawBox(double x, double y, double z, double w, double h, double d, ColorUtil color, int sides) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		color.glColor();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		if ((sides & Geometry.Quad.DOWN) != 0) {
			vertex(x + w, y, z, bufferbuilder);
			vertex(x + w, y, z + d, bufferbuilder);
			vertex(x, y, z + d, bufferbuilder);
			vertex(x, y, z, bufferbuilder);
		}
		if ((sides & Geometry.Quad.UP) != 0) {
			vertex(x + w, y + h, z, bufferbuilder);
			vertex(x, y + h, z, bufferbuilder);
			vertex(x, y + h, z + d, bufferbuilder);
			vertex(x + w, y + h, z + d, bufferbuilder);
		}
		if ((sides & Geometry.Quad.NORTH) != 0) {
			vertex(x + w, y, z, bufferbuilder);
			vertex(x, y, z, bufferbuilder);
			vertex(x, y + h, z, bufferbuilder);
			vertex(x + w, y + h, z, bufferbuilder);
		}
		if ((sides & Geometry.Quad.SOUTH) != 0) {
			vertex(x, y, z + d, bufferbuilder);
			vertex(x + w, y, z + d, bufferbuilder);
			vertex(x + w, y + h, z + d, bufferbuilder);
			vertex(x, y + h, z + d, bufferbuilder);
		}
		if ((sides & Geometry.Quad.WEST) != 0) {
			vertex(x, y, z, bufferbuilder);
			vertex(x, y, z + d, bufferbuilder);
			vertex(x, y + h, z + d, bufferbuilder);
			vertex(x, y + h, z, bufferbuilder);
		}
		if ((sides & Geometry.Quad.EAST) != 0) {
			vertex(x + w, y, z + d, bufferbuilder);
			vertex(x + w, y, z, bufferbuilder);
			vertex(x + w, y + h, z, bufferbuilder);
			vertex(x + w, y + h, z + d, bufferbuilder);
		}
		tessellator.draw();
	}

	private static void vertex(double x, double y, double z, BufferBuilder bufferbuilder) {
		bufferbuilder.pos(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY,
				z - mc.getRenderManager().viewerPosZ).endVertex();
	}

	public static void drawBoundingBox(BlockPos bp, double height, float width, ColorUtil color) {
		drawBoundingBox(getBoundingBox(bp, height), width, color);
	}

	public static void drawBoundingBox(AxisAlignedBB bb, float width, ColorUtil color) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.glLineWidth(width);
		color.glColor();
		bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		vertex(bb.minX, bb.minY, bb.minZ, bufferbuilder);
		vertex(bb.minX, bb.minY, bb.maxZ, bufferbuilder);
		vertex(bb.maxX, bb.minY, bb.maxZ, bufferbuilder);
		vertex(bb.maxX, bb.minY, bb.minZ, bufferbuilder);
		vertex(bb.minX, bb.minY, bb.minZ, bufferbuilder);
		vertex(bb.minX, bb.maxY, bb.minZ, bufferbuilder); //
		vertex(bb.minX, bb.maxY, bb.maxZ, bufferbuilder);
		vertex(bb.minX, bb.minY, bb.maxZ, bufferbuilder);
		vertex(bb.maxX, bb.minY, bb.maxZ, bufferbuilder); //
		vertex(bb.maxX, bb.maxY, bb.maxZ, bufferbuilder);
		vertex(bb.minX, bb.maxY, bb.maxZ, bufferbuilder);
		vertex(bb.maxX, bb.maxY, bb.maxZ, bufferbuilder);
		vertex(bb.maxX, bb.maxY, bb.minZ, bufferbuilder);
		vertex(bb.maxX, bb.minY, bb.minZ, bufferbuilder);
		vertex(bb.maxX, bb.maxY, bb.minZ, bufferbuilder);
		vertex(bb.minX, bb.maxY, bb.minZ, bufferbuilder);
		tessellator.draw();
	}

	public static void drawBoundingBox(AxisAlignedBB bb, float width, int color) {
		final float alpha = (color >> 24 & 0xFF) / 255.0F;
		final float red = (color >> 16 & 0xFF) / 255.0F;
		final float green = (color >> 8 & 0xFF) / 255.0F;
		final float blue = (color & 0xFF) / 255.0F;
		drawBoundingBox(bb, width, red, green, blue, alpha);
	}

	public static void drawBoundingBox(AxisAlignedBB bb, float width, float red, float green, float blue, float alpha) {
		GL11.glLineWidth(width);

		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();

		bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 0.0F).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0F).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0F).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, 0.0F).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 0.0F).endVertex();
		tessellator.draw();
	}

	public static void drawFilledBox(AxisAlignedBB bb, int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0F;
        final float red = (color >> 16 & 0xFF) / 255.0F;
        final float green = (color >> 8 & 0xFF) / 255.0F;
        final float blue = (color & 0xFF) / 255.0F;

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();

        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();

        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();

        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();

        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();

        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();

        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }
	
	private static AxisAlignedBB getBoundingBox(BlockPos bp, double height) {
		double x=bp.getX();
		double y=bp.getY();
		double z=bp.getZ();
		return new AxisAlignedBB(x,y,z,x+ (double) 1,y+height,z+ (double) 1);
	}

	//Esp's
	public static void playerEsp (BlockPos bp, double height, float width, ColorUtil color) {
		drawBoundingBox(getBoundingBox(bp, height),width,color);
	}

	public static void playerEsp (AxisAlignedBB bb, float width, ColorUtil color) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.glLineWidth(width);
		color.glColor();
		bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		vertex(bb.minX,bb.minY,bb.minZ,bufferbuilder);
		vertex(bb.minX,bb.minY,bb.maxZ,bufferbuilder);
		vertex(bb.maxX,bb.minY,bb.maxZ,bufferbuilder);
		vertex(bb.maxX,bb.minY,bb.minZ,bufferbuilder);
		vertex(bb.minX,bb.minY,bb.minZ,bufferbuilder);
		vertex(bb.minX,bb.maxY,bb.minZ,bufferbuilder); //
		vertex(bb.minX,bb.maxY,bb.maxZ,bufferbuilder);
		vertex(bb.minX,bb.minY,bb.maxZ,bufferbuilder);
		vertex(bb.maxX,bb.minY,bb.maxZ,bufferbuilder); //
		vertex(bb.maxX,bb.maxY,bb.maxZ,bufferbuilder);
		vertex(bb.minX,bb.maxY,bb.maxZ,bufferbuilder);
		vertex(bb.maxX,bb.maxY,bb.maxZ,bufferbuilder);
		vertex(bb.maxX,bb.maxY,bb.minZ,bufferbuilder);
		vertex(bb.maxX,bb.minY,bb.minZ,bufferbuilder);
		vertex(bb.maxX,bb.maxY,bb.minZ,bufferbuilder);
		vertex(bb.minX,bb.maxY,bb.minZ,bufferbuilder);
		tessellator.draw();
	}
	
	public static void drawPlayerBox(AxisAlignedBB bb, float width, ColorUtil color, int sides) {
		drawPlayerBox(bb.minX,bb.minY,bb.minZ,bb.maxX-bb.minX, bb.maxY-bb.minY,bb.maxZ-bb.minZ,color,sides);
}
	
	public static void drawPlayerBox(double x, double y, double z, double w, double h, double d, ColorUtil color, int sides) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		color.glColor();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		if ((sides & Geometry.Quad.DOWN) != 0) {
			vertex(x+w,y,z,  bufferbuilder);
			vertex(x+w,y,z+d,bufferbuilder);
			vertex(x,  y,z+d,bufferbuilder);
			vertex(x,  y,z,  bufferbuilder);
		}
		if ((sides & Geometry.Quad.UP) != 0) {
			vertex(x+w,y+h,z,  bufferbuilder);
			vertex(x,  y+h,z,  bufferbuilder);
			vertex(x,  y+h,z+d,bufferbuilder);
			vertex(x+w,y+h,z+d,bufferbuilder);
		}
		if ((sides & Geometry.Quad.NORTH) != 0) {
			vertex(x+w,y,  z,bufferbuilder);
			vertex(x,  y,  z,bufferbuilder);
			vertex(x,  y+h,z,bufferbuilder);
			vertex(x+w,y+h,z,bufferbuilder);
		}
		if ((sides & Geometry.Quad.SOUTH) != 0) {
			vertex(x,  y,  z+d,bufferbuilder);
			vertex(x+w,y,  z+d,bufferbuilder);
			vertex(x+w,y+h,z+d,bufferbuilder);
			vertex(x,  y+h,z+d,bufferbuilder);
		}
		if ((sides & Geometry.Quad.WEST) != 0) {
			vertex(x,y,  z,  bufferbuilder);
			vertex(x,y,  z+d,bufferbuilder);
			vertex(x,y+h,z+d,bufferbuilder);
			vertex(x,y+h,z,  bufferbuilder);
		}
		if ((sides & Geometry.Quad.EAST) != 0) {
			vertex(x+w,y,  z+d,bufferbuilder);
			vertex(x+w,y,  z,  bufferbuilder);
			vertex(x+w,y+h,z,  bufferbuilder);
			vertex(x+w,y+h,z+d,bufferbuilder);
		}
		tessellator.draw();
	}

	
	

	public static void drawStorageBox(BlockPos blockPos, double height, ColorUtil color, int sides) {
		drawStorageBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, height, 1, color, sides);
	}

	public static void drawStorageBox(AxisAlignedBB bb, boolean check,  double height, ColorUtil color, int sides) {
		if (check){
			drawStorageBox(bb.minX,bb.minY,bb.minZ,bb.maxX-bb.minX, bb.maxY-bb.minY,bb.maxZ-bb.minZ,color,sides);
		}
		else {
			drawStorageBox(bb.minX,bb.minY,bb.minZ,bb.maxX-bb.minX, height,bb.maxZ-bb.minZ,color,sides);
		}
	}

	public static void drawStorageBox(double x, double y, double z, double w, double h, double d, ColorUtil color, int sides) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		color.glColor();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		if ((sides & Geometry.Quad.DOWN) != 0) {
			vertex(x+w - 0.06,y,z + 0.06,  bufferbuilder);
			vertex(x+w - 0.06,y,z+d - 0.06,bufferbuilder);
			vertex(x + 0.06,  y,z+d - 0.06,bufferbuilder);
			vertex(x + 0.06,  y,z + 0.06,  bufferbuilder);
		}
		if ((sides & Geometry.Quad.UP) != 0) {
			vertex(x+w - 0.06,y+h,z + 0.06,  bufferbuilder);
			vertex(x + 0.06,  y+h,z + 0.06,  bufferbuilder);
			vertex(x + 0.06,  y+h,z+d - 0.06,bufferbuilder);
			vertex(x+w - 0.06,y+h,z+d - 0.06,bufferbuilder);
		}
		if ((sides & Geometry.Quad.NORTH) != 0) {
			vertex(x+w - 0.06,y,  z + 0.06,bufferbuilder);
			vertex(x + 0.06,  y,  z + 0.06,bufferbuilder);
			vertex(x + 0.06,  y+h,z + 0.06,bufferbuilder);
			vertex(x+w - 0.06,y+h,z + 0.06,bufferbuilder);
		}
		if ((sides & Geometry.Quad.SOUTH) != 0) {
			vertex(x + 0.06,  y,  z+d - 0.06,bufferbuilder);
			vertex(x+w - 0.06,y,  z+d - 0.06,bufferbuilder);
			vertex(x+w - 0.06,y+h,z+d - 0.06,bufferbuilder);
			vertex(x + 0.06,  y+h,z+d - 0.06,bufferbuilder);
		}
		if ((sides & Geometry.Quad.WEST) != 0) {
			vertex(x + 0.06,y,  z + 0.06,  bufferbuilder);
			vertex(x + 0.06,y,  z+d - 0.06,bufferbuilder);
			vertex(x + 0.06,y+h,z+d - 0.06,bufferbuilder);
			vertex(x + 0.06,y+h,z + 0.06,  bufferbuilder);
		}
		if ((sides & Geometry.Quad.EAST) != 0) {
			vertex(x+w - 0.06,y,  z+d - 0.06,bufferbuilder);
			vertex(x+w - 0.06,y,  z + 0.06,  bufferbuilder);
			vertex(x+w - 0.06,y+h,z + 0.06,  bufferbuilder);
			vertex(x+w - 0.06,y+h,z+d - 0.06,bufferbuilder);
		}
		tessellator.draw();
	}
	
	
	public static void drawBoundingBoxWithSides(BlockPos blockPos, int width, ColorUtil color, int sides){
		drawBoundingBoxWithSides(getBoundingBox(blockPos, 1), width, color, sides);
	}

	public static void drawBoundingBoxWithSides(AxisAlignedBB axisAlignedBB, int width, ColorUtil color, int sides){
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.glLineWidth(width);
		color.glColor();
		double w = axisAlignedBB.maxX-axisAlignedBB.minX;
		double h = axisAlignedBB.maxY-axisAlignedBB.minY;
		double d = axisAlignedBB.maxZ-axisAlignedBB.minZ;

		bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		if ((sides & Geometry.Quad.EAST) != 0) {
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY,  axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY,  axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY+h,axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY+h,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX +w, axisAlignedBB.minY, axisAlignedBB.minZ +d, bufferbuilder);
		}
		if ((sides & Geometry.Quad.WEST) != 0) {
			vertex(axisAlignedBB.minX,axisAlignedBB.minY, axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX,axisAlignedBB.minY,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX,axisAlignedBB.minY+h,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX,axisAlignedBB.minY+h,axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, bufferbuilder);
		}
		if ((sides & Geometry.Quad.NORTH) != 0) {
			vertex(axisAlignedBB.minX + w,axisAlignedBB.minY, axisAlignedBB.minZ, bufferbuilder);
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY, axisAlignedBB.minZ,bufferbuilder);
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY+h,axisAlignedBB.minZ,bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY+h,axisAlignedBB.minZ,bufferbuilder);
			vertex(axisAlignedBB.minX +w, axisAlignedBB.minY, axisAlignedBB.minZ, bufferbuilder);
		}
		if ((sides & Geometry.Quad.SOUTH) != 0) {
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY+h,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY+h,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ +d, bufferbuilder);
		}
		if ((sides & Geometry.Quad.UP) != 0) {
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY+h,axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY+h,axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY+h,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY+h,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX +w, axisAlignedBB.minY +h, axisAlignedBB.minZ, bufferbuilder);
		}
		if ((sides & Geometry.Quad.DOWN) != 0) {
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY,axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX+w,axisAlignedBB.minY,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY,axisAlignedBB.minZ+d,bufferbuilder);
			vertex(axisAlignedBB.minX,  axisAlignedBB.minY,axisAlignedBB.minZ,  bufferbuilder);
			vertex(axisAlignedBB.minX +w, axisAlignedBB.minY, axisAlignedBB.minZ, bufferbuilder);
		}
		tessellator.draw();
	}
	
	 public static void drawSelectionBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color color) {
	        bufferbuilder.begin(GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	        addChainedFilledBoxVertices(bufferbuilder, axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, color);
	        tessellator.draw();
	    }
	 
	 
	 public static void drawNametag(Entity entity, String[] text, ColorUtil color, int type) {
			Vec3d pos = EntityUtil.getInterpolatedPos(entity, mc.getRenderPartialTicks());
			drawNametag(pos.x, pos.y + entity.height, pos.z, text, color, type);
		}

		public static void drawNametag(double x, double y, double z, String[] text, ColorUtil color, int type) {
			double dist = mc.player.getDistance(x, y, z);
			double scale = 1, offset = 0;
			int start = 0;
			switch (type) {
			case 0:
				scale = dist / 20 * Math.pow(1.2589254, 0.1 / (dist < 25 ? 0.5 : 2));
				scale = Math.min(Math.max(scale, .5), 5);
				offset = scale > 2 ? scale / 2 : scale;
				scale /= 40;
				start = 10;
				break;
			case 1:
				scale = -((int) dist) / 6.0;
				if (scale < 1)
					scale = 1;
				scale *= 2.0 / 75.0;
				break;
			case 2:
				scale = 0.002 + 0.00349 * dist ;
				if (dist <= 8.0)
					scale = .0265;
				start = -8;
				break;
			}
			GlStateManager.pushMatrix();
			GlStateManager.translate(x - mc.getRenderManager().viewerPosX, y + offset - mc.getRenderManager().viewerPosY,
					z - mc.getRenderManager().viewerPosZ);
			GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
			GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1 : 1, 0, 0);
			GlStateManager.scale(-scale, -scale, scale);
			if (type == 2) {
				double width = 0;
				ColorUtil bcolor = new ColorUtil(255, 0, 200, 100);
				/*
				 * if (Nametags.customColor.getValue()) { bcolor =
				 * Nametags.borderColor.getValue(); }
				 */
				for (String s : text) {
					double w = FontUtils.getStringWidth(false, s) / 2;
					if (w > width) {
						width = w;
					}
				}
				drawBorderedRect(-width - 1, -mc.fontRenderer.FONT_HEIGHT, width + 3, new ColorUtil(110, 4, 0, 100),
						bcolor);
			}
			GlStateManager.enableTexture2D();
			for (int i = 0; i < text.length; i++) {
		        if(Main.moduleManager.getModule("ClientFont").isToggled()) {

				FontUtils.drawStringWithShadow(true, text[i], -FontUtils.getStringWidth(false, text[i]) / 2,
						i * (mc.fontRenderer.FONT_HEIGHT + 1) + start, color);
		        }else {
		        	FontUtils.drawStringWithShadow(false, text[i], -FontUtils.getStringWidth(false, text[i]) / 2,
							i * (mc.fontRenderer.FONT_HEIGHT + 1) + start, color);
		        }
			}
			GlStateManager.disableTexture2D();
			if (type != 2) {
				GlStateManager.popMatrix();
			}
		}
	 
	 public static void draw_gradiant_cube(final BufferBuilder buffer, float x, float y, float z, float w, float h, float d, Color startColor, Color endColor, String sides) {
	        int r1 = startColor.getRed();
	        int g1 = startColor.getGreen();
	        int b1 = startColor.getBlue();
	        int a1 = startColor.getAlpha();
	        
	        int r2 = endColor.getRed();
	        int g2 = endColor.getGreen();
	        int b2 = endColor.getBlue();
	        int a2 = endColor.getAlpha();

	        List<String> sidesList = Arrays.asList(sides.split("-"));
	        if (sidesList.contains("north") || sides.equalsIgnoreCase("all")) {
	            buffer.pos(x + w, y, z).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x, y, z).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x, y + h, z).color(r2, g2, b2, a2).endVertex();
	            buffer.pos(x + w, y + h, z).color(r2, g2, b2, a2).endVertex();
	        }

	        if (sidesList.contains("south") || sides.equalsIgnoreCase("all")) {
	            buffer.pos(x, y, z + d).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x + w, y, z + d).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x + w, y + h, z + d).color(r2, g2, b2, a2).endVertex();
	            buffer.pos(x, y + h, z + d).color(r2, g2, b2, a2).endVertex();
	        }

	        if (sidesList.contains("west") || sides.equalsIgnoreCase("all")) {
	            buffer.pos(x, y, z).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x, y, z + d).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x, y + h, z + d).color(r2, g2, b2, a2).endVertex();
	            buffer.pos(x, y + h, z).color(r2, g2, b2, a2).endVertex();
	        }

	        if (sidesList.contains("east") || sides.equalsIgnoreCase("all")) {
	            buffer.pos(x + w, y, z + d).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x + w, y, z).color(r1, g1, b1, a1).endVertex();
	            buffer.pos(x + w, y + h, z).color(r2, g2, b2, a2).endVertex();
	            buffer.pos(x + w, y + h, z + d).color(r2, g2, b2, a2).endVertex();
	        }
	    }
	 
	 public static void draw_gradiant_outline(final BufferBuilder buffer, double x, double y, double z, double height, Color startColor, Color endColor,  String sides) {
	        List<String> sidesList = Arrays.asList(sides.split("-"));
	        boolean drawAll = sides.equalsIgnoreCase("all");
	        if (sidesList.contains("northwest") || drawAll) draw_gradiant_line(buffer, x, y, z, x, y + height, z, startColor, endColor); // NW
	        if (sidesList.contains("northeast") || drawAll) draw_gradiant_line(buffer, x + 1, y, z, x + 1, y + height, z, startColor, endColor); // NE
	        if (sidesList.contains("southwest") || drawAll) draw_gradiant_line(buffer, x, y, z + 1, x, y + height, z + 1, startColor, endColor); // SW
	        if (sidesList.contains("southeast") || drawAll) draw_gradiant_line(buffer, x + 1, y, z + 1, x + 1, y + height, z + 1, startColor, endColor); // SE
	    }
	 
	 public static void draw_gradiant_line(final BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2, Color startColor, Color endColor) {
	        buffer.pos(x1, y1, z1).color(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), startColor.getAlpha()).endVertex();
	        buffer.pos(x2, y2, z2).color(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), endColor.getAlpha()).endVertex();
	    }
	 
	  public static void addChainedFilledBoxVertices(BufferBuilder builder, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
	        builder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        builder.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	    }
	  
	  
	  public static void drawSelectionBoundingBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color color) {
	        bufferbuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	        addChainedBoundingBoxVertices(bufferbuilder, axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, color);
	        tessellator.draw();
	    }

	    public static void addChainedBoundingBoxVertices(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
	        buffer.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	    }

	    public static void drawSelectionGlowFilledBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color startColor, Color endColor) {
	        bufferbuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	        addChainedGlowBoxVertices(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, startColor, endColor);
	        tessellator.draw();
	    }

	    public static void addChainedGlowBoxVertices(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color startColor, Color endColor) {
	        bufferbuilder.pos(minX, minY, minZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, minY, minZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, minY, maxZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, minY, maxZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, maxY, minZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, maxY, maxZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, maxY, maxZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, maxY, minZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, minY, minZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, maxY, minZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, maxY, minZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, minY, minZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, minY, minZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, maxY, minZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, maxY, maxZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, minY, maxZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, minY, maxZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, minY, maxZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(maxX, maxY, maxZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, maxY, maxZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, minY, minZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, minY, maxZ).color(startColor.getRed() / 255.0f, startColor.getGreen() / 255.0f, startColor.getBlue() / 255.0f, startColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, maxY, maxZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	        bufferbuilder.pos(minX, maxY, minZ).color(endColor.getRed() / 255.0f, endColor.getGreen() / 255.0f, endColor.getBlue() / 255.0f, endColor.getAlpha() / 255.0f).endVertex();
	    }

	    public static void drawClawBox(AxisAlignedBB axisAlignedBB, double height, double length, double width, Color color) {
	        bufferbuilder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	        addChainedClawBoxVertices(bufferbuilder, axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + length, axisAlignedBB.maxY + height, axisAlignedBB.maxZ + width, color);
	        tessellator.draw();
	    }

	    public static void addChainedClawBoxVertices(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
	        buffer.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, minY, maxZ - 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, minY, minZ + 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, minY, maxZ - 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, minY, minZ + 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX - 0.8, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX - 0.8, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX + 0.8, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX + 0.8, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, minY + 0.2, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, minY + 0.2, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, minY + 0.2, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, minY + 0.2, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, maxY, maxZ - 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, maxY, minZ + 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, maxY, maxZ - 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, maxY, minZ + 0.8).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX - 0.8, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX - 0.8, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX + 0.8, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX + 0.8, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, maxY - 0.2, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(minX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(minX, maxY - 0.2, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, maxY - 0.2, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	        buffer.pos(maxX, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 0.0F).endVertex();
	        buffer.pos(maxX, maxY - 0.2, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
	    }

	public static void drawBoxBlockPos(BlockPos blockPos, double height, double length, double width, Color color, RenderMode renderMode) {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - mc.getRenderManager().viewerPosX, blockPos.getY() - mc.getRenderManager().viewerPosY, blockPos.getZ() - mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - mc.getRenderManager().viewerPosX, blockPos.getY() + 1 - mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);

        RenderBuilder.glSetup();
        switch (renderMode) {
            case Fill:
                drawSelectionBox(axisAlignedBB, height, length, width, color);
                break;
            case Outline:
                drawSelectionBoundingBox(axisAlignedBB, height, length, width, new Color(color.getRed(), color.getGreen(), color.getBlue(), 144));
                break;
            case Both:
                drawSelectionBox(axisAlignedBB, height, length, width, color);
                drawSelectionBoundingBox(axisAlignedBB, height, length, width, new Color(color.getRed(), color.getGreen(), color.getBlue(), 144));
                break;
            case Glow:
                RenderBuilder.glPrepare();
                drawSelectionGlowFilledBox(axisAlignedBB, height, length, width, color, new Color(color.getRed(), color.getGreen(), color.getBlue(), 0));
                RenderBuilder.glRestore();
                break;
            case Claw:
                drawClawBox(axisAlignedBB, height, length, width, new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
                break;
        }

        RenderBuilder.glRelease();
    }
	
	  public static void drawText(final BlockPos pos, final String text) {
	        GlStateManager.pushMatrix();
	        glBillboardDistanceScaled(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, (EntityPlayer)mc.player, 1.0f);
	        GlStateManager.disableDepth();
	        GlStateManager.translate(-(mc.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
	        if(Main.moduleManager.getModule("ClientFont").isToggled()) {
	        	FontUtils.drawStringWithShadow(true, text, 0, 0, new ColorUtil(255,255,255,255));
	        }else {
	        	FontUtils.drawStringWithShadow(false, text, 0, 0, new ColorUtil(255,255,255,255));

	        }
	        GlStateManager.popMatrix();
	    }
	  
	  public static void glBillboard(final float x, final float y, final float z) {
	        final float scale = 0.02666667f;
	        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
	        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
	        GlStateManager.rotate(-mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
	        GlStateManager.rotate(mc.player.rotationPitch, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
	        GlStateManager.scale(-scale, -scale, scale);
	    }

	  
	  public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
	        glBillboard(x, y, z);
	        final int distance = (int)player.getDistance(x, y, z);
	        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
	        if (scaleDistance < 1.0f) {
	            scaleDistance = 1.0f;
	        }
	        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
	    }

}