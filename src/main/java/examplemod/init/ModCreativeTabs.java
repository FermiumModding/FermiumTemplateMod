package examplemod.init;

import examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.EXAMPLE_BLOCK_ITEM.get());
                output.accept(ModItems.EXAMPLE_DIRECTIONAL_BLOCK_ITEM.get());
                output.accept(ModItems.EXAMPLE_ITEM.get());
                output.accept(ModItems.EXAMPLE_HELMET.get());
                output.accept(ModItems.EXAMPLE_CHESTPLATE.get());
                output.accept(ModItems.EXAMPLE_LEGGINGS.get());
                output.accept(ModItems.EXAMPLE_BOOTS.get());
                output.accept(ModItems.EXAMPLE_ENTITY_SPAWN_EGG.get());
            }).build());
}