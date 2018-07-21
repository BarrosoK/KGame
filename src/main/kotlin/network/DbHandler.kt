package instances

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop

object account : Table() {
    val id = integer("id").autoIncrement().primaryKey() // Column<Int>
    val username = varchar("username", 50) // Column<String>
}

object DbHandler {

    init {
        println("yo")
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

        transaction {
            create(account)

            account.insert {
                it[username] = "Joseph"
            }
        }
    }
}

