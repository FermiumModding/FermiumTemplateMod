package examplemod.datagen;

import examplemod.init.ModBlocks;
import examplemod.init.ModEntities;
import examplemod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY)
                //There's a lot more LootContextParamSets...
        ));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
        // Leave empty
    }

    public static class ModEntityLootTables extends EntityLootSubProvider {

        public ModEntityLootTables() {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate() {
            // Example Entity drops 1-3 Example Items with 0-1 additional Items per Looting lvl
            this.add(ModEntities.EXAMPLE_ENTITY.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(ModItems.EXAMPLE_ITEM.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );

    //        // Multiple items with different drop chances
    //        this.add(ModEntities.EXAMPLE_ENTITY.get(),
    //                LootTable.lootTable()
    //                        .withPool(LootPool.lootPool()
    //                                .setRolls(ConstantValue.exactly(1.0F))
    //                                .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(70))
    //                                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(20))
    //                                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(10))
    //                        )
    //        );
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return ModEntities.ENTITY_TYPES.getEntries().stream()
                    .map(RegistryObject::get);
        }
    }

    public static class ModBlockLootTables extends BlockLootSubProvider {

        public ModBlockLootTables() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            // Block drops itself
            this.dropSelf(ModBlocks.EXAMPLE_BLOCK.get());
            this.dropSelf(ModBlocks.EXAMPLE_DIRECTIONAL_BLOCK.get());

            // Drop something else
             this.dropOther(ModBlocks.EXAMPLE_BLOCK.get(), Items.DIAMOND);

             // Various other loot functions in the super class
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    ::iterator;
        }
    }
}