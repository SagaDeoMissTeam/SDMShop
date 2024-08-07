package net.sdm.sdmshopr.client;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import net.minecraft.client.gui.GuiGraphics;
import net.sdm.sdmshopr.SDMShopR;
import net.sdm.sdmshopr.SDMShopRClient;
import net.sdm.sdmshopr.shop.Shop;

public class TabsPanel extends Panel {
    public TabsPanel(Panel panel, int w, int h) {
        super(panel);
        setSize(w,h);
    }

    @Override
    public void addWidgets() {
        int y = 2;
        for (int i = 0; i < Shop.CLIENT.shopTabs.size(); i++) {
            if(!Shop.CLIENT.shopTabs.get(i).isLocked()) {
                TabButton tab = new TabButton(this, Shop.CLIENT.shopTabs.get(i));
                tab.setSize(this.width - 4, 14);
                if (i > 0) {
                    y += 14 + 6;
                    tab.setPos(2, y);
                } else {
                    tab.setPos(2, y);
                }
                add(tab);
            }
        }

        if(SDMShopR.isEditModeClient()){
            CreateTabButton tab = new CreateTabButton(this);
            tab.setSize(this.width - 4, 14);

            if(Shop.CLIENT.shopTabs.isEmpty()) {
                tab.setPos(2, y);
            } else {
                y += 14 + 6;
                tab.setPos(2, y);
            }
            add(tab);
        }
    }

    @Override
    public void alignWidgets() {

    }

    @Override
    public void drawBackground(GuiGraphics matrixStack, Theme theme, int x, int y, int w, int h) {

        Color4I.RED.draw(matrixStack, x + 1, y + 1, w - 2, h - 2);
        GuiHelper.drawHollowRect(matrixStack, x, y, w, h, Color4I.BLUE, false);
//        GuiHelper.drawHollowRect(matrixStack, x - 1, y - 1, w + 2, h + 5, SDMShopRClient.shopTheme.getStoke(), false);
    }
}
