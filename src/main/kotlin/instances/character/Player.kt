package instances.character

import enums.EquipmentType
import ClientHandler
import instances.items.Equipment
import instances.items.Items
import instances.Stat

public class Player (var client: ClientHandler, name : String, ops : Character.() -> Unit = {}) : Character(name, CharacterType.PLAYER, ops)
{
    val paperDoll = ArrayList<Equipment>()

    //get() = paperDoll[0].bonuses.filter { it.stat == Stat.ATK } . sumBy { it.amount }

    override var health : Int = 0
    override val maxHealth
        get() = super.maxHealth + paperDoll.flatMap { it.bonuses } .filter { it.stat == Stat.HP } .sumBy { it.amount }

    override val atk : Int
        get() = super.atk + paperDoll.flatMap { it.bonuses }.filter { it.stat == Stat.ATK }.sumBy { it.amount }
    override val def : Int
        get() = super.def + paperDoll.flatMap { it.bonuses }.filter { it.stat == Stat.DEF }.sumBy { it.amount }
    override val critChance : Int
        get() = super.critChance + paperDoll.flatMap { it.bonuses }.filter { it.stat == Stat.CRIT_CHC }.sumBy { it.amount }
    override val critDamage : Int
        get() = super.critDamage + paperDoll.flatMap { it.bonuses }.filter { it.stat == Stat.CRIT_DMG }.sumBy { it.amount }



    init {
        health = maxHealth
    }

    override fun doAttack(target: Character) {
      super.doAttack(target)
    }

    override fun receiveAttack(attacker: Character, amount: Int) {
        super.receiveAttack(attacker, amount)
    }

    override fun doDie(killer: Character) {
        super.doDie(killer)
    }

    fun doEquip(equipment: Equipment) {

        var equiped : Equipment? = null

        //Check if we need to remove an equipment first
        when (equipment.equipmentType) {
            EquipmentType.WEAPON ->  equiped = paperDoll.find { it.equipmentType == EquipmentType.WEAPON }
            EquipmentType.ARMOR ->   equiped = paperDoll.find { it.secondType == equipment.secondType }
        }

        if (equiped != null) {
            client.writeln(equiped.name + " removed")
            paperDoll.remove(equiped)
        }
        paperDoll.add(equipment)
        updateChar()
        client.writeln(equipment.name + " equipped")
    }

    fun requestEquipment() {
        var message =  ""

        if (paperDoll.isEmpty()) {
            client.writeln("No equipments")
            return ;
        }

        paperDoll.forEach {
            message += it.name + " ";
        }
        client.writeln(message)
    }

    fun gmParser(message: String) {
        var values = message.split(" ");

        when (values[0]) {
            "give" -> {
                    when (values[1]) {
                        "equip" -> {
                            doEquip(Items.equipments[values[2].toInt()])
                        }
                }
            }
        }
    }

    fun haveClient(): Boolean {
        if (client == null)
            return false
        return true
    }

    override fun respawn() {
        super.respawn()
    }

    override fun gainExp(amount: Double) {
        super.gainExp(amount)
    }

    override fun updateChar() {
        super.updateChar()
    }

    override fun levelUp() {
        super.levelUp()
    }
}