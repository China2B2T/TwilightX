package org.china2b2t.twilightx.api;

import org.china2b2t.twilightx.GLimitStorage;

public class Player {
    public static void setLimited(org.bukkit.entity.Player player, boolean is) {
        GLimitStorage.Companion.setLimited(player, is);
    }

    public static boolean isLimited(org.bukkit.entity.Player player) {
        return GLimitStorage.Companion.isLimited(player);
    }
}
