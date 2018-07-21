package network

import network.server.RequestChannelList
import network.server.RequestPlayerList

// [XXX XXXXXXXX\n]
// 100 Kevin
enum class Packets(val value: Int, val packet: Packet) {
    REQUEST_PLAYER_LIST(100, RequestPlayerList()),
    REQUEST_CHANNELS_LIST(101, RequestChannelList())
}