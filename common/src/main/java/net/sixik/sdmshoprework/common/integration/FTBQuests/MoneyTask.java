package net.sixik.sdmshoprework.common.integration.FTBQuests;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.ISingleLongValueTask;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.sixik.sdmshoprework.SDMShopR;
import net.sixik.sdmshoprework.SDMShopRework;

public class MoneyTask extends Task implements ISingleLongValueTask {
    public static TaskType TYPE;

    public long value = 1L;

    public MoneyTask(long id, Quest quest) {
        super(id, quest);
    }


    @Override
    public TaskType getType() {
        return TYPE;
    }

    @Override
    public long getMaxProgress() {
        return value;
    }

    @Override
    public String formatMaxProgress() {
        return SDMShopRework.moneyString(value);
    }

    @Override
    public String formatProgress(TeamData teamData, long progress) {
        return SDMShopRework.moneyString(progress);
    }

    @Override
    public void writeData(CompoundTag nbt) {
        super.writeData(nbt);
        nbt.putLong("value", value);
    }

    @Override
    public void readData(CompoundTag nbt) {
        super.readData(nbt);
        value = nbt.getLong("value");
    }

    @Override
    public void writeNetData(FriendlyByteBuf buf) {
        super.writeNetData(buf);
        buf.writeVarLong(value);
    }

    @Override
    public void readNetData(FriendlyByteBuf buf) {
        super.readNetData(buf);
        value = buf.readVarLong();
    }

    @Override
    public void setValue(long v) {
        value = v;
    }

    @Override
    public void fillConfigGroup(ConfigGroup config) {
        super.fillConfigGroup(config);
        config.addLong("value", value, v -> value = v, 1L, 1L, Long.MAX_VALUE).setNameKey("ftbquests.task.sdmshop");
    }

    @Override
    public Component getAltTitle() {
        return Component.literal(SDMShopRework.moneyString(value));
    }

    @Override
    public boolean consumesResources() {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void addMouseOverText(TooltipList list, TeamData teamData) {
        super.addMouseOverText(list, teamData);
        list.add(Component.translatable("sdmshop.balance").append(": ").append(Component.literal(SDMShopRework.moneyString(SDMShopR.getMoney(net.minecraft.client.Minecraft.getInstance().player)))).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
        long money = SDMShopR.getMoney(player);
        long add = Math.min(money, value - teamData.getProgress(this));

        if (add > 0L) {
            SDMShopR.setMoney(player, money - add);
            teamData.addProgress(this, add);
        }
    }
}
