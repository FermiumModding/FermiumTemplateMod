package examplemod.datagen;

import examplemod.init.ModBlocks;
import examplemod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Shaped crafting: Example Block (Iron ingots in a chest shape)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EXAMPLE_BLOCK.get())
                .pattern("III")
                .pattern("I I")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(consumer);

        // Shapeless crafting: Example Item (Iron + Gold)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.EXAMPLE_ITEM.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.GOLD_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(consumer);

        // Smelting: Example Block -> Example Item
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModBlocks.EXAMPLE_BLOCK.get()),
                        RecipeCategory.MISC,
                        ModItems.EXAMPLE_ITEM.get(), //Output
                        0.7f, //XP
                        200) //Cooking Time
                .unlockedBy("has_example_block", has(ModBlocks.EXAMPLE_BLOCK.get()))
                .save(consumer, "example_item_from_smelting");

        // Blast Furnace and others are separate
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModBlocks.EXAMPLE_BLOCK.get()),
                        RecipeCategory.MISC,
                        ModItems.EXAMPLE_ITEM.get(),
                        0.7f,
                        100)
                .unlockedBy("has_example_block", has(ModBlocks.EXAMPLE_BLOCK.get()))
                .save(consumer, "example_item_from_blasting");
    }
}