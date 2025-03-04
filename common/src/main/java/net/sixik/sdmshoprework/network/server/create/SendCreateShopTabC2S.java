package net.sixik.sdmshoprework.network.server.create;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.sixik.sdmshoprework.SDMShopRework;
import net.sixik.sdmshoprework.api.SDMSerializeParam;
import net.sixik.sdmshoprework.common.shop.ShopBase;
import net.sixik.sdmshoprework.network.ShopNetwork;

public class SendCreateShopTabC2S extends BaseC2SMessage {

    private final CompoundTag nbt;

    public SendCreateShopTabC2S(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public SendCreateShopTabC2S(FriendlyByteBuf nbt) {
        this.nbt = nbt.readAnySizeNbt();
    }

    @Override
    public MessageType getType() {
        return ShopNetwork.CREATE_SHOP_TAB;
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeNbt(nbt);
    }

    @Override
    public void handle(NetworkManager.PacketContext packetContext) {
        try {
            ShopBase.SERVER.createShopTab(nbt, SDMSerializeParam.SERIALIZE_ALL);
            ShopBase.SERVER.syncShop(packetContext.getPlayer().getServer());
            ShopBase.SERVER.saveShopToFile();
        } catch (Exception e){
            SDMShopRework.printStackTrace("", e);
        }
    }
}
