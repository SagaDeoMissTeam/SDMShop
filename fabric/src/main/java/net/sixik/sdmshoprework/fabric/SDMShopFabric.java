package net.sixik.sdmshoprework.fabric;

import net.sixik.sdmshoprework.SDMShopRework;
import net.fabricmc.api.ModInitializer;

public class SDMShopFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SDMShopRework.init();
    }
}