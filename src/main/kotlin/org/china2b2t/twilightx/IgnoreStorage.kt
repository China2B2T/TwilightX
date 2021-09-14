package org.china2b2t.twilightx

import org.bukkit.OfflinePlayer

class IgnoreStorage {
    companion object {
        var ignore: HashMap<OfflinePlayer, MutableList<OfflinePlayer>> = HashMap()

        fun ignore(from: OfflinePlayer, tag: OfflinePlayer) {
            if (!ignore.containsKey(from)) {
                val modified = mutableListOf(tag)
                ignore[from] = modified

                return
            }

            val list = ignore.get(from)!!.toMutableList()
            if (list.isNullOrEmpty()) {
                val modified = mutableListOf(tag)
                ignore[from] = modified
            } else {
                list.plus(tag)
                ignore[from] = list
            }

            return
        }

        fun unignore(from: OfflinePlayer, tag: OfflinePlayer) {
            if (!ignore.containsKey(from)) {
                return
            }

            val list = ignore.get(from)!!

            if (!list.contains(tag)) {
                return
            }

            list.remove(tag)

            ignore[from] = list
        }

        fun checkIgnored(from: OfflinePlayer, tag: OfflinePlayer): Boolean {
            if (!ignore.containsKey(from)) {
                return false
            }

            val list = ignore.get(from)!!
            return if(list.isNullOrEmpty()) {
                false
            } else list.contains(tag)
        }
    }
}

fun OfflinePlayer.ignore(player: OfflinePlayer) {
    IgnoreStorage.ignore(this, player)
}

fun OfflinePlayer.unignore(player: OfflinePlayer) {
    IgnoreStorage.unignore(this ,player)
}

fun OfflinePlayer.ignored(player: OfflinePlayer): Boolean {
    return IgnoreStorage.checkIgnored(this, player)
}