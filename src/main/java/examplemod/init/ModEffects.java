package examplemod.init;

import examplemod.ExampleMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ExampleMod.MODID);

    public static final RegistryObject<MobEffect> EXAMPLE_EFFECT = MOB_EFFECTS.register("example",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x98D982) {
                @Override
                public void applyEffectTick(LivingEntity entity, int amplifier) {
                    // Example: heal entity by 1 health every tick
                    if (entity.getHealth() < entity.getMaxHealth()) entity.heal(1.0F);
                }

                @Override
                public boolean isDurationEffectTick(int duration, int amplifier) {
                    // Apply every 40 ticks = 2 sec
                    return duration % 40 == 0;
                }

                //If it's an instant potion you use these instead

                @Override
                public boolean isInstantenous(){
                    return false; //set to true for instant
                }

                @Override
                public void applyInstantenousEffect(@Nullable Entity indirectSource, @Nullable Entity trueSource, LivingEntity target, int amp, double mult) {
                    //mult is 1.0 when drank, 0.5 for lingering clouds, 1-distanceToSplash/4 for splash potions, if you want to use it
                }
            }
            //you can give potions one or many attribute modifiers
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, String.valueOf(UUID.nameUUIDFromBytes("examplemod:example_effect".getBytes(StandardCharsets.UTF_8))), 1, AttributeModifier.Operation.ADDITION));
}