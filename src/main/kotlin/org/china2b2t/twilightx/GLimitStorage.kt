package org.china2b2t.twilightx

import org.bukkit.entity.Player

class GLimitStorage {
    companion object{
        fun setLimited(player: Player, muted: Boolean) {
            var uuid = player.uniqueId;
            TwilightX.data?.set("$uuid.limited", muted)
        }

        fun isLimited(player: Player): Boolean {
            var uuid = player.uniqueId
            if(TwilightX.data?.isSet("$uuid.limited") == false) {
                return false
            }
            if(TwilightX.data?.getBoolean("$uuid.limited") == true) {
                return true
            }
            return false
        }
    }
}