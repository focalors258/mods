package com.integration_package_core.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().food(new  FoodProperties.Builder().nutrition(35).build()));


       // EntityBar.ITEMS.register();

       // EntityBar.ITEMS.register();

    }




    //EntityBar.ITEMS//register("fttft", ItemBase::new);







}
