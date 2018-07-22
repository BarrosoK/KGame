package network

import instances.character.Player

open class Packet {
    lateinit var data : ByteArray
    lateinit var player: Player

    private fun create(player: Player, data: Any) {
        this.player = player
    }

    open fun run(player: Player, data: String) {
        create(player, data)
    }
}