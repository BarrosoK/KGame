package instances.character

import instances.Bonus
import instances.items.Item
import instances.Stat
import java.util.*

enum class CharacterType {
    NPC,
    PLAYER,
    MONSTER
}

class Position {
    var x = 0
    var y = 0
}

open class Character(name: String, type: CharacterType, ops : Character.() -> Unit = {}) {
    var level: Int = 0
    var experience: Double = 0.0
    val name: String
    val type: CharacterType
    var isDead: Boolean = false
    val bonuses = ArrayList<Bonus>()
    val drop = ArrayList<Item>()
    var position = Position()
    var lastAttack: Double = 0.0
    var attackSpeed: Double = 0.0
    open var health : Int = 0

    init {
        this.name = name
        this.type = type
        this.ops()
        health = bonuses.filter { it.stat == Stat.HP }. sumBy { it.amount }
        println(name)
        bonuses.forEach { println("stat ${it.stat} amount : ${it.amount}") }
    }

    fun ClosedRange<Int>.random() =
            Random().nextInt(endInclusive - start) +  start



    fun dropAdd(item: Item) {
        drop.add(item)
    }

    fun baseAdd(stat: Stat, amount: Int) {
        bonuses += Bonus(stat, amount)
    }

    fun setLvl(level : Int) {
        this.level = level
    }

    open val maxHealth
        get() = bonuses.filter { it.stat == Stat.HP } . sumBy { it.amount }
    open val atk
        get() = bonuses.filter { it.stat == Stat.ATK } . sumBy { it.amount }
    open val def
        get() = bonuses.filter { it.stat == Stat.DEF } . sumBy { it.amount }
    open val critChance
        get() = bonuses.filter { it.stat == Stat.CRIT_CHC } . sumBy { it.amount }
    open val critDamage
        get() = bonuses.filter { it.stat == Stat.CRIT_DMG } . sumBy { it.amount }


    open fun doAttack(target: Character) {
        var player: Player = this as Player

        if (isDead) {
                player.client.writeln("You can't attack while u're dead noob")
            return
        }

        /* Attack Speed */
        val time = (System.currentTimeMillis() / 1000).toDouble()

        attackSpeed = 5.0
        if (time < lastAttack + attackSpeed) {
            if (player.isPlayer()) {
                player.client.writeln("You're attacking too fast ${((lastAttack + attackSpeed) - time)}")
            }
            return
        }
        lastAttack = time


        println("${name} attack ${target.name}")

        /* Critical */
        var critBonus : Int = 0
        val randomCrit = 1.0 + (1.5 - 1.0) * Random().nextDouble()
        if ((0..100).random() < critChance) {
            critBonus = (critDamage * randomCrit).toInt()
        }


        val randomWeap = 0.8 + (1.2 - 0.8) * Random().nextDouble()
        var damage : Int = (10.0 * (randomWeap * (atk  + critBonus )) / target.def).toInt()
        if (damage < 0)
            damage = 0
        target.receiveAttack(this, damage)
    }

    open fun receiveAttack(attacker: Character, amount: Int) {

        var p: Player = attacker as Player
        var m: Player = this as Player

        if (attacker.isPlayer()) {
            p.client.writeln("You dealt ${amount} to ${this.name}")
        }

        if (this.isPlayer()) {
            m.client.writeln("Received ${amount} damages from ${attacker.name}")
        }

        health -= amount
        if (health <= 0) {
            health = 0
            doDie(attacker)
        }
    }

    fun isPlayer(): Boolean {
        if (type == CharacterType.PLAYER)
            return true
        return false
    }

    open fun doDie(killer: Character) {
        println("Killed by ${killer.name}")
        isDead = true
    }

    open fun respawn() {
        health = maxHealth
        isDead = false
    }

    open fun levelUp() {
        level += 1
    }

    open fun gainExp(amount: Double) {
        if (amount < 0) {
            return
        }

        experience += amount

    }

    open fun updateChar() {
    }

}