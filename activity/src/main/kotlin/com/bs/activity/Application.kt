package com.bs.activity

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.netty.http.server.HttpServer
import java.time.Duration

@SpringBootApplication
class Application{
//   private val httpHandler:HttpHandler
//    private val server:HttpServer
//
//    constructor(port: Int=8080){
//        val context = GenericApplicationContext().apply {
//            beans().initialize(this)
//            refresh()
//        }
//        httpHandler= WebHttpHandlerBuilder.applicationContext(context).build()
//        server = HttpServer.create().host("127.0.0.1").port(port)
//
//
//    }
//    fun startAndAwait() {
//        server.handle(ReactorHttpHandlerAdapter(httpHandler)).bindUntilJavaShutdown(Duration.ofSeconds(45), null)
//    }
}

//class BeanInitialisation:ApplicationContextInitializer<GenericApplicationContext>{
//    override fun initialize(context:GenericApplicationContext) {
//        beans().initialize(context)
//    }
//}

fun main(args: Array<out String>) {
    //Application().startAndAwait()
    SpringApplication.run(Application::class.java, *args)
    //runApplication<Application>(*args)
}
