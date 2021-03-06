package network

import instances.character.Player

open class Packet {
    lateinit var data : String
    lateinit var player: Player

    private fun create(player: Player, data: String) {
        this.player = player
        this.data = data
    }

    open fun run(player: Player, data: String) {
        create(player, data)
    }
}