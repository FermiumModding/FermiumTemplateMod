package examplemod;

import com.mojang.logging.LogUtils;
import examplemod.config.CommonConfig;
import examplemod.init.ModRegistry;
import fermiumbooter.api.config.Config;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ExampleMod.MODID)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static IEventBus MOD_EVENT_BUS;
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(ModRegistry.class);
        MOD_EVENT_BUS = context.getModEventBus();

        MOD_EVENT_BUS.register(Config.class);
        MOD_EVENT_BUS.register(StartupHandler.class);

        ModRegistry.onModLoad(context);

        //With Types CLIENT and SERVER you can modify syncing behavior of the cfgs
        context.registerConfig(ModConfig.Type.COMMON, new CommonConfig().getSpec());
    }

    public static class StartupHandler {
        // there's clientside-specific and dedicated-server-side-specific variants of this event too,
        // called FMLClientSetupEvent and FMLDedicatedServerSetupEvent, running right after preInit
        @SubscribeEvent
        public static void setup(final FMLCommonSetupEvent event) {
            //all of these can use event.enqueueWork for parallel processing
        }

        @SubscribeEvent
        public static void loadComplete(final FMLLoadCompleteEvent event) {

        }
    }
}
