package instances.items.definitions

import instances.items.Items
import instances.Stat

fun Items.loadSwords() {

    sword(2, "SWORD_01") {
        baseAdd(Stat.ATK, 20)
        baseAdd(Stat.CRIT_DMG, 5)
    }

}