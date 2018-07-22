package network.server

import instances.character.Player
import instances.character.Stat
import network.Characters
import network.Packet
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class RequestSelectionCharacter : Packet() {

	override fun run(player: Player, data: String) {
		val characterId = data.toInt()

		transaction {
			var p = Characters.select {
				Characters.account.eq(player.client.accountName) and  Characters.id.eq(characterId)
			}.toList()
			if (p.size == 1) {
				player.baseAdd(Stat.ATK, p[0][Characters.attack])
				player.baseAdd(Stat.DEF, p[0][Characters.def])
				player.baseAdd(Stat.CRIT_CHC, p[0][Characters.crit_chance])
				player.baseAdd(Stat.CRIT_DMG, p[0][Characters.crit_dmg])
				player.baseAdd(Stat.SPEED, p[0][Characters.speed])
				player.baseAdd(Stat.HP, p[0][Characters.hp_max])
				player.name = p[0][Characters.name]
				player.experience = p[0][Characters.experience]
				player.bonuses.forEach { player.client.writeln("stat ${it.stat} amount : ${it.amount}") }
				player.client.writeln("Character loaded")
			} else {
				player.client.writeln("Wrong id")
			}
		}
	}

}