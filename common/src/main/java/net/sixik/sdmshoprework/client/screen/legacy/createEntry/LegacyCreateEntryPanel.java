package net.sixik.sdmshoprework.client.screen.legacy.createEntry;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import net.sixik.sdmshoprework.SDMShopClient;
import net.sixik.sdmshoprework.client.screen.basic.createEntry.AbstractCreateEntryPanel;

public class LegacyCreateEntryPanel extends AbstractCreateEntryPanel {
    public LegacyCreateEntryPanel(Panel panel) {
        super(panel);
    }

    @Override
    public void addWidgets() {

    }

    @Override
    public void alignWidgets() {

    }

    @Override
    public void drawBackground(PoseStack graphics, Theme theme, int x, int y, int w, int h) {
        GuiHelper.drawHollowRect(graphics, x,y,w,h, SDMShopClient.getTheme().getReact(), false);
    }
}
