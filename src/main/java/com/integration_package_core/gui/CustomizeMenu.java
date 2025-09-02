package com.integration_package_core.gui;

import com.integration_package_core.util.RegistryHandler.MenuRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;


public class CustomizeMenu extends AbstractContainerMenu {


    public CustomizeMenu(int pContainerId) {
        this(pContainerId, new SimpleContainer(1));
    }


    public CustomizeMenu(int pContainerId, Container pLectern) {
        //   Function<Integer, customizeMenu> aNew = customizeMenu::new;
        //Function<Integer, customizeMenu> aNew = customizeMenu::new;
        //Maps.EntryTransformer<Integer, Inventory, AnvilMenu> integerInventoryAnvilMenuEntryTransformer = AnvilMenu::new;
        super(MenuRegistry.Customize.get(), pContainerId);

        CompoundTag a=   new   CompoundTag();

        a.putString("id", "minecraft:stone");

        ItemContainer b=   new ItemContainer(  ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "stone")).getDefaultInstance());



     //   System.out.println(b);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(b, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(b, k, 8 + k * 18, 142));
        }

    }
       // addSlots(new Slot(new ItemContainer(   ItemStack.of(a)), 0,500,300 ));


    public CustomizeMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i,inventory);
    }


    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }


    public boolean stillValid(Player pPlayer) {
        return true;
    }//


    public  Slot addSlots(Slot pSlot) {

        return this.addSlot(pSlot);

    }


}
// private static <T extends AbstractContainerMenu> MenuType<T> register(String pKey, MenuType.MenuSupplier<T> pFactory) {
//     return Registry.register(BuiltInRegistries.MENU, pKey, new MenuType<>(pFactory, FeatureFlags.VANILLA_SET));
// }

//  public customizeMenu( int pContainerId,Inventory pPlayerInventory) {
//
//      this(pContainerId);
//  }
//

