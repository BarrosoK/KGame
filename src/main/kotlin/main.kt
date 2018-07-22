import network.Accounts
import network.DbHandler
import instances.chatroom.Rooms
import instances.items.Items
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.ServerSocket
import kotlin.concurrent.thread

fun init() {
    Items.init()
    Rooms.init()
	DbHandler
}

fun main(args : Array<String>) {

    init()

    val server = ServerSocket(4242)
    println("Server is running on port ${server.localPort}")

	transaction {
		Accounts.select(where = Accounts.username.eq("Joseph")).forEach {
			println(it[Accounts.password])
		}
	}

    while (true) {
        val client = server.accept()
        println("Client connected: ${client.inetAddress.hostAddress}")
        thread { ClientHandler(client).run() }
    }

}