package instances.items

import enums.ItemType
import enums.PotionType
import instances.character.Bonus
import instances.character.Stat


open class Potion(id: Int, name: String, val equipmentType: PotionType) : Item(id, name, ItemType.POTION) {

    val bonuses = ArrayList<Bonus>()

    fun bonusAdd(stat: Stat, amount: Int) {
        bonuses += Bonus(stat, amount)
    }
}

