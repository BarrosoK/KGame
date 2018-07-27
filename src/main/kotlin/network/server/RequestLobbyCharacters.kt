package network.server

import instances.character.Player
import network.Packet

class RequestLobbyCharacters : Packet() {

    override fun run(player: Player, data: String) {
        ExLobbyCharacters(player)
    }

}