package com.integration_package_core.util.RegistryHandler;

import com.integration_package_core.IPC;
import com.integration_package_core.gui.CustomizeMenu;
import com.integration_package_core.gui.CustomizeScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class MenuRegistry {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, IPC.MODID);


    public static final RegistryObject<MenuType<CustomizeMenu>> Customize = MENUS.register("customize", () -> IForgeMenuType.create(CustomizeMenu::new));

    public static void connect() {

        MenuScreens.register(MenuRegistry.Customize.get(), CustomizeScreen::new);//将menu和screen绑定

    }

}

/// public static final   RegistryObject<MenuType<CustomizeMenu>> Customize= MENUS.register("customize", () -> new MenuType<>(CustomizeMenu::new,FeatureFlags.DEFAULT_FLAGS));

//  public static List<Map<MenuType<?>, MenuScreens.ScreenConstructor<?, ?>>> MenuScreenContrast;

//// public static final DeferredRegister< MenuType<CustomizeMenu>> Customize = register("customize", CustomizeMenu::new ,FeatureFlags.DEFAULT_FLAGS);


//   public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>>  register (
//           String name,
//           MenuType.MenuSupplier<?> pConstructor,
//           FeatureFlagSet pRequiredFeatures
//   ) {

//      // MenuType<? extends AbstractContainerMenu> menu =;

//       return EntityBar.MENUS.register(name, () -> new MenuType<>(pConstructor, pRequiredFeatures)); //注册新类型
//   }

//MenuScreens.register(newMenu.Customize, CustomizeScreen::new);//将menu和screen绑定
//public static final RegistryObject<RecipeType<WeaponfusionRecipe>> WEAPON_FUSION = RECIPE_TYPES.register("weapon_fusion", () -> registerRecipeType("weapon_fusion"));
// public static final RegistryObject<MenuType<WeaponfusionMenu>> aaa =Registry.register(BuiltInRegistries.MENU, "pKey",, FeatureFlags.VANILLA_SET))
// MenuScreens.register(Customize,CustomizeScreen.class::new );//将menu和screen绑定


// if (MenuScreenContrast != null) {
// }
