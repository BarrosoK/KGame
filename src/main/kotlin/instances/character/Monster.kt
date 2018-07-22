package instances.character

class Monster (name: String, ops : Character.() -> Unit = {}) : Character(name, CharacterType.MONSTER, ops)
{


    override fun doAttack(target: Character) {
        super.doAttack(target)
    }

    override fun receiveAttack(attacker: Character, amount: Int) {
        super.receiveAttack(attacker, amount)
    }

    override fun doDie(killer: Character) {
        killer.gainExp(1)
        drop.forEach {
            println(it.name)
        }
        super.doDie(killer)
    }
}