import instances.character.Player
import instances.chatroom.Rooms
import instances.Stat
import instances.World
import network.PacketHandler
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*



class ClientHandler(client: Socket) {
    private val client: Socket = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    private var running: Boolean = false
    lateinit var player: Player

    init {
        World.clients.add(this)
    }

    fun run() {
        running = true
        // Welcome message
        write("Welcome to the server!\n" +
                "To Exit, write: 'EXIT'.\n" +
                "What's your pseudo : \n")

        val pseudo = reader.nextLine()
        player = Player(this, pseudo.split(' ')[0]) {
            baseAdd(Stat.ATK, 4)
            baseAdd(Stat.MATK, 5)
            baseAdd(Stat.DEF, 5)
            baseAdd(Stat.SPEED, 20)
            baseAdd(Stat.CRIT_CHC, 10)
            baseAdd(Stat.CRIT_DMG, 25)
            baseAdd(Stat.HP, 100)
            setLvl(3)
        }


        writeln("Welcome " + player.name)
        sendToOther(player.name + " logged !")

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
        World.clients.remove(this)
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }

}