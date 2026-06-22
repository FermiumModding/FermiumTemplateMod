package examplemod.datagen;

import examplemod.ExampleMod;
import examplemod.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ExampleMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.EXAMPLE_BLOCK.get())
                .add(ModBlocks.EXAMPLE_DIRECTIONAL_BLOCK.get());

        // Vanilla tags in BlockTags
        this.tag(BlockTags.LOGS)
                .add(ModBlocks.EXAMPLE_DIRECTIONAL_BLOCK.get());

        // Forge tags in Tags.Blocks
        this.tag(Tags.Blocks.NEEDS_GOLD_TOOL)
                .add(ModBlocks.EXAMPLE_BLOCK.get());
    }
}