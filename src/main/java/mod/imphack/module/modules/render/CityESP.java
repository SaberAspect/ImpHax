package mod.imphack.module.modules.render;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import mod.imphack.event.events.ImpHackEventRender;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.util.EntityUtil;
import mod.imphack.util.render.RenderUtil;

public class CityESP extends Module {

    public CityESP() {

    	super("CityESP", "Renders the players protected blocks", Category.RENDER);
    	addSetting(endcrystal_mode, mode, off_set,range,r,g,b,a);
    }

    BooleanSetting endcrystal_mode = new BooleanSetting("EndCrystal", this, false);
    ModeSetting mode = new ModeSetting("Mode", this, "Pretty", "Pretty", "Solid", "Outline");
    FloatSetting off_set = new FloatSetting("Height", this, 0.2f);
    IntSetting range = new IntSetting("Range", this, 6);
    IntSetting r = new IntSetting("R", this, 0);
    IntSetting g = new IntSetting("G", this, 255);
    IntSetting b = new IntSetting("B", this, 0);
    IntSetting a = new IntSetting("A", this, 50);

    List<BlockPos> blocks = new ArrayList<>();

    boolean outline = false;
    boolean solid   = false;

    @Override
    public void onUpdate() {
        blocks.clear();
        for (EntityPlayer player : mc.world.playerEntities) {
            if (mc.player.getDistance(player) > range.getValue() || mc.player == player) continue;

            BlockPos p = EntityUtil.is_cityable(player, endcrystal_mode.isEnabled());

            if (p != null) {
                blocks.add(p);
            }
        }
    }

    @Override
    public void render(ImpHackEventRender event) {

        float off_set_h = (float) off_set.getValue();

        for (BlockPos pos : blocks) {

            if (mode.is("Pretty")) {
                outline = true;
                solid   = true;
            }

            if (mode.is("Solid")) {
                outline = false;
                solid   = true;
            }

            if (mode.is("Outline")) {
                outline = true;
                solid   = false;
            }

            if (solid) {
                RenderUtil.prepare("quads");
                RenderUtil.draw_cube(RenderUtil.get_buffer_build(),
                        pos.getX(), pos.getY(), pos.getZ(),
                1, off_set_h, 1,
                r.getValue(), g.getValue(), b.getValue(), a.getValue(),
                "all"
                );

                RenderUtil.release();
            }


            if (outline) {
                RenderUtil.prepare("lines");
                RenderUtil.draw_cube_line(RenderUtil.get_buffer_build(),
                    pos.getX(), pos.getY(), pos.getZ(),
                    1, off_set_h, 1,
                    r.getValue(), g.getValue(), b.getValue(), a.getValue(),
                    "all"
                );

                RenderUtil.release();
            }
        }
    }
}