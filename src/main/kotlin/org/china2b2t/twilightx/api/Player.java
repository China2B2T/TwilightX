package org.china2b2t.twilightx.api;

import org.china2b2t.twilightx.GLimitStorage;
import org.china2b2t.twilightx.MuteStorage;

public class Player {
    /**
     * Set soft-ban
     *
     * @param player
     * @param is
     */
    public static void setLimited(org.bukkit.entity.Player player, boolean is) {
        GLimitStorage.Companion.setLimited(player, is);
    }

    /**
     * Check if soft-banned
     *
     * @param player
     * @return
     */
    public static boolean isLimited(org.bukkit.entity.Player player) {
        return GLimitStorage.Companion.isLimited(player);
    }

    /**
     * Set whether the specified player is muted
     *
     * @param player
     * @param is
     */
    public static void setMuted(org.bukkit.entity.Player player, boolean is) {
        MuteStorage.Companion.setMuted(player, is);
    }

    /**
     * Check if muted
     *
     * @param player
     * @return
     */
    public static boolean isMuted(org.bukkit.entity.Player player) {
        return MuteStorage.Companion.isMuted(player);
    }
}
