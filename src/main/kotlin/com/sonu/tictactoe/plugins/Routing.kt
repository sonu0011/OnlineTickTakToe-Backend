package com.sonu.tictactoe.plugins

import com.sonu.tictactoe.models.TicTacToeGame
import com.sonu.tictactoe.socket
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(game: TicTacToeGame) {
    routing {
        socket(game)
    }
}
