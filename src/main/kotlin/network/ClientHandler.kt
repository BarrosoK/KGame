import instances.character.Player
import instances.chatroom.Rooms
import instances.character.Stat
import instances.World
import network.DbHandler
import network.PacketHandler
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*
import kotlin.math.log

enum class GameState {
	LOGGING,
	LOBBY,
	PLAYING
}

class ClientHandler(client: Socket) {
	lateinit var  accountName : String
    private val client: Socket = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    private var running: Boolean = false
    lateinit var player: Player
    private var logged = false
	var gameState  : GameState = GameState.LOGGING

    init {
        World.clients.add(this)
    }

    fun run() {
        running = true
        // Welcome message
        write("Welcome to the server!\n" +
                "To Exit, write: 'EXIT'.\n" +
                "To Login : 1 username::pass\n")
        /*
        val pseudo = reader.nextLine()

        */
        player = Player(this, "Anon".split(' ')[0]) {
        }

        loop@ while (running) {
            try {
                PacketHandler.analyzePacket(player, reader.nextLine())
            } catch (ex: Exception) {
                // TODO: Implement exception handling
                shutdown()
            } finally {

            }

        }
    }

    private fun requestChanList() {
        var list = "Amount of rooms : ${Rooms.rooms.size}\n"

        Rooms.rooms.forEach {
            list += "[${it.name}] ${it.description}\n"
        }
        write(list)
    }

    private fun requestPlayerList() {
        var list = "(${World.clients.size}) "

        World.clients.forEach {
            list += it.player.name + " "
        }
        writeln(list)
    }

    private fun invalidCommand() {
        writeln("Invalid command")
    }

    fun writeBits(data: ByteArray) {
        writer.write(data)
    }

    fun write(message: String) {
        writer.write((message).toByteArray(Charset.defaultCharset()))
    }

    fun writeln(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }

    fun sendToOther(message: String) {
        World.clients.forEach {
            if (it != this)
                it.writeln(message)
        }
    }

    fun sendTo(receiver: ClientHandler, msg: String) {
        println("cc " + receiver.player.name)
        receiver.writeln(msg)
    }

    private fun shutdown() {
		DbHandler.updateCharacter(player)
        World.clients.remove(this)
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }

}