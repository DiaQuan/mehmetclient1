package com.mehmetclient.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Central module registry used by the menu and HUD. */
public final class ClientModules {
    private ClientModules() {}

    public static final List<String> CATEGORIES = List.of("General", "BaseFinding", "Render");
    private static final Map<String, List<String>> MODULES = new LinkedHashMap<>();
    private static final Map<String, Boolean> ENABLED = new LinkedHashMap<>();

    static {
        MODULES.put("General", List.of(
                "Anti Trap", "Auto Sell", "Auto Spawner Sell", "Chunk Finder", "Fake Pay",
                "Fake Stats", "Item Dropper", "Netherite Finder", "Player Chunks", "Spawner Protect"));
        MODULES.put("BaseFinding", List.of(
                "Block Entity Debug", "Hole ESP", "Light Finder", "Prime Chunk Finder", "RTP Base Finder",
                "Sus Chunk Finder", "Suspicious ESP", "Tunnel Base Finder", "Seed Chunk Finder"));
        MODULES.put("Render", List.of(
                "Block ESP", "Block Notifier", "Free Look", "Fullbright", "HUD", "Jump Circles",
                "Mob ESP", "Name Tags", "Ore Sim", "Pearl Trajectory", "Player ESP", "RealHitBox",
                "Music HUD", "Storage ESP", "SwingSpeed"));
        MODULES.values().forEach(list -> list.forEach(name -> ENABLED.put(name, false)));
        ENABLED.put("HUD", true);
    }

    public static List<String> modules(String category) {
        return MODULES.getOrDefault(category, List.of());
    }

    public static boolean isEnabled(String module) {
        return ENABLED.getOrDefault(module, false);
    }

    public static void toggle(String module) {
        if (ENABLED.containsKey(module)) ENABLED.put(module, !ENABLED.get(module));
    }

    public static int enabledCount() {
        return (int) ENABLED.values().stream().filter(Boolean::booleanValue).count();
    }

    public static boolean hasBehavior(String module) {
        // This build implements the in-game menu and the HUD module. Other entries are
        // registered in the UI but their gameplay behavior is intentionally not wired yet.
        return "HUD".equals(module);
    }
}
