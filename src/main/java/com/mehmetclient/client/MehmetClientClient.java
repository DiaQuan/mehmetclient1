package com.mehmetclient.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class MehmetClientClient implements ClientModInitializer {
    private static KeyMapping openMenuKey;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath("mehmetclient", "main"));
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.mehmetclient.open_menu",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_RIGHT_SHIFT,
                category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.consumeClick()) {
                if (client.screen == null) client.setScreen(new MehmetClientScreen());
                else if (client.screen instanceof MehmetClientScreen) client.setScreen(null);
            }
        });

        HudElementRegistry.addLast(
                Identifier.fromNamespaceAndPath("mehmetclient", "client_hud"),
                (graphics, deltaTracker) -> {
                    if (!ClientModules.isEnabled("HUD")) return;
                    var client = net.minecraft.client.Minecraft.getInstance();
                    if (client.player == null || client.options.hideGui) return;

                    int x = 8;
                    int y = 8;
                    graphics.fill(x - 4, y - 4, x + 154, y + 43, 0xB8101020);
                    graphics.fill(x - 4, y - 4, x + 154, y - 2, 0xFF9B5CFF);
                    graphics.text(client.font, Component.literal("MEHMET CLIENT"), x, y, 0xFFD9B8FF, false);
                    graphics.text(client.font, Component.literal("XYZ: " +
                            (int) client.player.getX() + " / " + (int) client.player.getY() + " / " +
                            (int) client.player.getZ()), x, y + 13, 0xFFFFFFFF, false);
                    graphics.text(client.font, Component.literal("Modules: " + ClientModules.enabledCount()),
                            x, y + 26, 0xFFB8B8C8, false);
                });
    }
}
