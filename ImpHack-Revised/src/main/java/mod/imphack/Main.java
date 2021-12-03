package mod.imphack;

import mod.imphack.command.CommandManager;
import mod.imphack.config.Config;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.ImpHackEventHandler;
import mod.imphack.event.ImpHackEventManager;
import mod.imphack.module.ModuleManager;
import mod.imphack.module.modules.hud.*;
import mod.imphack.setting.SettingManager;
import mod.imphack.sound.SongManager;
import mod.imphack.ui.clickgui.ClickGuiController;
import mod.imphack.util.FPSManager;
import mod.imphack.util.Reference;
import mod.imphack.util.font.ImpHackFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

//main class, contains all event handlers etc.

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.RELEASE_VERSION)
@SideOnly(Side.CLIENT)
public class Main {

	public static long startTimeStamp = 0;
	public static ImpHackFontRenderer customFontRenderer;

	final Minecraft mc = Minecraft.getMinecraft();

	public static ModuleManager moduleManager;
	public static Config config;

	public static final HudCoords hudCoords = new HudCoords();
	public static final HudArrayList hudArrayList = new HudArrayList();
	public static final HudWatermark hudVersion = new HudWatermark();
	public static final HudFPS hudFps = new HudFPS();
	public static final HudArmor hudArmor = new HudArmor();
	public static final HudWelcome hudWelcome = new HudWelcome();

	
	

	public static CommandManager cmdManager;
	public static SettingManager settingManager;
	public static ImpHackEventManager eventManager;
	public static SongManager songManager;
    private static FPSManager fpsManager;


	private ClickGuiController gui;
	public static boolean configLoaded = false;

	@Instance
	public Main instance;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (event.getSide() == Side.SERVER)
			return;

		ImpHackEventHandler.INSTANCE = new ImpHackEventHandler();

		// register HUD
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(hudCoords);
		MinecraftForge.EVENT_BUS.register(hudArrayList);
		MinecraftForge.EVENT_BUS.register(hudVersion);
		MinecraftForge.EVENT_BUS.register(hudFps);
		MinecraftForge.EVENT_BUS.register(hudArmor);
		MinecraftForge.EVENT_BUS.register(hudWelcome);

		moduleManager = new ModuleManager();
		cmdManager = new CommandManager();
		settingManager = new SettingManager();
		eventManager = new ImpHackEventManager();
		songManager = new SongManager();
		config = new Config();
        fpsManager = new FPSManager();


		config.Load();
		configLoaded = true;

		MinecraftForge.EVENT_BUS.register(eventManager);

		ImpHackEventBus.EVENT_BUS.subscribe(eventManager);

		startTimeStamp = System.currentTimeMillis();

		ArrayList<ResourceLocation> backgrounds = new ArrayList<>();
		backgrounds.add(new ResourceLocation("textures/1.png"));
		backgrounds.add(new ResourceLocation("textures/2.png"));
		backgrounds.add(new ResourceLocation("textures/3.png"));
		backgrounds.add(new ResourceLocation("textures/4.jpg"));
		backgrounds.add(new ResourceLocation("textures/5.png"));
		backgrounds.add(new ResourceLocation("textures/6.png"));
		backgrounds.add(new ResourceLocation("textures/7.png"));
		backgrounds.add(new ResourceLocation("textures/8.png"));
		backgrounds.add(new ResourceLocation("textures/9.jpg"));
		for (ResourceLocation r : backgrounds)
			this.mc.getTextureManager().bindTexture(r);
	}

	public ClickGuiController getGui() {
		return gui;
	}

	public static ImpHackEventHandler get_event_handler() {
		return ImpHackEventHandler.INSTANCE;
	}
	
	 public static FPSManager getFpsManager() {
	        return Main.fpsManager;
	    }

}
