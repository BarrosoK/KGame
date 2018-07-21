package network.server

import instances.character.Player
import instances.World
import network.Packet

class RequestPlayerList() : Packet(){

    override fun run(player: Player, data: String) {
        super.run(player, data)

        var list = "(${World.clients.size}) "

        World.clients.forEach {
            list += it.player.name + " "
        }

        player.client.writeln(list)
    }

}