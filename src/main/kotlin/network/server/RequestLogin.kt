package network.server

import instances.character.Player
import instances.chatroom.Rooms
import network.LoginHandler
import network.Packet

class RequestLogin : Packet() {

	override fun run(player: Player, data: String) {

		if (player.client.gameState != GameState.LOGGING)
			return

		var values = data.split("::")
		if (values.size < 2) {
			player.client.writeln("1 username::password")
			return
		}
		var ret = LoginHandler.tryToLogin(values[0], values[1])

		if (ret) {
			player.client.accountName = values[0]
			player.client.writeln("20 OK")
			player.client.gameState = GameState.LOBBY
			ExLobbyCharacters(player)
			player.client.writeln("Choose your character :\n3 [id]")
		} else {
			player.client.writeln("20 KO")
		}
	}

}