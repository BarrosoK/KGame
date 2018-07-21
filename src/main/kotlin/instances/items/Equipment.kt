package instances.items

import enums.EquipmentType
import enums.ItemType
import instances.Bonus
import instances.Stat


open class Equipment(id: Int, name: String, val equipmentType: EquipmentType, val secondType: Any) : Item(id, name, ItemType.EQUIPMENT) {

    val bonuses = ArrayList<Bonus>()

    fun baseAdd(stat: Stat, amount: Int) {
        bonuses += Bonus(stat, amount)
    }
}

