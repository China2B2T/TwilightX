package org.china2b2t.twilightx

import org.bukkit.entity.Player

class MuteStorage {
    companion object{
        fun setMuted(player: Player, muted: Boolean) {
            var uuid = player.uniqueId;
            TwilightX.data?.set("$uuid.muted", muted)
        }

        fun isMuted(player: Player): Boolean {
            var uuid = player.uniqueId
            if(TwilightX.data?.isSet("$uuid.muted") == false) {
                return false
            }
            if(TwilightX.data?.getBoolean("$uuid.muted") == true) {
                return true
            }
            return false
        }
    }
}