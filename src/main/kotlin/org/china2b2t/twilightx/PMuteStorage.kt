package org.china2b2t.twilightx

import org.bukkit.entity.Player

class PMuteStorage {
    companion object{
        fun setMuted(player: Player, muted: Boolean) {
            var uuid = player.uniqueId;
            TwilightX.data?.set("$uuid.pmuted", muted)
        }

        fun isMuted(player: Player): Boolean {
            var uuid = player.uniqueId
            if(TwilightX.data?.isSet("$uuid.pmuted") == false) {
                return false
            }
            if(TwilightX.data?.getBoolean("$uuid.pmuted") == true) {
                return true
            }
            return false
        }
    }
}