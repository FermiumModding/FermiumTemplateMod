package examplemod.config;

import com.google.common.base.Predicates;
import fermiumbooter.api.config.Config;
import fermiumbooter.api.config.IConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonConfig extends Config {
    // These classes are ran as singleton (created in ExampleMod::<init>)
    // so configs can be both static or non-static
    public boolean testBool = true;
    public static List<Item> testItemList; //default in CommonConfig.<init>

    public static TestConfigCategory testCategory;

    public CommonConfig() {
        registerCfg(() -> BUILDER.comment("I'm just a bool").define("testBool", testBool), newVal -> testBool = newVal);

        testCategory = new TestConfigCategory(this);

        registerCfg(() ->
                BUILDER.comment("A list, internally seen as list of items").defineListAllowEmpty(
                        "testItemList",
                        List.of("minecraft:iron_ingot"), //default
                        Predicates.alwaysTrue() //Adding validation to the list is not really helpful as failing the validation silently resets the cfg to default (with only a log warn and no preservation of the old cfg values)
                ),
                //You can transform while collecting the primitive config value
                newVal -> testItemList = transformItemList(newVal)
        );
    }

    public static class TestConfigCategory implements IConfig {
        // Subcategories can also be both static or non-static
        public int testInt = 42;
        public static String testString = "";

        public TestConfigCategory(CommonConfig cfg) {
            cfg.registerCategory(this, "testCategory", new String[]{"This is a category comment", "With multiple lines", "All comment sections can use multiple lines like this"},
                    List.of(
                            () -> cfg.registerCfg(() -> cfg.BUILDER.comment("An inner cfg int. If set to 0, will set testString to \"TEST\"").defineInRange("testInt", testInt, 0, Integer.MAX_VALUE), newVal -> this.testInt = newVal),
                            () -> cfg.registerCfg(() -> cfg.BUILDER.comment("Just a string, inside a category. If testInt is set to 0, this is automatically set to TEST").define("testString", testString), newVal -> testString = newVal)
                    ),
                    new ArrayList<>()
            );
        }

        @Override
        public void onCfgReload(ModConfigEvent.Reloading event, Config cfg, boolean postSet) {
            //Example IConfig usage: automatic self corrections
            if (postSet && this.testInt == 0 && !testString.equals("TEST"))
                cfg.setValue("testCategory.testString", "TEST");
        }
    }

    public static List<Item> transformItemList(List<? extends String> itemIdList) {
        return itemIdList.stream()
                .map(name -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}