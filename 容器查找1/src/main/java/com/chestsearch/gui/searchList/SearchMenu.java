package com.chestsearch.gui.searchList;

import com.chestsearch.init.newMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class SearchMenu extends AbstractContainerMenu {


  public   chestContainer container;


    //  @Override
    //  public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
    //      return null;
    //  }

    //  @Override
    //  public boolean stillValid(Player pPlayer) {
    //   return false;
    //  }


    public SearchMenu(int pContainerId) {

        this(pContainerId, new SimpleContainer(1), new SimpleContainerData(1));

    }

   public SearchMenu(int pContainerId, Container pLectern, ContainerData container) {
       super(newMenu.searchList.get(), pContainerId);

     //  this.container= container;

    //   System.out.println( this.container.get());

   }

    public SearchMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(newMenu.searchList.get(), i);



    }


    /**
     * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
     */
  // public boolean clickMenuButton(Player pPlayer, int pId) {
  //     return false;
  // }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    public void setData(int pId, int pData) {
        super.setData(pId, pData);
        this.broadcastChanges();
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}