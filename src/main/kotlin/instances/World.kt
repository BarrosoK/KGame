package instances

import ClientHandler

object World {

    val clients = ArrayList<ClientHandler>()


           init {
            println("World created");
        }

    fun broadcast(message: String) {
        clients.forEach {
            it.writeln(message)
        }
    }

    fun getClient(pseudo: String) : ClientHandler? {
        var client: ClientHandler?

        client = clients.find {
            println(">${it.player.name}< | >${pseudo}<")
            it.player.name == pseudo.split(":")[0] }

        if (client == null) {
            println("No player found with this name")
            return null;
        }

        return client
    }

}