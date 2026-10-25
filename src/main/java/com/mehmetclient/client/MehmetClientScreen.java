package com.mehmetclient.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public final class MehmetClientScreen extends Screen {
    private String selectedCategory = "Render";
    private static final int PURPLE = 0xFF9B5CFF;
    private static final int PANEL = 0xF0121222;
    private static final int ROW = 0xFF202035;
    private static final int ROW_HOVER = 0xFF302449;
    private static final int TEXT = 0xFFF1EDFF;
    private static final int MUTED = 0xFFAAA5BC;
    private int panelX, panelY, panelW, panelH;

    public MehmetClientScreen() {
        super(Component.literal("Mehmet Client"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, width, height, 0x88050012);
        panelW = Math.min(560, width - 24);
        panelH = Math.min(390, height - 24);
        panelX = (width - panelW) / 2;
        panelY = (height - panelH) / 2;

        graphics.fill(panelX, panelY, panelX + panelW, panelY + panelH, PANEL);
        graphics.fill(panelX, panelY, panelX + panelW, panelY + 3, PURPLE);
        graphics.drawString(font, Component.literal("MEHMET CLIENT"), panelX + 18, panelY + 15, TEXT);
        graphics.drawString(font, Component.literal("1.21.11  |  Fabric  |  v0.2.0"), panelX + 18, panelY + 30, MUTED);
        graphics.drawString(font, Component.literal("RIGHT SHIFT  •  CLOSE WITH ESC"), panelX + panelW - 190,
                panelY + 18, MUTED);

        int tabY = panelY + 53;
        int tabX = panelX + 16;
        for (String category : ClientModules.CATEGORIES) {
            int tabW = category.equals("BaseFinding") ? 112 : 92;
            boolean active = category.equals(selectedCategory);
            graphics.fill(tabX, tabY, tabX + tabW, tabY + 24, active ? 0xFF39245A : ROW);
            if (active) graphics.fill(tabX, tabY + 21, tabX + tabW, tabY + 24, PURPLE);
            graphics.drawCenteredString(font, Component.literal(category), tabX + tabW / 2, tabY + 8, active ? TEXT : MUTED);
            tabX += tabW + 7;
        }

        int listX = panelX + 16;
        int listY = tabY + 36;
        int rowW = panelW - 32;
        int rowH = 22;
        List<String> modules = ClientModules.modules(selectedCategory);
        int visible = Math.min(modules.size(), Math.max(1, (panelY + panelH - listY - 49) / rowH));
        for (int i = 0; i < visible; i++) {
            String module = modules.get(i);
            int y = listY + i * rowH;
            boolean hover = mouseX >= listX && mouseX <= listX + rowW && mouseY >= y && mouseY <= y + rowH - 2;
            boolean enabled = ClientModules.isEnabled(module);
            graphics.fill(listX, y, listX + rowW, y + rowH - 2, hover ? ROW_HOVER : ROW);
            graphics.drawString(font, Component.literal(module), listX + 9, y + 6, enabled ? TEXT : MUTED);
            String status = enabled ? "ON" : "OFF";
            int statusColor = enabled ? 0xFFB78BFF : 0xFF777386;
            graphics.drawString(font, Component.literal(status), listX + rowW - 34, y + 6, statusColor);
        }

        int footerY = panelY + panelH - 30;
        graphics.fill(panelX + 16, footerY - 7, panelX + panelW - 16, footerY - 6, 0xFF353047);
        graphics.drawString(font, Component.literal("Enabled: " + ClientModules.enabledCount()), panelX + 18, footerY, MUTED);
        graphics.drawString(font, Component.literal("Click modules to toggle"), panelX + panelW - 158, footerY, MUTED);
        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        int tabY = panelY + 53;
        int tabX = panelX + 16;
        for (String category : ClientModules.CATEGORIES) {
            int tabW = category.equals("BaseFinding") ? 112 : 92;
            if (mouseX >= tabX && mouseX <= tabX + tabW && mouseY >= tabY && mouseY <= tabY + 24) {
                selectedCategory = category;
                return true;
            }
            tabX += tabW + 7;
        }

        int listX = panelX + 16;
        int listY = tabY + 36;
        int rowW = panelW - 32;
        int rowH = 22;
        List<String> modules = ClientModules.modules(selectedCategory);
        int visible = Math.min(modules.size(), Math.max(1, (panelY + panelH - listY - 49) / rowH));
        for (int i = 0; i < visible; i++) {
            int y = listY + i * rowH;
            if (mouseX >= listX && mouseX <= listX + rowW && mouseY >= y && mouseY <= y + rowH - 2) {
                ClientModules.toggle(modules.get(i));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
