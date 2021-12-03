package solutions.desati.drawin.gateway

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
  val username = varchar("username", 50).uniqueIndex()
  val password = varchar("password", 100)
}

fun ConnectDatabase() {
  val config = HikariConfig()
  config.jdbcUrl = "jdbc:pgsql://localhost:5432/postgres"
  config.username = "postgres"
  config.dataSourceClassName = "com.impossibl.postgres.jdbc.PGDataSource"
  val hikariDataSource = HikariDataSource(config)
  Database.connect(hikariDataSource)

  transaction {
    SchemaUtils.createMissingTablesAndColumns(Users)
  }
}
