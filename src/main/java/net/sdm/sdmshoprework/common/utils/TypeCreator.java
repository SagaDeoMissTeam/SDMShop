package net.sdm.sdmshoprework.common.utils;

import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModList;
import net.sdm.sdmshoprework.SDMShopClient;
import net.sdm.sdmshoprework.SDMShopRework;
import net.sdm.sdmshoprework.api.IConstructor;
import net.sdm.sdmshoprework.api.register.ShopContentRegister;
import net.sdm.sdmshoprework.api.shop.AbstractShopEntryType;
import net.sdm.sdmshoprework.client.screen.basic.AbstractShopScreen;
import net.sdm.sdmshoprework.client.screen.legacy.createEntry.LegacyCreateEntryScreen;
import net.sdm.sdmshoprework.common.shop.ShopEntry;
import net.sdm.sdmshoprework.network.server.SendChangesShopEntriesC2S;
import net.sdm.sdmshoprework.network.server.create.SendCreateShopEntryC2S;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeCreator {

    public static List<ContextMenuItem> createContext(AbstractShopScreen screen){
        List<ContextMenuItem> contextMenu = new ArrayList<>();

        for (Map.Entry<String, IConstructor<AbstractShopEntryType>> d1 : ShopContentRegister.SHOP_ENTRY_TYPES.entrySet()) {
            AbstractShopEntryType shopEntryType =  d1.getValue().createDefaultInstance();
            if(ModList.get().isLoaded(shopEntryType.getModId()) && SDMShopClient.creator.favoriteCreator.contains(d1.getKey())) {
                contextMenu.add(new ContextMenuItem(shopEntryType.getTranslatableForCreativeMenu(), shopEntryType.getCreativeIcon(), (b) -> {
                    ShopEntry entry = new ShopEntry(screen.selectedTab);
                    entry.setEntryType(shopEntryType);
                    screen.selectedTab.getTabEntry().add(entry);
                    screen.refreshWidgets();

                    new SendCreateShopEntryC2S(screen.selectedTab.shopTabUUID, entry.serializeNBT()).sendToServer();
                }));
            }
        }

        contextMenu.add(new ContextMenuItem(Component.translatable("sdm.shop.entry.creator.contextmenu.info"), Icons.BOOK, (b) -> {
            new LegacyCreateEntryScreen(screen).openGui();
        }));
        return contextMenu;
    }
}
