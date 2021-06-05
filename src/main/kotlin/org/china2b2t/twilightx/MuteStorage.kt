package org.china2b2t.twilightx

import org.bukkit.entity.Player

class MuteStorage {
    companion object{
        var muted: Array<Player>? = arrayOf()

        fun setMuted(player: Player, mute: Boolean) {
            if (!mute) {
                if (muted!!.contains(player)) {
                    //
                }
            }
            if (muted!!.contains(player)) {
                return
            }

            muted?.set(muted!!.size, player)
        }

        fun isMuted(player: Player): Boolean {
            if (muted.contains(player)) {
                return true
            }
            return false
        }
    }
}