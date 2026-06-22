package examplemod.datagen;

import examplemod.ExampleMod;
import examplemod.init.*;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, ExampleMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        // Blocks
        add(ModBlocks.EXAMPLE_BLOCK.get(), "Example Block");
        add(ModBlocks.EXAMPLE_DIRECTIONAL_BLOCK.get(), "Example Directional Block");

        // Items
        add(ModItems.EXAMPLE_ITEM.get(), "Example Item");
        add(ModItems.EXAMPLE_HELMET.get(), "Example Helmet");
        add(ModItems.EXAMPLE_CHESTPLATE.get(), "Example Chestplate");
        add(ModItems.EXAMPLE_LEGGINGS.get(), "Example Leggings");
        add(ModItems.EXAMPLE_BOOTS.get(), "Example Boots");
        add(ModItems.EXAMPLE_ENTITY_SPAWN_EGG.get(), "Example Entity Spawn Egg");

        // Entities
        add(ModEntities.EXAMPLE_ENTITY.get(), "Example Entity");

        // Enchantments
        add(ModEnchantments.EXAMPLE_ENCHANTMENT.get(), "Example Enchantment");

        // Attributes
        add("attribute." + ExampleMod.MODID + ".example_attribute", "Example Attribute");

        // Potions
        addEffect(ModEffects.EXAMPLE_EFFECT, "Example Effect");
        add("item.minecraft.potion.effect.example", "Potion of Example");
        add("item.minecraft.splash_potion.effect.example", "Splash Potion of Example");
        add("item.minecraft.lingering_potion.effect.example", "Lingering Potion of Example");
        add("item.minecraft.tipped_arrow.effect.example", "Arrow of Example");

        // Creative Tab
        add("itemGroup." + ExampleMod.MODID + ".example_tab", "Example Mod");

        // Sounds
        add("subtitles." + ExampleMod.MODID + ".example_sound", "Example Sound");

        // Custom messages/tooltips
        add("message." + ExampleMod.MODID + ".example", "This is an example message!");
    }
}