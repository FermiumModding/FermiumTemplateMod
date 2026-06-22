package examplemod.init;

import examplemod.ExampleMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ExampleMod.MODID);

    public static final RegistryObject<Enchantment> EXAMPLE_ENCHANTMENT = ENCHANTMENTS.register("example", () -> new Enchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}) {
        @Override
        public int getMaxLevel() {
            return 3;
        }

        @Override
        public int getMinCost(int level) {
            return 10 + (level - 1) * 10;
        }

        @Override
        public int getMaxCost(int level) {
            return this.getMinCost(level) + 50;
        }

        @Override
        public void doPostAttack(LivingEntity attacker, Entity target, int level) {
            // Example: set target on fire based on enchantment level
            if (target instanceof LivingEntity) target.setSecondsOnFire(level * 2);
        }
    });
}