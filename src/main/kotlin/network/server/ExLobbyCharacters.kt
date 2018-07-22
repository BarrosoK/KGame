package network.server

import instances.character.Player
import network.Characters
import network.Packet
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ExLobbyCharacters(val player: Player) {

		init {
			var lobby = ""
		transaction {
			Characters.select { Characters.account.eq(player.client.accountName) }.forEach {
				lobby += "${it[Characters.id]} ${it[Characters.name]}\n"
			}
			player.client.write(lobby)
		}
	}

}