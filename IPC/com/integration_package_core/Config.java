package com.integration_package_core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.stringtemplate.v4.ST;

import java.util.List;
import java.util.Set;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    public static final ForgeConfigSpec.BooleanValue ANGLE_LOCKING = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("ANGLE_LOCKING", true);

    public static final ForgeConfigSpec.BooleanValue TRACE_ATT_TARGET_TYPE = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("TRACE_ATT_TARGET_TYPE", true);



    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("MAGIC_NUMBER", 42, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue MAX_COMBAT_VISUAL_ANGLE = BUILDER
            .comment("A magic number")
            .defineInRange("MAX_COMBAT_VISUAL_ANGLE", 100.0, 0, 1000.0);

    public static final ForgeConfigSpec.DoubleValue COMBAT_VISUAL_ANGLE = BUILDER
            .comment("A magic number")
            .defineInRange("COMBAT_VISUAL_ANGLE", 0, -1.0, 1.0);




    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

 //   static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean log_dirt_block;
    public static int magic_number;

    public static String magic_number_introduction;


    public static boolean angle_locking;

public static boolean trace_att_target_type;

public  static float combat_visual_angle;

    public  static float max_combat_visual_angle;










    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }






    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        log_dirt_block = LOG_DIRT_BLOCK.get();
        magic_number = MAGIC_NUMBER.get();
        magic_number_introduction = MAGIC_NUMBER_INTRODUCTION.get();
        angle_locking=ANGLE_LOCKING.get();
        trace_att_target_type=TRACE_ATT_TARGET_TYPE.get();








        // convert the list of strings into a set of items
    //   items = ITEM_STRINGS.get().stream()
    //           .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
    //           .collect(Collectors.toSet());
    }
}
