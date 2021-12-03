package mod.imphack.module.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.imphack.event.events.ImpHackEventMove;
import mod.imphack.event.events.ImpHackEventRenderItem;
import mod.imphack.event.events.ImpHackTransformSideFirstPersonEvent;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ViewModel extends Module {
	
	
	public ViewModel() {
		super("ViewModel", "Models your view", Category.RENDER);
		
		addSetting(mainX);
		addSetting(mainY);
		addSetting(mainZ);
		addSetting(offX);
		addSetting(offY);
		addSetting(offZ);
		addSetting(mainScaleX);
		addSetting(mainScaleY);
		addSetting(mainScaleZ);
		addSetting(offScaleX);
		addSetting(offScaleY);
		addSetting(offScaleZ);


	}
	
	  FloatSetting mainX = new FloatSetting("mainX", this, 1.2f);
	    FloatSetting mainY = new FloatSetting("mainY", this, -0.95f);
	    FloatSetting mainZ = new FloatSetting("mainZ", this,  -1.45f);
	    FloatSetting offX = new FloatSetting("offX", this, -1.2f);
	    FloatSetting offY = new FloatSetting("offY", this, -0.95f);
	    FloatSetting offZ = new FloatSetting("offZ", this, -1.45f);
	    FloatSetting mainScaleX = new FloatSetting("mainScaleX",this, 1.0f);
	    FloatSetting mainScaleY = new FloatSetting("mainScaleY",this, 1.0f);
	    FloatSetting mainScaleZ = new FloatSetting("mainScaleZ",this, 1.0f);
	    FloatSetting offScaleX = new FloatSetting("offScaleX",this, 1.0f);
	    FloatSetting offScaleY = new FloatSetting("offScaleY",this, 1.0f);
	    FloatSetting offScaleZ = new FloatSetting("offScaleZ",this, 1.0f);


		@EventHandler
		private final Listener<ImpHackEventRenderItem> onItemRender = new Listener<>(event -> {
	        event.setMainX(mainX.getValue());
	        event.setMainY(mainY.getValue());
	        event.setMainZ(mainZ.getValue());

	        event.setOffX(offX.getValue());
	        event.setOffY(offY.getValue());
	        event.setOffZ(offZ.getValue());

	        event.setMainHandScaleX(mainScaleX.getValue());
	        event.setMainHandScaleY(mainScaleY.getValue());
	        event.setMainHandScaleZ(mainScaleZ.getValue());

	        event.setOffHandScaleX(offScaleX.getValue());
	        event.setOffHandScaleY(offScaleY.getValue());
	        event.setOffHandScaleZ(offScaleZ.getValue());
	    });
	}