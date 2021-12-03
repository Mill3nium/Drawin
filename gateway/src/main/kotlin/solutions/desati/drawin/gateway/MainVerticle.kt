package solutions.desati.drawin.gateway

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.http.HttpServer
import io.vertx.ext.auth.PubSecKeyOptions
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.auth.jwt.JWTAuthOptions
import io.vertx.ext.web.Router

lateinit var server: HttpServer
lateinit var router: Router
lateinit var authProvider: JWTAuth

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {

    ConnectDatabase()

    server = vertx.createHttpServer()
    router = Router.router(vertx)

    authProvider = JWTAuth.create(
      vertx, JWTAuthOptions()
        .addPubSecKey(
          PubSecKeyOptions()
            .setAlgorithm("HS256")
            .setBuffer("keyboard cat")
        )
    )

    // Routes
    auth()

    server.requestHandler(router).listen(8888)
  }
}
