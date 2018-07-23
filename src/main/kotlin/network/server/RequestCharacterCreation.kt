package network.server

import instances.character.Player
import instances.character.Stat
import network.DbHandler
import network.Packet

class RequestCharacterCreation : Packet() {

	override fun run(player: Player, data: String) {
		player.name = data.split("::")[0]
		player.baseAdd(Stat.ATK, 5)
		player.baseAdd(Stat.HP, 100)
		player.baseAdd(Stat.SPEED, 5)
		player.baseAdd(Stat.CRIT_DMG, 5)
		player.baseAdd(Stat.CRIT_CHC, 5)
		DbHandler.createCharacter(player)
		player.client.writeln("Character ${player.name} created !")
		player.client.gameState = GameState.PLAYING
	}

}