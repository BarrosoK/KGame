package network

import org.jetbrains.exposed.sql.*

object Accounts : Table() {

    val id = integer("id").autoIncrement().primaryKey() // Column<Int>
    val username = varchar("username", 255) // Column<String>
    var password = varchar("password", 255)  // Column<String>
}

object DbHandler {

    private const val url  = "jdbc:mysql://root:root@localhost:3306/game?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
    private const val driver = "com.mysql.cj.jdbc.Driver"

    fun init() {
        Database.connect(url, driver)
        println("Server connected to the database")
    }

    fun test() {

    }


}

