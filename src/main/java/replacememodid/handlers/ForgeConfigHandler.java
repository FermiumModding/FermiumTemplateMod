package replacememodid.handlers;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import replacememodid.ReplaceMeModName;

@Config(modid = ReplaceMeModName.MODID)
public class ForgeConfigHandler {
	
	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@MixinConfig(name = ReplaceMeModName.MODID) //Needed on config classes that contain MixinToggles for those mixins to be added
	public static class ServerConfig {

		@Config.Comment("Example server side config option")
		@Config.Name("Example Server Option")
		public boolean exampleServerOption = true;

		@Config.Comment("Example Early Mixin Toggle Config")
		@Config.Name("Enable Vanilla Player Mixin (Vanilla)")
		@MixinConfig.MixinToggle(earlyMixin = "mixins.replacememodid.vanilla.json", defaultValue = false)
		public boolean enableVanillaMixin = false;

		@Config.Comment("Example Late Mixin Toggle Config")
		@Config.Name("Enable JEI Init Mixin (JEI)")
		@MixinConfig.MixinToggle(lateMixin = "mixins.replacememodid.jei.json", defaultValue = false)
		@MixinConfig.CompatHandling(
				modid = "jei",
				desired = true,
				reason = "Mod needed for this Mixin to properly work"
		)
		public boolean enableJeiMixin = false;
	}

	public static class ClientConfig {

		@Config.Comment("Example client side config option")
		@Config.Name("Example Client Option")
		public boolean exampleClientOption = true;
	}

	@Mod.EventBusSubscriber(modid = ReplaceMeModName.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(ReplaceMeModName.MODID)) {
				ConfigManager.sync(ReplaceMeModName.MODID, Config.Type.INSTANCE);
			}
		}
	}
}