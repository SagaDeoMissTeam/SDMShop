package net.sdm.sdmshopr.shop.entry.type;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.sdm.sdmshopr.shop.entry.ShopEntry;

public interface IEntryType extends INBTSerializable<CompoundTag> {

    boolean isSellable();
    boolean isCountable();

    default void execute(){}

    Icon getIcon();

    CompoundTag getIconNBT();

    void getConfig(ConfigGroup group);

    Icon getCreativeIcon();
    default Icon getPreviewIcon(){
        if(getCreativeIcon().isEmpty()) return Icons.BARRIER;
        else return getCreativeIcon();
    }

    default void buy(ServerPlayer player, int countBuy, ShopEntry<?> entry){}
    default void sell(ServerPlayer player, int countSell, ShopEntry<?> entry) {}
}
