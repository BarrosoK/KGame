package network.server

import network.Accounts
import network.Packet
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class CreateAccount : Packet() {

    var username = ""
    var passwird = ""

    fun createAccount() : Boolean {
        transaction {
            if (Accounts.selectAll().any { it[Accounts.username] == username }) {
                println("Username already taken")
            } else {
                println("Account created    ")
            }
        }
        return true
    }
}