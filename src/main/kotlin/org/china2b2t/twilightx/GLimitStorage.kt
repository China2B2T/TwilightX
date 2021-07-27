package org.china2b2t.twilightx

import org.bukkit.entity.Player

class GLimitStorage {
    companion object{
        fun setLimited(player: Player, limited: Boolean) {
            var uuid = player.uniqueId;
            TwilightX.data?.set("$uuid.limited", limited)
        }

        fun isLimited(player: Player): Boolean {
            var uuid = player.uniqueId
            if(!TwilightX.data?.isSet("$uuid.limited")) {
                return false
            }
            if(TwilightX.data?.getBoolean("$uuid.limited")) {
                return true
            }
            return false
        }
    }
}

fun Player.isLimited() : Boolean {
    return GLimitStorage.isLimited(this)
}

fun Player.setLimited(expression: Boolean) {
    GLimitStorage.setLimited(this, expression)
}