package com.chestsearch.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class E {

    public static Attribute getAttribute(String modId, String registryName) {

        ResourceLocation resourceLocation = new ResourceLocation(modId, registryName);
        // 从 Forge 的属性注册表中获取属性
        return ForgeRegistries.ATTRIBUTES.getValue(resourceLocation);
    }

    public static BlockState getTargetBlock(Player p) {

      // Vec3 start = player.getEyePosition();
      // Vec3 direction = player.getViewVector(1.0F);
      // Vec3 end = start.add(direction.x * distance, direction.y * distance, direction.z * distance);

      // ClipContext context = new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
      // BlockHitResult result = player.level().clip(context);

      // return result.getBlockPos();


    double l = p.getAttributes().getValue(E.getAttribute("forge", "block_reach"));


    BlockPos pos=p.level().clip(new ClipContext(p.position(), p.position().add(M.getVec(p).scale(l)), ClipContext.Block.FALLDAMAGE_RESETTING, ClipContext.Fluid.NONE, p)).getBlockPos();
   // System.out.println(pos);

    return p.level().getBlockState(pos);

    }

    public static LevelChunk getChunk(Player player,int x,int z){

      return player.level().getChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));


    }

    public static BlockPos getPos(int[] pos){

        return new BlockPos(pos[0],pos[1],pos[2]);


    }


    public static String   getChestName(CompoundTag tag) {


        if (tag.contains("CustomName")) {

            String CustomName = tag.get("CustomName").getAsString();


            Pattern pattern = Pattern.compile("\"text\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(CustomName);
            if (matcher.find()) {
            return   matcher.group(1);
            }


        }

        return   "0";

    }

    public static ItemStack getItemStack (String registry_name){

        Pattern pattern = Pattern.compile("^([^:]+):(.*)$");
        Matcher matcher = pattern.matcher(registry_name);
        if (matcher.find()) {
            String namespace = matcher.group(1);
            String item_name = matcher.group(2);

            ResourceLocation registryName = new ResourceLocation(namespace,item_name);
            // 从 Forge 物品注册表中获取 Item 对象
            Item item = ForgeRegistries.ITEMS.getValue(registryName);

            if (item != null) {
                return new ItemStack(item);
            }


        } else {
            System.out.println("输入的注册名格式不符合要求");
        }

        return null;

    }



}
