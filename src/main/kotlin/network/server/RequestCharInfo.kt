package network.server

import instances.character.Player
import instances.character.Stat
import network.Packet

class RequestCharInfo : Packet() {
	override fun run(player: Player, data: String) {
		var msg = ""

		msg += player.client.writeln("Name : ${player.name}\n")
		player.bonuses.forEach { msg += "stat ${it.stat} amount : ${it.amount}\n" }
		player.client.write(msg)
	}
}