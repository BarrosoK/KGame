package network.server

import instances.character.Player
import instances.character.Stat
import network.Packet

class RequestCharInfo : Packet() {
	override fun run(player: Player, data: String) {
		player.baseAdd(Stat.ATK, 100)
		println("bojnour")
		player.bonuses.forEach { player.client.writeln("stat ${it.stat} amount : ${it.amount}") }
	}
}