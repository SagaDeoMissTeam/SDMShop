package net.sdm.sdmshopr.shop;

import dev.ftb.mods.ftblibrary.snbt.SNBT;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.sdm.sdmshopr.SDMShopR;
import net.sdm.sdmshopr.network.mainshop.SyncShop;
import net.sdm.sdmshopr.shop.entry.ShopEntry;
import net.sdm.sdmshopr.shop.tab.ShopTab;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.sdm.sdmshopr.SDMShopR.getFile;

public class Shop implements INBTSerializable<CompoundTag> {
    public static Shop CLIENT;
    public static Shop SERVER;
    public boolean needSave = false;

    public List<ShopTab> shopTabs = new ArrayList<>();

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("sdmversion", "0.0.1");

        ListTag tabs = new ListTag();

        for (ShopTab shopTab : shopTabs) {
            tabs.add(shopTab.serializeNBT());
        }

        nbt.put("tabs", tabs);

        return nbt;
    }

    @Nullable
    public ShopEntry<?> getEntryByUUID(UUID uuid){
        for (ShopTab shopTab : shopTabs) {
            for (ShopEntry<?> shopEntry : shopTab.shopEntryList) {
                if(shopEntry.entryID.equals(uuid)) return shopEntry;
            }
        }
        return null;
    }

    @Nullable
    public ShopEntry<?> getEntryByUUID(UUID uuid, ShopTab tab){
        for (ShopEntry<?> shopEntry : tab.shopEntryList) {
            if(shopEntry.entryID.equals(uuid)) return shopEntry;
        }
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        shopTabs.clear();

        if(nbt.contains("tabs")) {
            ListTag tabs = (ListTag) nbt.get("tabs");
            for (Tag tab : tabs) {
                ShopTab d1 = new ShopTab(this);
                d1.deserializeNBT((CompoundTag) tab);
                shopTabs.add(d1);
            }
        }
    }

    public void needSave(){
        needSave = true;
    }

    public void saveAndSync(){
        needSave();
        trySync(ServerLifecycleHooks.getCurrentServer());
    }
    public void saveToFileWithSync(){
        SNBT.write(getFile(), Shop.SERVER.serializeNBT());
        trySync(ServerLifecycleHooks.getCurrentServer());
    }

    public void saveTabs(CompoundTag nbt, ShopTab tab){
        Shop.SERVER.shopTabs.get(tab.getIndex()).deserializeNBT(nbt);
        saveToFileWithSync();
    }

    public void saveToFile(){
        SNBT.write(SDMShopR.getFile(), serializeNBT());
    }
    public void trySync(MinecraftServer server){
        new SyncShop(serializeNBT()).sendToAll(server);
    }


}
