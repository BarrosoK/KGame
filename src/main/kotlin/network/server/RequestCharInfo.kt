package network.server

import instances.character.Player
import network.Packet

class RequestCharInfo : Packet() {
	override fun run(player: Player, data: String) {
		player.bonuses.forEach { player.client.writeln("stat ${it.stat} amount : ${it.amount}") }
	}
}