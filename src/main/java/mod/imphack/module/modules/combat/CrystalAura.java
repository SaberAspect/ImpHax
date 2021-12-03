package mod.imphack.module.modules.combat;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.init.Blocks;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.Client;
import mod.imphack.Main;
import mod.imphack.event.ImpHackEventStage;
import mod.imphack.event.events.ImpHackEventEntityRemoved;
import mod.imphack.event.events.ImpHackEventMotionUpdate;
import mod.imphack.event.events.ImpHackEventPacket;
import mod.imphack.event.events.ImpHackEventRender;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.ColorSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.util.BlockUtil;
import mod.imphack.util.CrystalUtil;
import mod.imphack.util.EntityUtil;
import mod.imphack.util.MathUtil;
import mod.imphack.util.Key;
import mod.imphack.util.Reference;
import mod.imphack.util.RotationUtil;
import mod.imphack.util.Timer;
import mod.imphack.util.render.ColorUtil;
import mod.imphack.util.render.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;



public class CrystalAura extends Module {
	
	BooleanSetting debug = new BooleanSetting("Debug", this, false);
	BooleanSetting place_crystal = new BooleanSetting("Place", this, true);
	BooleanSetting break_crystal = new BooleanSetting("Break", this, true);
    IntSetting break_trys = new IntSetting("Break Attempts", this, 1);
    BooleanSetting anti_weakness = new BooleanSetting("Anti-Weakness", this, true);
    FloatSetting hit_range = new FloatSetting("Hit Range", this, 1f);
    FloatSetting place_range = new FloatSetting("Place Range", this, 1f);
    FloatSetting hit_range_wall = new FloatSetting("Range Wall", this, 1f);
    IntSetting place_delay = new IntSetting("Place Delay", this, 0);
    IntSetting break_delay = new IntSetting("Break Delay", this, 0);
    IntSetting min_player_place = new IntSetting("Min Enemy Place", this, 0);
    IntSetting min_player_break = new IntSetting("Min Enemy Break", this,  0);
    IntSetting max_self_damage = new IntSetting("Max Self Damage", this,  0);
    ModeSetting rotate_mode = new ModeSetting("Rotate", this, "Good", "Good","Off", "Old", "Const");
    BooleanSetting raytrace = new BooleanSetting("Raytrace", this, false);
    ModeSetting switch_mode = new ModeSetting("Switch", this, "Swap", "Swap", "Silent", "Off");
    BooleanSetting anti_suicide = new BooleanSetting("Anti Suicide", this, true);
    BooleanSetting fast_mode = new BooleanSetting("Fast Mode", this, true);
    BooleanSetting client_side = new BooleanSetting("Client Side", this, false);
    BooleanSetting jumpy_mode = new BooleanSetting("Jumpy Mode", this, false);
    BooleanSetting attempt_chain = new BooleanSetting("Attemp Chain", this, false);
    BooleanSetting anti_stuck = new BooleanSetting("Anti Stuck", this, false);
    BooleanSetting endcrystal = new BooleanSetting("1.13 Mode", this, false);
    BooleanSetting faceplace_mode = new BooleanSetting("FacePlace Mode", this, true);
    IntSetting faceplace_mode_damage = new IntSetting("T Health", this, 0);
    BooleanSetting fuck_armor_mode = new BooleanSetting("Armor Destroy", this, true);
    IntSetting fuck_armor_mode_precent = new IntSetting("Armor %", this, 0);
    BooleanSetting stop_while_mining = new BooleanSetting("Stop While Mining", this, false);
    BooleanSetting faceplace_check = new BooleanSetting("No Sword FP", this, false);
    ModeSetting swing = new ModeSetting("Swing", this, "Mainhand", "Mainhand", "Offhand", "Both", "None");
    ModeSetting render_mode = new ModeSetting("Render", this, "Pretty", "Pretty", "Solid", "Outline", "Glow", "Glow 2", "None");
    BooleanSetting top_block = new BooleanSetting("Top Block", this, false);
    BooleanSetting render_damage = new BooleanSetting("Render Damage", this, true);
    IntSetting chain_length = new IntSetting("Chain Length", this, 1);
    IntSetting r = new IntSetting("R", this, 255);
    IntSetting g = new IntSetting("G", this, 255);
    IntSetting b = new IntSetting("B", this, 255);
    IntSetting a = new IntSetting("A", this, 100);
    IntSetting a_out = new IntSetting("Outline A", this, 255);
    FloatSetting sat = new FloatSetting("Satiration", this, 0.8f);
    FloatSetting brightness = new FloatSetting("Brightness", this, 0.8f);
    FloatSetting height = new FloatSetting("Height", this, 1.0f);
 
	public CrystalAura() {
		super("CrystalAura", "places and breaks Crystals", Category.COMBAT);

		addSetting(debug,place_crystal,break_crystal,break_trys,anti_weakness,hit_range,place_range,hit_range_wall,place_delay,break_delay,min_player_place,min_player_break,max_self_damage,rotate_mode,raytrace,
				switch_mode,anti_suicide,fast_mode,client_side,jumpy_mode,attempt_chain,anti_stuck,endcrystal,faceplace_mode,faceplace_mode_damage,fuck_armor_mode,fuck_armor_mode_precent,stop_while_mining,
				faceplace_check, swing,render_mode,top_block,render_damage,chain_length, r, g,b,a,a_out,sat,brightness,height);
		


	}
	
	 

	    private final ConcurrentHashMap<EntityEnderCrystal, Integer> attacked_crystals = new ConcurrentHashMap<>();
	    private static EntityPlayer ca_target = null;

	    private final Timer remove_visual_timer = new Timer();
	    private final Timer chain_timer = new Timer();

	    private EntityPlayer autoez_target = null;

	    private String detail_name = null;
	    private int detail_hp = 0;

	    private BlockPos render_block_init;

	    private double render_damage_value;

	    private float yaw;
	    private float pitch;

	    private boolean already_attacking = false;
	    private boolean place_timeout_flag = false;
	    private boolean is_rotating;
	    private boolean did_anything;
	    private boolean outline;
	    private boolean solid;
	    private boolean glow;
	    private boolean glowLines;

	    private int chain_step = 0;
	    private int current_chain_index = 0;
	    private int place_timeout;
	    private int break_timeout;
	    private int break_delay_counter;
	    private int place_delay_counter;
	    private int prev_slot;

	    @EventHandler
	    private final Listener<ImpHackEventEntityRemoved> on_entity_removed = new Listener<>(event -> {
	        if (event.get_entity() instanceof EntityEnderCrystal) {
	            attacked_crystals.remove(event.get_entity());
	        }
	    });

	    @EventHandler
	    private final Listener<ImpHackEventPacket.SendPacket> send_listener = new Listener<>(event -> {
	        if (event.getPacket() instanceof CPacketPlayer && is_rotating && rotate_mode.getMode().equalsIgnoreCase("Old")) {
		        if (debug.isEnabled()) {
	        		Client.addChatMessage("CrystalAura: Rotating");
	        	}
	        	
	        	
	            final CPacketPlayer p = (CPacketPlayer) event.getPacket();
	            p.yaw = yaw;
	            p.pitch = pitch;
	            is_rotating = false;
	        }
	        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && is_rotating && rotate_mode.getMode().equalsIgnoreCase("old")) {
	            final CPacketPlayerTryUseItemOnBlock p = (CPacketPlayerTryUseItemOnBlock) event.getPacket();
	            
		        if (debug.isEnabled()) {
	        		Client.addChatMessage("CrystalAura: Rotating");
	        	}
	            p.facingX = render_block_init.getX();
	            p.facingY = render_block_init.getY();
	            p.facingZ = render_block_init.getZ();
	            is_rotating = false;
	        }
	    });

	    @EventHandler
	    private final Listener<ImpHackEventMotionUpdate> on_movement = new Listener<>(event -> {
	        if (event.stage == 0 && (rotate_mode.is("Good") || rotate_mode.is("Const"))) {
		        if (debug.isEnabled()) {
	        		Client.addChatMessage("CrystalAura: Updating Rotation");
	        	}
	            EntityUtil.updatePosition();
	            RotationUtil.updateRotations();
	            do_ca();
	        }
	        if (event.stage == 1 && (rotate_mode.is("Good") || rotate_mode.is("Const"))) {
		        if (debug.isEnabled()) {
	        		Client.addChatMessage("CrystalAura: Resetting Rotation");
	        	}
		        EntityUtil.restorePosition();
	            RotationUtil.restoreRotations();
	        }
	    });

	    @EventHandler
	    private final Listener<ImpHackEventPacket.ReceivePacket> receive_listener = new Listener<>(event -> {
	        if (event.getPacket() instanceof SPacketSoundEffect) {
	            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();

	            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
	                for (Entity e : mc.world.loadedEntityList) {
	                    if (e instanceof EntityEnderCrystal) {
	                        if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
	                            e.setDead();
	                        }
	                    }
	                }
	            }
	        }

	    });

	    public void do_ca() {
	        did_anything = false;

	        if (mc.player == null || mc.world == null) return;

	       
	        

	        if (remove_visual_timer.passedMs(1000)) {
	            remove_visual_timer.reset();
	            attacked_crystals.clear();
	        }

	        if (check_pause()) {
	            return;
	        }

	        if (place_crystal.isEnabled() && place_delay_counter > place_timeout) {
	            place_crystal();
	        }

	        if (break_crystal.isEnabled() && break_delay_counter > break_timeout) {
	            break_crystal();
	        }

	        if (!did_anything) {
	            
	            autoez_target = null;
	            is_rotating = false;
	        }

	       

	        if (chain_timer.passedMs(1000)) {
	            chain_timer.reset();
	            chain_step = 0;
	        }


	        break_delay_counter++;
	        place_delay_counter++;
	    }

	    @Override
	    public void onUpdate() {
	        if (rotate_mode.is("Off") || rotate_mode.is("Old")) {
	            do_ca();
	        }

	        if (mc.player.isDead || mc.player.getHealth() <= 0) ca_target = null;
	    }

	    

	    public EntityEnderCrystal get_best_crystal() {
	        double best_damage = 0;

	        double minimum_damage;
	        double maximum_damage_self = this.max_self_damage.getValue();

	        double best_distance = 0;

	        EntityEnderCrystal best_crystal = null;

	        for (Entity c : mc.world.loadedEntityList) {

	            if (!(c instanceof EntityEnderCrystal)) continue;

	            EntityEnderCrystal crystal = (EntityEnderCrystal) c;
	            if (mc.player.getDistance(crystal) > (!mc.player.canEntityBeSeen(crystal) ? hit_range_wall.getValue() : hit_range.getValue())) {
	                continue;
	            }
	            if (!mc.player.canEntityBeSeen(crystal) && raytrace.isEnabled()) {
	                continue;
	            }
	            if (crystal.isDead) continue;

	            if (attacked_crystals.containsKey(crystal) && attacked_crystals.get(crystal) > 5 && anti_stuck.isEnabled()) continue;

	            for (Entity player : mc.world.playerEntities) {

	                if (player == mc.player || !(player instanceof EntityPlayer)) continue;


	                if (player.getDistance(mc.player) >= 11) continue; // stops lag

	                final EntityPlayer target = (EntityPlayer) player;

	                if (target.isDead || target.getHealth() <= 0) continue;

	                boolean no_place = faceplace_check.isEnabled() && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD;
	                if ((target.getHealth() < faceplace_mode_damage.getValue() && faceplace_mode.isEnabled() && !no_place) || (get_armor_fucker(target) && !no_place)) {
	                    minimum_damage = 2;
	                } else {
	                    minimum_damage = this.min_player_break.getValue();
	                }

	                final double target_damage = CrystalUtil.calculateDamage(crystal, target);

	                if (target_damage < minimum_damage) continue;

	                final double self_damage = CrystalUtil.calculateDamage(crystal, mc.player);

	                if (self_damage > maximum_damage_self || (anti_suicide.isEnabled() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) - self_damage <= 0.5)) continue;

	                if (target_damage > best_damage && !jumpy_mode.isEnabled()) {
	                    autoez_target = target;
	                    best_damage = target_damage;
	                    best_crystal = crystal;
	                }
	            }

	            if (jumpy_mode.isEnabled() && mc.player.getDistanceSq(crystal) > best_distance) {
	                best_distance = mc.player.getDistanceSq(crystal);
	                best_crystal = crystal;
	            }
	        }

	        return best_crystal;
	    }

	    public BlockPos get_best_block() {
	        if (get_best_crystal() != null && !fast_mode.isEnabled()) {
	            place_timeout_flag = true;
	            return null;
	        }
	        if (place_timeout_flag) {
	            place_timeout_flag = false;
	            return null;
	        }
	        List<Key<Double, BlockPos>> damage_blocks = new ArrayList<>();
	        double best_damage = 0;
	        double bestBlockSelfDamage = 0;
	        double minimum_damage;
	        double maximum_damage_self = this.max_self_damage.getValue();

	        BlockPos best_block = null;

	        List<BlockPos> blocks = CrystalUtil.possiblePlacePositions((float) place_range.getValue(), endcrystal.isEnabled(), true);

	        for (Entity player : mc.world.playerEntities) {


	            for (BlockPos block : blocks) {

	                if (player == mc.player || !(player instanceof EntityPlayer)) continue;

	                if (player.getDistance(mc.player) >= 11) continue;

	                if (!BlockUtil.rayTracePlaceCheck(block, this.raytrace.isEnabled())) {
	                    continue;
	                }

	                if (!BlockUtil.canSeeBlock(block) && mc.player.getDistance(block.getX(), block.getY(), block.getZ()) > hit_range_wall.getValue()) {
	                    continue;
	                }

	                final EntityPlayer target = (EntityPlayer) player;

	                ca_target = target;

	                if (target.isDead || target.getHealth() <= 0) continue;

	                boolean no_place = faceplace_check.isEnabled() && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD;
	                if ((target.getHealth() < faceplace_mode_damage.getValue() && faceplace_mode.isEnabled()&& !no_place) || (get_armor_fucker(target) && !no_place)) {
	                    minimum_damage = 2;
	                } else {
	                    minimum_damage = this.min_player_place.getValue();
	                }

	                final double target_damage = CrystalUtil.calculateDamage((double) block.getX() + 0.5, (double) block.getY() + 1, (double) block.getZ() + 0.5, target);

	                if (target_damage < minimum_damage) continue;

	                final double self_damage = CrystalUtil.calculateDamage((double) block.getX() + 0.5, (double) block.getY() + 1, (double) block.getZ() + 0.5, mc.player);

	                if (self_damage > maximum_damage_self || (anti_suicide.isEnabled() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) - self_damage <= 0.5)) continue;

	                if (target_damage > best_damage) {
	                    best_damage = target_damage;
	                    best_block = block;
	                    bestBlockSelfDamage = self_damage;
	                    autoez_target = target;
	                } else if (target_damage == best_damage && self_damage < bestBlockSelfDamage) {
	                    best_damage = target_damage;
	                    best_block = block;
	                    bestBlockSelfDamage = self_damage;
	                    autoez_target = target;
	                }
	            }
	            if (best_block == null) ca_target = null;
	        }
	        
	        

	        blocks.clear();

	        if (chain_step == 1) {
	            current_chain_index = chain_length.getValue();
	        } else if (chain_step > 1) {
	            current_chain_index--;
	        } 

	        render_damage_value = best_damage;
	        render_block_init = best_block;

	        damage_blocks = sort_best_blocks(damage_blocks);

	        if (!attempt_chain.isEnabled()) {
	            return best_block;
	        } else {
	            if (damage_blocks.size() == 0) {
	                return null;
	            }
	            if (damage_blocks.size() < current_chain_index) {
	                return damage_blocks.get(0).getValue();
	            }
	            return damage_blocks.get(current_chain_index).getValue();
	        }
	    }

	    public List<Key<Double, BlockPos>> sort_best_blocks(List<Key<Double, BlockPos>> list) {
	        List<Key<Double, BlockPos>> new_list = new ArrayList<>();
	        double damage_cap = 1000;
	        for (int i = 0; i < list.size(); i++) {
	            double biggest_dam = 0;
	            Key<Double, BlockPos> best_pair = null;
	            for (Key<Double, BlockPos> pair : list) {
	                if (pair.getKey() > biggest_dam && pair.getKey() < damage_cap) {
	                    best_pair = pair;
	                }
	            }
	            if (best_pair == null) continue;
	            damage_cap = best_pair.getKey();
	            new_list.add(best_pair);
	        }
	        return new_list;
	    }

	    public void place_crystal() {
	        BlockPos target_block = get_best_block();

	        if (target_block == null) {
	            return;
	        }

	        place_delay_counter = 0;

	        already_attacking = false;

	        boolean offhand_check = false;
	        prev_slot = mc.player.inventory.currentItem;
	        int crystal_slot = find_crystals_hotbar();
	        if (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
	            if (mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
	                if (crystal_slot == -1) return;
	                if (switch_mode.is("Swap")) {
	                    mc.player.inventory.currentItem = crystal_slot;
	                    return;
	                } else if (switch_mode.is("Silent")) {
	                    mc.player.connection.sendPacket(new CPacketHeldItemChange(crystal_slot));
	                } else if (switch_mode.is("Off")) {
	                    return;
	                }
	            }
	        } else {
	            offhand_check = true;
	        }

	        if (debug.isEnabled()) {
	            Client.addChatMessage("CrystalAura: placing");
	        }

	        chain_step++;
	        did_anything = true;
	        rotate_to_pos(target_block);
	        chain_timer.reset();
	        BlockUtil.placeCrystalOnBlock(target_block, offhand_check ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
	        if (switch_mode.is("Silent")) {
	            mc.player.connection.sendPacket(new CPacketHeldItemChange(prev_slot));
	        }
	    }
	    
	    

	    public boolean get_armor_fucker(EntityPlayer p) {
	        for (ItemStack stack : p.getArmorInventoryList()) {

	            if (stack == null || stack.getItem() == Items.AIR) return true;

	            final float armor_percent = ((float) (stack.getMaxDamage() - stack.getItemDamage()) / (float) stack.getMaxDamage()) * 100.0f;

	            if (fuck_armor_mode.isEnabled() && fuck_armor_mode_precent.getValue() >= armor_percent) return true;
	        }

	        return false;

	    }

	    public void break_crystal() {
	        EntityEnderCrystal crystal = get_best_crystal();
	        if (crystal == null) {
	            return;
	        }

	        if (anti_weakness.isEnabled() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
	            boolean should_weakness = true;

	            if (mc.player.isPotionActive(MobEffects.STRENGTH)) {
	                if (Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.STRENGTH)).getAmplifier() == 2) {
	                    should_weakness = false;
	                }
	            }

	            if (should_weakness) {

	                if (!already_attacking) {
	                    already_attacking = true;
	                }

	                int new_slot = -1;

	                for (int i = 0; i < 9; i++) {

	                    ItemStack stack = mc.player.inventory.getStackInSlot(i);

	                    if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
	                        new_slot = i;
	                        mc.playerController.updateController();
	                        break;
	                    }
	                }

	                if (new_slot != -1) {
	                    mc.player.inventory.currentItem = new_slot;
	                }
	            }
	        }

	        if (debug.isEnabled()) {
	            Client.addChatMessage("CrystalAura: attacking");
	        }

	        did_anything = true;

	        rotate_to(crystal);
	        for (int i = 0; i < break_trys.getValue(); i++) {
	            EntityUtil.attackEntity(crystal, false, swing);
	        }
	        add_attacked_crystal(crystal);

	        if (client_side.isEnabled() && crystal.isEntityAlive()) {
	            crystal.setDead();
	        }

	        break_delay_counter = 0;
	    }

	    public boolean check_pause() {
	        if (find_crystals_hotbar() == -1 && mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
	            return true;
	        }

	        if (stop_while_mining.isEnabled() && mc.gameSettings.keyBindAttack.isKeyDown() && mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
	           
	            return true;
	        }

	        Surround s = (Surround) Main.moduleManager.getModule("Surround");
	        if (s.blocksSurround()) {
	           
	            return true;
	        }

	       
	        return false;
	    }

	    private int find_crystals_hotbar() {
	        for (int i = 0; i < 9; i++) {
	            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
	                return i;
	            }
	        }
	        return -1;
	    }

	    private void add_attacked_crystal(EntityEnderCrystal crystal) {
	        if (attacked_crystals.containsKey(crystal)) {
	            int value = attacked_crystals.get(crystal);
	            attacked_crystals.put(crystal, value + 1);
	        } else {
	            attacked_crystals.put(crystal, 1);
	        }
	    }

	    public void rotate_to_pos(final BlockPos pos) {
	        final float[] angle;
	        if (rotate_mode.is("Const")) {
	            angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f));
	        } else {
	            angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f));
	        }
	        if (rotate_mode.is("Off")) {
	            is_rotating = false;
	        }
	        if (rotate_mode.is("Good") || rotate_mode.is("Const")) {
	            RotationUtil.setPlayerRotations(angle[0], angle[1]);
	        }
	        if (rotate_mode.is("Old")) {
	            yaw = angle[0];
	            pitch = angle[1];
	            is_rotating = true;
	        }
	    }

	    public void rotate_to(final Entity entity) {
	        final float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionVector());
	        if (rotate_mode.is("Off")) {
	            is_rotating = false;
	        }
	        if (rotate_mode.is("Good")) {
	            RotationUtil.setPlayerRotations(angle[0], angle[1]);
	        }
	        if (rotate_mode.is("Old") || rotate_mode.is("Const")) {
	            yaw = angle[0];
	            pitch = angle[1];
	            is_rotating = true;
	        }
	    }

	    @Override
	    public void render(ImpHackEventRender event) {

	        if (render_block_init == null) return;

	        if (render_mode.is("None")) return;

	        if (render_mode.is("Pretty")) {
	            outline = true;
	            solid = true;
	            glow = false;
	            glowLines = false;
	        }

	        if (render_mode.is("Solid")) {
	            outline = false;
	            solid = true;
	            glow = false;
	            glowLines = false;
	        }

	        if (render_mode.is("Outline")) {
	            outline = true;
	            solid = false;
	            glow = false;
	            glowLines = false;
	        } 

	        if (render_mode.is("Glow")) {
	            outline = false;
	            solid = false;
	            glow = true;
	            glowLines = false;
	        }

	        if (render_mode.is("Glow 2")) {
	            outline = false;
	            solid = false;
	            glow = true;
	            glowLines = true;
	        }

	        render_block(render_block_init);

	        

	        

	    }

	    public void render_block(BlockPos pos) {
	        BlockPos render_block = (top_block.isEnabled() ? pos.up() : pos);

	        if (render_damage.isEnabled() && !top_block.isEnabled()) {
	            RenderUtil.drawText(render_block_init, ((Math.floor(this.render_damage_value) == this.render_damage_value) ? Integer.valueOf((int)this.render_damage_value) : String.format("%.1f", this.render_damage_value)) + "");
	        }
	            else if(render_damage.isEnabled() && top_block.isEnabled()) {
		            RenderUtil.drawText(render_block, ((Math.floor(this.render_damage_value) == this.render_damage_value) ? Integer.valueOf((int)this.render_damage_value) : String.format("%.1f", this.render_damage_value)) + "");

	            }
	        
	        
	        float h = (float) height.getValue();

	        if (solid) {
	            RenderUtil.prepare("quads");
	            RenderUtil.draw_cube(RenderUtil.get_buffer_build(),
	                    render_block.getX(), render_block.getY(), render_block.getZ(),
	                    1, h, 1,
	                    r.getValue(), g.getValue(), b.getValue(), a.getValue(),
	                    "all"
	            );
	            RenderUtil.release();
	        }

	        if (outline) {
	            RenderUtil.prepare("lines");
	            RenderUtil.draw_cube_line(RenderUtil.get_buffer_build(),
	                    render_block.getX(), render_block.getY(), render_block.getZ(),
	                    1, h, 1,
	                    r.getValue(), g.getValue(), b.getValue(), a_out.getValue(),
	                    "all"
	            );
	            RenderUtil.release();
	        }

	        if (glow) {
	            RenderUtil.prepare("lines");
	            RenderUtil.draw_cube_line(RenderUtil.get_buffer_build(),
	                    render_block.getX(), render_block.getY(), render_block.getZ(),
	                    1, 0, 1,
	                    r.getValue(), g.getValue(), b.getValue(), a_out.getValue(),
	                    "all"
	            );
	            RenderUtil.release();
	            RenderUtil.prepare("quads");
	            RenderUtil.draw_gradiant_cube(RenderUtil.get_buffer_build(), 
	                    render_block.getX(), render_block.getY(), render_block.getZ(), 
	                    1, h, 1,  new Color(r.getValue(), g.getValue(), b.getValue(), a.getValue()),
	                    new Color(0, 0, 0, 0), 
	                    "all"
	            );
	            RenderUtil.release();
	        }

	        if (glowLines) {
	            RenderUtil.prepare("lines");
	            RenderUtil.draw_gradiant_outline(RenderUtil.get_buffer_build(), 
	                    render_block.getX(), render_block.getY(), render_block.getZ(), 
	                    h, new Color(r.getValue(), g.getValue(), b.getValue(), a_out.getValue()), 
	                    new Color(0, 0, 0, 0),
	                    "all"
	            );
	            RenderUtil.release();
	        }
	    }

	    @Override
	    public void onEnable() {
	        place_timeout = this.place_delay.getValue();
	        break_timeout = this.break_delay.getValue();
	        place_timeout_flag = false;
	        is_rotating = false;
	        autoez_target = null;
	        chain_step = 0;
	        current_chain_index = 0;
	        chain_timer.reset();
	        remove_visual_timer.reset();
	        detail_name = null;
	        detail_hp = 20;
	    }

	    @Override
	    public void onDisable() {
	        render_block_init = null;
	        autoez_target = null;
	    }

	   


	    public static EntityPlayer get_target() {
	        return ca_target;
	    }
	}

