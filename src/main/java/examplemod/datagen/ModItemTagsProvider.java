package examplemod.datagen;

import examplemod.ExampleMod;
import examplemod.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ExampleMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Copy block tags to item tags, for block items
        // This copies only whatever we changed for BlockTags.LOGS
        copy(BlockTags.LOGS, ItemTags.LOGS);

        // Vanilla tags in ItemTags
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.EXAMPLE_HELMET.get())
                .add(ModItems.EXAMPLE_CHESTPLATE.get())
                .add(ModItems.EXAMPLE_LEGGINGS.get())
                .add(ModItems.EXAMPLE_BOOTS.get());

        // Forge tags in Tags.Items
        this.tag(Tags.Items.INGOTS)
                .add(ModItems.EXAMPLE_ITEM.get());
    }
}