package network

import network.server.*

// [XXX XXXXXXXX\n]
// 100 Kevin
enum class Packets(val value: Int, val packet: Packet) {
    REQUEST_LOGIN(1, RequestLogin()),
	REQUEST_CHAR_LOBBY(3, RequestSelectionCharacter()),
	REQUEST_CHAR_INFO(4, RequestCharInfo()),
    REQUEST_PLAYER_LIST(100, RequestPlayerList()),
    REQUEST_CHANNELS_LIST(101, RequestChannelList())
}
