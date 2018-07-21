package instances.chatroom

import ClientHandler

class ChatRoom (val name: String, var description: String){
    var users = ArrayList<ClientHandler>()

    init {
        println("Chatroom ${name} created !")
    }

    fun speak(sender: ClientHandler, msg: String) {
        users.filter { it != sender }.forEach { it.writeln("[${name}] ${msg}") }
    }

    fun join(client: ClientHandler): Boolean {
        if (users.find { it == client } != null) {
            client.writeln("You're already in this room !")
            return false
        }
        speak(client, "${client.player.name} joined the room")
        client.writeln("You joined the room ${name}")
        users.add(client)
        return true
    }

    fun leave(client: ClientHandler): Boolean {

        if (users.remove(client)) {
            client.writeln("You left the room [${name}]")
            return true
        }
        return false
    }
}