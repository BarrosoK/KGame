package network

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.beans.Encoder
import javax.crypto.EncryptedPrivateKeyInfo

object Accounts : Table() {

	val id = integer("id").autoIncrement().primaryKey() // Column<Int>
	val username = varchar("username", 255) // Column<String>
	var password = varchar("password", 255)  // Column<String>
}

object 	DbHandler {

	private const val url = "jdbc:mysql://root:root@localhost:3306/game?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
	private const val driver = "com.mysql.cj.jdbc.Driver"
	private val connect = Database.connect(url, driver)

	fun createAccount(username: String, password: String) {

		transaction {
			if (Accounts.select { Accounts.username.eq(username) }.count() > 0 ) {
				// Username already exist
			} else {
				Accounts.insert {
					it[Accounts.username] = username
					it[Accounts.password] = password
				}
			}
		}
	}

	fun deleteAccount(username : String) {
		transaction {
			Accounts.deleteWhere { Accounts.username.eq(username) }
		}
	}

}

