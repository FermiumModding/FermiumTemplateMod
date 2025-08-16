package replacememodid;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import replacememodid.capability.CapabilityExampleHandler;
import replacememodid.handlers.ModRegistry;
import replacememodid.proxy.CommonProxy;

@Mod(modid = ReplaceMeModName.MODID, version = ReplaceMeModName.VERSION, name = ReplaceMeModName.NAME)
public class ReplaceMeModName {
    public static final String MODID = "replacememodid";
    public static final String VERSION = "ReplaceMe.Mod.Version";
    public static final String NAME = "ReplaceMeModName";
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean completedLoading = false;
	
    @SidedProxy(clientSide = "replacememodid.proxy.ClientProxy", serverSide = "replacememodid.proxy.CommonProxy")
    public static CommonProxy PROXY;
	
	@Instance(MODID)
	public static ReplaceMeModName instance;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        ReplaceMeModName.PROXY.preInit();

        CapabilityExampleHandler.registerCapability();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        completedLoading = true;
    }
}