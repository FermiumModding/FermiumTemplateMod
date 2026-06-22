package examplemod.datagen;

import examplemod.ExampleMod;
import examplemod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExampleMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Armor items (generated parent)
        simpleItem(ModItems.EXAMPLE_HELMET);
        simpleItem(ModItems.EXAMPLE_CHESTPLATE);
        simpleItem(ModItems.EXAMPLE_LEGGINGS);
        simpleItem(ModItems.EXAMPLE_BOOTS);

        handheldItem(ModItems.EXAMPLE_ITEM);

        // Spawn egg (template parent)
        withExistingParent(ModItems.EXAMPLE_ENTITY_SPAWN_EGG.getId().getPath(),
                mcLoc("item/template_spawn_egg"));

//        // Example: Item with multiple layers
//        getBuilder(ModItems.EXAMPLE_ITEM.getId().getPath())
//                .parent(new ModelFile.UncheckedModelFile("item/generated"))
//                .texture("layer0", modLoc("item/example_item_layer0"))
//                .texture("layer1", modLoc("item/example_item_layer1"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(ExampleMod.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(ExampleMod.MODID, "item/" + item.getId().getPath()));
    }
}