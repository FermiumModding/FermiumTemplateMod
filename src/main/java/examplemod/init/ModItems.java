package examplemod.init;

import examplemod.ExampleMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new Item(new Item.Properties()
                    //Example: food, theres various others
                    .food(new FoodProperties.Builder()
                            .alwaysEat()
                            .nutrition(1)
                            .saturationMod(2f)
                            .build())));

    public static final RegistryObject<Item> EXAMPLE_ENTITY_SPAWN_EGG = ITEMS.register("example_entity_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.EXAMPLE_ENTITY, 0x7E9680, 0xC5D1C5, new Item.Properties()));

    //The items for the blocks also need to be registered
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block",
            () -> new BlockItem(ModBlocks.EXAMPLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE_DIRECTIONAL_BLOCK_ITEM = ITEMS.register("example_directional_block",
            () -> new BlockItem(ModBlocks.EXAMPLE_DIRECTIONAL_BLOCK.get(), new Item.Properties()));

    // Custom Armor Material for the armor items below
    public static final ArmorMaterial EXAMPLE_ARMOR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return switch (type) { //this is fixed in vanilla, HEALTH_FUNCTION_FOR_TYPE, values copied to here
                case HELMET -> 11;
                case CHESTPLATE -> 16;
                case LEGGINGS -> 15;
                case BOOTS -> 13;
            } * 15; //Total multiplier on top, variable per material
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type){ // These are variable per vanilla type and are multipliers for the durability
                case HELMET -> 2;
                case CHESTPLATE -> 5;
                case LEGGINGS -> 3;
                case BOOTS -> 1;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return 9; //max vanilla is gold with 25
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_GENERIC;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.MAGMA_CREAM);
        }

        @Override
        public String getName() {
            return ExampleMod.MODID + ":example_material";
        }

        @Override public float getToughness() {return 0.0F;}
        @Override public float getKnockbackResistance() {return 0.0F;}
    };
    public static final RegistryObject<Item> EXAMPLE_HELMET = ITEMS.register("example_helmet",
            () -> new ArmorItem(EXAMPLE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE_CHESTPLATE = ITEMS.register("example_chestplate",
            () -> new ArmorItem(EXAMPLE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE_LEGGINGS = ITEMS.register("example_leggings",
            () -> new ArmorItem(EXAMPLE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE_BOOTS = ITEMS.register("example_boots",
            () -> new ArmorItem(EXAMPLE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()));
}