package network

import instances.character.Character
import instances.character.Player
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.beans.Encoder
import javax.crypto.EncryptedPrivateKeyInfo

object Accounts : Table() {

	val id = integer("id").autoIncrement().primaryKey() // Column<Int>
	val username = varchar("username", 255) // Column<String>
	var password = varchar("password", 255)  // Column<String>
}

object Characters : Table() {
	val account = varchar("account", 255)
	val id = integer("id").autoIncrement().primaryKey()
	val name = varchar("name", 255)
	val attack = integer("attack")
	val matk = integer("matk")
	val def = integer("def")
	val speed = integer("speed")
	val crit_chance = integer("crit_chance")
	val crit_dmg = integer("crit_dmg")
	val hp_current = integer("hp_current")
	val hp_max = integer("hp_max")
	val experience = integer("experience")
}

object 	DbHandler {

	private const val url = "jdbc:mysql://root:root@localhost:3306/game?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
	private const val driver = "com.mysql.cj.jdbc.Driver"
	private val connect = Database.connect(url, driver)

	fun createCharacter(player : Player) {
		transaction {
			Characters.insert {
				it[Characters.name] = player.name
				it[Characters.account] = player.client.accountName
				it[Characters.attack] = player.atk
				it[Characters.def] = player.def
				it[Characters.crit_chance] = player.critChance
				it[Characters.crit_dmg] = player.critDamage
				it[Characters.experience] = player.experience
				it[Characters.speed]  = player.speed
				it[Characters.hp_current] = player.health
				it[Characters.hp_max] = player.maxHealth
			}
		}
	}

	fun listCharacters() {

	}

	fun loadCharacter(id : Int) {

	}

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

