package examplemod.config;

import com.google.common.base.Predicates;
import fermiumbooter.api.config.Config;
import fermiumbooter.api.config.IConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonConfig extends Config {
    // These classes are ran as singleton (created in ExampleMod::<init>)
    // so configs can be both static or non-static
    public boolean testBool;
    public static List<Item> testItemList;

    public static TestConfigCategory testCategory;
    public static class TestConfigCategory implements IConfig {
        // Subcategories can also be both static or non-static
        public int testInt;
        public static String testString;

        public TestConfigCategory(CommonConfig cfg) {
            cfg.registerCategory(this, "testCategory", new String[]{"This is a category comment", "With multiple lines", "All comment sections can use multiple lines like this"},
                    List.of(
                            () -> cfg.registerCfg(() -> cfg.BUILDER.comment("c").defineInRange("testInt", 42, 0, Integer.MAX_VALUE), newVal -> this.testInt = newVal),
                            () -> cfg.registerCfg(() -> cfg.BUILDER.comment("c").define("testString", ""), newVal -> testString = newVal)
                    ),
                    new ArrayList<>()
            );
        }
    }

    public CommonConfig() {
        registerCfg(() -> BUILDER.comment("c").define("testBool", true), newVal -> testBool = newVal);

        testCategory = new TestConfigCategory(this);

        registerCfg(() ->
                //Adding validation to the list is not really helpful as failing the validation silently resets the cfg to default (with only a log warn and no preservation of the old cfg values)
                BUILDER.comment("c").defineListAllowEmpty("testItemList", List.of("minecraft:iron_ingot"), Predicates.alwaysTrue()),
                //You can transform while collecting the primitive config value
                x -> testItemList = x.stream().map(s -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(s)))
                        .filter(Objects::nonNull) //we instead can filter here during transformation
                        .collect(Collectors.toList())
        );
    }
}