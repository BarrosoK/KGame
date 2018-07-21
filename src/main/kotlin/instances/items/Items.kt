package instances.items

import enums.*
import instances.items.definitions.loadArmors
import instances.items.definitions.loadPotions
import instances.items.definitions.loadWeapons

object Items {

    val items = ArrayList<Item>()

    val equipments
        get() = items.filter { it.itemType == ItemType.EQUIPMENT } as ArrayList<Equipment>

    val potions
        get() = items.filter { it.itemType == ItemType.POTION } as ArrayList<Potion>

    val weapons
        get() = equipments.filter { it.itemType == EquipmentType.ARMOR }
    val armors
        get() = equipments.filter { it.itemType == EquipmentType.ARMOR }

    val chests
        get() = equipments.filter { it.secondType == ArmorType.CHEST }
    val swords
        get() = equipments.filter { it.secondType == WeaponType.SWORD }
    val blunts
        get() = equipments.filter { it.secondType == WeaponType.BLUNT }

    fun init() {
        loadArmors()
        loadWeapons()
        loadPotions()
    }

    fun item(id: Int, name: String, type: ItemType) {
        val item = Item(id, name, type)
        items.add(item)
    }

    fun potion(id: Int, name: String, type: PotionType, ops: Potion.() -> Unit = {}) {
        val potion = Potion(id, name, type)
        potion.ops()
        items.add(potion)
    }

    fun equipment(id: Int, name: String, itemType: EquipmentType, type: Any, ops: Equipment.() -> Unit = {}) {
        val equipment = Equipment(id, name, itemType, type)
        equipment.ops()
        items.add(equipment)
    }

    fun chest(id: Int, name: String, ops: Equipment.() -> Unit = {}) {
        equipment(id, name, EquipmentType.ARMOR, ArmorType.CHEST, ops)
    }

    fun sword(id: Int, name: String, ops : Equipment.() -> Unit = {}) {
        equipment(id, name, EquipmentType.WEAPON, WeaponType.SWORD, ops)
    }

    fun blunt(id: Int, name: String, ops: Equipment.() -> Unit = {}) {
        equipment(id, name, EquipmentType.WEAPON, WeaponType.BLUNT, ops)
    }

}
