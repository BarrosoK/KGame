package network.server

import instances.character.Player
import instances.chatroom.Rooms
import network.Packet

class RequestChannelList : Packet(){

    override fun run(player: Player, data: String) {
        super.run(player, data)

        var list = "Amount of rooms : ${Rooms.rooms.size}\n"
        Rooms.rooms.forEach {
            list += "[${it.name}] ${it.description}\n"
        }
        player.client.write(list)
    }

}