package examplemod.entity.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import examplemod.ExampleMod;
import examplemod.entity.client.model.ExampleEntityModel;
import examplemod.entity.ExampleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ExampleEntityRenderer extends MobRenderer<ExampleEntity, ExampleEntityModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ExampleMod.MODID, "textures/entity/example_entity.png");

    public ExampleEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ExampleEntityModel(context.bakeLayer(ExampleEntityModel.LAYER_LOCATION)), 0.5F);
    }

    @NotNull @Override
    public ResourceLocation getTextureLocation(@NotNull ExampleEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(ExampleEntity entity, PoseStack poseStack, float partialTick) {
        // Optional: scale the entity
        poseStack.scale(0.9F, 0.9F, 0.9F);
    }
}