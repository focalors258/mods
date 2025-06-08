package com.chestsearch.event.eventHandler;
import com.chestsearch.ChestSearch;
import com.chestsearch.gui.customize.CustomizeScreen;
import com.chestsearch.init.newMenu;
import com.chestsearch.tool.E;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.server.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = ChestSearch.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class damageEvent {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {

   //     MenuScreens.register(newMenu.Customize.get(), CustomizeScreen::new);//将menu和screen绑定
        //  MenuScreens.register(MenuHandle.STEW_POT_MENU.get(), StewPotScreen::new);
    }


    @SubscribeEvent
    public static void p(LivingDamageEvent e) {


        //  System.out.println("345346457");e.getSource().getEntity() instanceof Player player

        if (false) {


            //     player.openMenu(new SimpleMenuProvider((a, b, c) -> {
            //         return new SearchMenu(100);
            //     }, Component.literal("465")));

//buttonNetwork.send("666", (ServerPlayer) player);

//int x=player.getBlockX();
//
//int z=player.getBlockZ();
//
//            LevelChunk levelchunk0 = E.getChunk(player, x, z);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]), SectionPos.blockToSectionCoord(pos[2]));
//            LevelChunk levelchunk1 = E.getChunk(player, x - 16, z - 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]-16), SectionPos.blockToSectionCoord(pos[2]-16));
//            LevelChunk levelchunk2 = E.getChunk(player, x + 16, z + 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]+16), SectionPos.blockToSectionCoord(pos[2]+16));
//            LevelChunk levelchunk3 = E.getChunk(player, x - 16, z + 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]-16), SectionPos.blockToSectionCoord(pos[2]+16));
//            LevelChunk levelchunk4 = E.getChunk(player, x + 16, z - 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]+16), SectionPos.blockToSectionCoord(pos[2]-16));
//
//            Map<BlockPos, BlockEntity> list = new HashMap<>();
//
//            list.putAll(levelchunk0.getBlockEntities());
//            list.putAll(levelchunk1.getBlockEntities());
//            list.putAll(levelchunk2.getBlockEntities());
//            list.putAll(levelchunk3.getBlockEntities());
//            list.putAll(levelchunk4.getBlockEntities());
//
//
//
//
//            // buttonNetwork.send(new CompoundTag());
//            System.out.println(SectionPos.blockToSectionCoord(500));
//            System.out.println(list);


            if (e.getEntity() instanceof Creeper) {
                //   player.openMenu(new SimpleMenuProvider((i, playerInventory, p) -> a, Component.literal("456546")));
//CustomizeMenu a= new CustomizeMenu(100);
//   player.openMenu(new SimpleMenuProvider((i, playerInventory, p) -> a, Component.literal("456546")));
                //          int[] pos = Objects.requireNonNull(player).getPersistentData().getIntArray("chestPos");


////LevelChunk levelchunk = player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]), SectionPos.blockToSectionCoord(pos[2]));
                //          //
                //          //
                //          //
                //          //    SearchMenu a = new SearchMenu(100,new chestContainer(levelchunk.getBlockEntities()) );
                //          //  //  System.out.println(levelchunk.getBlockEntities());
                //          //
                //          //    player.openMenu(new SimpleMenuProvider((i, playerInventory, p) -> a, Component.literal("附近的箱子")));


                //          BlockPos b = new BlockPos(pos[0], pos[1], pos[2]);

                //          Block block = player.level().getBlockState(b).getBlock();

                //          MenuProvider menuprovider = block.getMenuProvider(player.level().getBlockState(b), player.level(), b);

                //          player.openMenu(menuprovider);


                //      }
                // Minecraft.getInstance().setScreen(new CustomizeScreen(new CustomizeMenu(100),player.getInventory(),Component.literal("456546")));

                //Component.translatable("ew");
                //  player.health_move=0;


            }
        }

    }


    @SubscribeEvent
    public static void g(EntityEvent e) {


//e.getEntity().getPersistentData().


    }

    @SubscribeEvent
    public static void c(MobSpawnEvent e) {


        //  e.getEntity().getPersistentData().


    }

    @SubscribeEvent
    public static void d(ServerStartedEvent e) {


        //  e.getEntity().getPersistentData().

        e.getServer().getAllLevels().forEach(level -> {
            if (level == null) return;

            level.getEntities().getAll().forEach(entity -> {
                if (entity == null) return;
                if (entity instanceof LivingEntity livingEntity) {

                    // livingEntity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("34546"),"56436",100.0,AttributeModifier.Operation.ADDITION));//长久保存

                }

            });

        });


    }

    @SubscribeEvent
    public static void d(ServerStoppingEvent e) {


        //  e.getEntity().getPersistentData().


        e.getServer().getAllLevels().forEach(level -> {
            if (level == null) return;

            level.getEntities().getAll().forEach(entity -> {

                if (entity == null) return;
                if (entity instanceof LivingEntity livingEntity) {

                    entity.getPersistentData().putInt("345345", 675);
             //       System.out.println(entity);
                    //System.out.println(entity.getPersistentData().getInt("345345"));

                    //  livingEntity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("3-4-5-4-6"), "56436", 100.0, AttributeModifier.Operation.ADDITION));//长久保存
                    //livingEntity.getPersistentData()



                }


            });

        });


    }


}
