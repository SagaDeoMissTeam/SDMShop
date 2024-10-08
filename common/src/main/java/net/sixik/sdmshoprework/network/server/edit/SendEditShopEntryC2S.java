package net.sixik.sdmshoprework.network.server.edit;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.sixik.sdmshoprework.SDMShopRework;
import net.sixik.sdmshoprework.common.shop.ShopBase;
import net.sixik.sdmshoprework.network.ShopNetwork;
import net.sixik.sdmshoprework.network.client.SyncShopS2C;

import java.util.UUID;

public class SendEditShopEntryC2S extends BaseC2SMessage {

    private final UUID entryID;
    private final UUID tabID;
    private final CompoundTag nbt;

    public SendEditShopEntryC2S(UUID tabID, UUID entryID, CompoundTag nbt) {
        this.entryID = entryID;
        this.tabID = tabID;
        this.nbt = nbt;
    }

    public SendEditShopEntryC2S(FriendlyByteBuf buf) {
        this.tabID = buf.readUUID();
        this.entryID = buf.readUUID();
        this.nbt = buf.readAnySizeNbt();
    }

    @Override
    public MessageType getType() {
        return ShopNetwork.SEND_EDIT_ENTRY;
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(tabID);
        friendlyByteBuf.writeUUID(entryID);
        friendlyByteBuf.writeNbt(nbt);
    }



    @Override
    public void handle(NetworkManager.PacketContext packetContext) {
        try {
            ShopBase.SERVER.getShopTab(tabID).getShopEntry(entryID).deserializeNBT(nbt);
            new SyncShopS2C(ShopBase.SERVER.serializeNBT()).sendToAll(packetContext.getPlayer().getServer());
            ShopBase.SERVER.saveShopToFile();
        } catch (Exception e){
            SDMShopRework.printStackTrace("", e);
        }
    }
}
