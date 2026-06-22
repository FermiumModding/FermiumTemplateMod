package examplemod.datagen;

import examplemod.ExampleMod;
import examplemod.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

//Generates block states and block models.
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExampleMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Simple block with same texture on all sides
        simpleBlockWithItem(ModBlocks.EXAMPLE_BLOCK.get(), cubeAll(ModBlocks.EXAMPLE_BLOCK.get()));

        // Directional block with axis orientation and powered state
        getVariantBuilder(ModBlocks.EXAMPLE_DIRECTIONAL_BLOCK.get())
                .forAllStates(state -> {
                    boolean powered = state.getValue(BlockStateProperties.POWERED);
                    String suffix = powered ? "powered" : "unpowered";

                    ResourceLocation side = modLoc("block/example_directional_block_side_" + suffix);
                    ResourceLocation ends = modLoc("block/example_directional_block_front_" + suffix);

                    var axis = state.getValue(BlockStateProperties.AXIS);

                    // X: 90/90, Y: 0/0, Z: 90/0
                    int rotX = axis == Direction.Axis.Y ? 0 : 90;
                    int rotY = axis == Direction.Axis.X ? 90 : 0;

                    return ConfiguredModel.builder()
                            .modelFile(models().cubeColumn(
                                    "example_directional_block_" + suffix,
                                    side,
                                    ends
                            ))
                            .rotationX(rotX)
                            .rotationY(rotY)
                            .build();
                });

        // Item model for the same block (uses unpowered textures, vertical orientation)
        simpleBlockItem(ModBlocks.EXAMPLE_DIRECTIONAL_BLOCK.get(),
                models().cubeColumn("example_directional_block_unpowered",
                        modLoc("block/example_directional_block_side_unpowered"),
                        modLoc("block/example_directional_block_front_unpowered")
                ));
    }
}