package mod.imphack.module;

import me.zero.alpine.listener.Listenable;
import mod.imphack.Main;
import mod.imphack.command.commands.Bind;
import mod.imphack.container.ImpHackInventory;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.ImpHackEventConnection;
import mod.imphack.event.events.ImpHackEventRender;
import mod.imphack.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module implements Listenable {

	protected static final Minecraft mc = Minecraft.getMinecraft();
	protected final ImpHackInventory inv = new ImpHackInventory();
	public final String name;
	public String description;
	public int key;
	
	private final Category category;
	public boolean toggled;
	private boolean enabled;

	public final List<Setting> settings = new ArrayList <>();
	public final List<GuiButton> buttons = new ArrayList <>();

	public Module(String name, String description, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.key = 0;
		this.category = category;
		this.toggled = false;
		this.enabled = false;

	}

	public void addButton(GuiButton... buttons) {
		this.buttons.addAll(Arrays.asList(buttons));
	}

	public void addSetting(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	 public int getBind() {
	        return key;
	    }
	    
	    public void setBind(final int key) {
	        this.key = key;
	        Main.config.Save();
	    }

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;

		if (this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
	}

	public void toggle() {
		this.toggled = !this.toggled;

		if (this.toggled) {
			onEnable();
		} else {
			onDisable();
		}
	}
	protected void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);

		ImpHackEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();
	}
	public void enable() {
		if (!this.isEnabled()) {
			this.enabled = true;
			try {
				this.onEnable();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void disable() {
		if (this.isEnabled()) {
			this.enabled = false;
			try {
				this.onDisable();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}


	protected void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);

		ImpHackEventBus.EVENT_BUS.unsubscribe(this);

		Main.config.Save();
	}

	public String getName() {
		return this.name;
	}

	public Category getCategory() {
		return this.category;
	}

	public void onUpdate() {
	}

	public void render(ImpHackEventRender event) {
		// 3d
	}

	public void render() {
		// 2d
	}

	public void onKeyPress() {
	}

	public void actionPerformed(GuiButton b) {
	}

	public void onRenderWorldLast(ImpHackEventRender event) {		
	}
	
	public void onWorldRender(ImpHackEventRender event) {
		
	}
	 public void setup() {
	    }
	 
	 public boolean onPacket(Object packet, ImpHackEventConnection.Side side) {
	        return true;
	    }
	  public void onClientTick(TickEvent.ClientTickEvent event) {
	    }
	  public String getMetaData() {
	        return null;
	    }
	  
	  
}