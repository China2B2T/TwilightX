package org.china2b2t.twilightx

import org.bukkit.OfflinePlayer

class IgnoreStorage {
    companion object {
        var ignore: HashMap<OfflinePlayer, MutableList<OfflinePlayer>> = HashMap();

        fun ignore(from: OfflinePlayer, tag: OfflinePlayer) {
            val list = ignore.get(from)!!.toMutableList()
            if (list.isNullOrEmpty()) {
                val modified = mutableListOf(tag)
                ignore[from] = modified
            } else {
                list[list.size] = tag
                ignore[from] = list
            }

            return
        }

        fun unignore(from: OfflinePlayer, tag: OfflinePlayer) {
            val list = ignore.get(from)!!
            list.drop(list.indexOf(tag))

            ignore[from] = list
        }

        fun checkIgnored(from: OfflinePlayer, tag: OfflinePlayer): Boolean {
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