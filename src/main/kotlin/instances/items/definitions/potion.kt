package instances.items.definitions

import enums.PotionType
import instances.items.Items
import instances.Stat

fun Items.loadPotions() {

        potion(4, "POTION_HEALTH_01", PotionType.HEALTH) {
            bonusAdd(Stat.HP, 10)
        }
}