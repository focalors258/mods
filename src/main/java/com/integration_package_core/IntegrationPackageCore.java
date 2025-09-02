package com.integration_package_core;

//import com.integration_package_core.util.RegistryHandler.newMenu;
import com.integration_package_core.animation.ClientSync;
import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.event.Event.KeyBindsEvent;
import com.integration_package_core.util.RegistryHandler.EntityRegistry;
import com.integration_package_core.util.RegistryHandler.MenuRegistry;
import com.integration_package_core.util.RegistryHandler.NetworkRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IntegrationPackageCore.MODID)
public  class IntegrationPackageCore
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "integration_package_core";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
//方块注册
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    //物品注册
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    //界面注册

//属性注册
    public static final DeferredRegister<Attribute> ATTRIBUTE= DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);





    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());





    public IntegrationPackageCore(FMLJavaModLoadingContext context)
    {



        //获取事件总线
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //  方块注册添加进事件线
        BLOCKS.register(modEventBus);
        //  物品注册添加进事件线
        ITEMS.register(modEventBus);
        //
        CREATIVE_MODE_TABS.register(modEventBus);

//实体注册
        EntityRegistry.ENTITIES.register(modEventBus);
        //菜单注册添加进事件线
      MenuRegistry.MENUS.register(modEventBus);

        NetworkRegistry.registerPackets();

      //  modEventBus.post(new KeyBindsEvent())

        ATTRIBUTE.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);



        //IEventBus ABC = FMLJavaModLoadingContext.get().getModEventBus();
        //RegistryHandler.ITEMS.register(ABC);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        //modEventBus.addListener(this::clientSetup);

        //context.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC)
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

       // Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    private void clientSetup(final FMLClientSetupEvent event) {
//        //FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientLayerRegistry::onAddLayers);
//        event.enqueueWork(()->{
//            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
//
            //MenuScreens.register(newMenu.Customize.get(), CustomizeScreen::new);//将menu和screen绑定
//
//        });
   }




    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)//客户端初始化
        {//;//将menu和screen绑定 必须在此注册
            // Some client setup code
            event.enqueueWork(MenuRegistry::connect);//<---------将menu和screen绑定

            //
            //renderEvent.o();
            ClientSync.client();






            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());





        }
    }
}
