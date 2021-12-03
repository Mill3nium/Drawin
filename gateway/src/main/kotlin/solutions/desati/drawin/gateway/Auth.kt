package solutions.desati.drawin.gateway

import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.JWTOptions
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.jsonObjectOf
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLIntegrityConstraintViolationException

fun auth() {
  router.post("/auth/signup").handler(BodyHandler.create()).respond {
    val json = it.bodyAsJson
    // todo validate json

    try {
        val username = transaction {
          Users.insert {
            it[username] = json.getString("username")
            it[password] = json.getString("password")
          } get Users.username
        }

      val token = authProvider.generateToken(
        JsonObject().put("sub", username), JWTOptions().setExpiresInMinutes(60)
      )
      val rb = jsonObjectOf(
        "token" to token
      )

      it.response().end(rb.encode())
    } catch (e: SQLIntegrityConstraintViolationException) {
      it.response().setStatusCode(HttpResponseStatus.CONFLICT.code()).end()
    }
  }

  router.post("/auth/signin").handler(BodyHandler.create()).respond {
    val json = it.bodyAsJson
    val username = json.getString("username")
    val password = json.getString("password")

    try {
      transaction {
        val user = Users.select { (Users.username eq username) and (Users.password eq password) }
          .single()

        val token: String = authProvider.generateToken(
          JsonObject().put("sub", user[Users.username]), JWTOptions()
        )

        it.response().end(jsonObjectOf("token" to token).encode())
      }
    } catch (e: NoSuchElementException) {
      it.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code()).end()
    }
  }
}
