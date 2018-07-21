package instances.items.definitions

import instances.items.Items
import instances.Stat

fun Items.loadChests() {

    chest(0, "CHEST_01") {
        baseAdd(Stat.HP, 80)
        baseAdd(Stat.ATK, 2)
    }
    chest(1, "CHEST_02") {
        baseAdd(Stat.DEF, 130)
        baseAdd(Stat.HP, 100)
    }

}