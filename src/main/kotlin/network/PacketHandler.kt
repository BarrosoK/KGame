package network

import instances.character.Player

object PacketHandler {

    fun analyzePacket(player: Player, packet: String) {

        val matchResult = Regex("""([\d]{1,3}) {0,1}([\w::]*)""").find(packet)

        if (matchResult == null) {
            println("omg")
            return
        }

        val (opcode, data) = matchResult.destructured
        println(opcode + ":" + data + ":" )

        Packets.values().find { it.value == opcode.toInt() }.apply {
            if (this != null) {
                if (player.client.gameState == GameState.LOGGING && opcode.toInt() != Packets.REQUEST_LOGIN.value) {
                    player.client.writeln("You have to login first")
                    return
                }
				if (player.client.gameState == GameState.LOBBY
                        && (opcode.toInt() != Packets.REQUEST_CHAR_LOBBY.value
                                && opcode.toInt() != Packets.REQUEST_CHAR_SELECT.value
                                && opcode.toInt() != Packets.REQUEST_CHAR_CREATION.value)) {
					player.client.writeln("You have to select a character or create a character")
					return
				}
                this.packet.run(player, data)
            }
        }
    }
}