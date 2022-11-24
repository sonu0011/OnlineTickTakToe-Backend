package com.sonu.tictactoe

import io.ktor.server.application.*
import com.sonu.tictactoe.plugins.configureMonitoring
import com.sonu.tictactoe.plugins.configureRouting
import com.sonu.tictactoe.plugins.configureSerialization
import com.sonu.tictactoe.plugins.configureSockets

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
