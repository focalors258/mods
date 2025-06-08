package com.chestsearch.event.eventHandler;

import com.chestsearch.ChestSearch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChestSearch.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class clickEvent {






    @SubscribeEvent
    public static void p(PlayerInteractEvent.RightClickBlock e) {//双端同步



        Player p = e.getEntity();

        BlockState blockState = e.getLevel().getBlockState(e.getPos());

        BlockEntity blockEntity = e.getLevel().getBlockEntity(e.getPos());


   //     System.out.println( buf.writeNbt(new CompoundTag()););
        if (blockState.getBlock() instanceof BaseEntityBlock chest) {


            e.getEntity().getPersistentData().putIntArray("chestPos", new int[]{e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()});

        //    System.out.println(Arrays.toString(e.getEntity().getPersistentData().getIntArray("chestPos")));

        }
    }
}
        //       //  if (blockEntity != null) {
        //       //      System.out.println(blockEntity.getPersistentData().get());
        //       //  }
        //       ListTag oldTag = (ListTag) p.getPersistentData().get("chestSearchCache");
        //       if (blockEntity != null) {
//
        //           BlockPos pos = blockEntity.getBlockPos();
//
        //           if (oldTag == null) {
        //               ListTag a = new ListTag();
//
        //               a.add(new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()}));
//
        //               p.getPersistentData().put("chestSearchCache", a);
//
        //           } else {
//
        //               oldTag.add(new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()}));
//
        //               //  p.getPersistentData().put("chestSearchCache", new ListTag().set(0, new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()})));
        //           }
//
        //           System.out.println(oldTag);
//
        //       }
//
//
        //   }

