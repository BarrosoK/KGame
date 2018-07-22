package network

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object LoginHandler {

	fun tryToLogin(username : String, password : String) : Boolean {

		var ret = false

		 transaction {
			var account = Accounts.select { Accounts.username.eq(username) }

			 if (account.count() == 0) {
				 println("Account doesn't exist")
			 }
			 if (account.any {
						 it[Accounts.username] == username
					 }) {
				 if (account.any { it[Accounts.password] == password })  {
					 ret = true
					 println("OK")
			 }	else {
					 println("Wrong Pass")
				 }
			 }
		}
		return ret
	}

}