import instances.chatroom.Rooms
import instances.items.Items
import java.net.ServerSocket
import kotlin.concurrent.thread

fun init() {
    Items.init()
    Rooms.init()
}

fun main(args : Array<String>) {

    init()

    val server = ServerSocket(4242)
    println("Server is running on port ${server.localPort}")


    while (true) {
        val client = server.accept()
        println("Client connected: ${client.inetAddress.hostAddress}")

        // Run client in it's own thread.
        thread { ClientHandler(client).run() }
    }


    /*
    var monster = Monster("Lionel Brosius") {
        baseAdd(Stat.HP, 100)
        baseAdd(Stat.ATK, 3)
        baseAdd(Stat.MATK, 5)
        baseAdd(Stat.DEF, 5)
        baseAdd(Stat.SPEED, 20)
        baseAdd(Stat.CRIT_CHC, 10)
        baseAdd(Stat.CRIT_DMG, 25)
        setLvl(4)
        dropAdd(Items.potions[0])
    }


    player.doEquip(Items.chests[0])
    player.doEquip(Items.blunts[0])
    player.doEquip(Items.swords[0])


    while (!player.isDead && !monster.isDead) {
        player.doAttack(monster)
        monster.doAttack(player)
    }
    */
}