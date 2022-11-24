package com.sonu.tictactoe

import com.sonu.tictactoe.models.MakeTurn
import com.sonu.tictactoe.models.TickTacToeGame
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Route.socket(game: TickTacToeGame) {
    route("/play") {
        webSocket {

            val player = game.connectPlayer(this)

            if (player == null) {
                close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "2 players already connected"))
                return@webSocket
            }
            try {
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val action = extractAction(frame.readText())
                        game.finishTurn(player, action.x, action.y)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()

            } finally {
                game.disconnectPlayer(player)
            }
        }
    }
}

/*
    String Format from client : make_turn#{json}
 */
private fun extractAction(message: String): MakeTurn {
    val type = message.substringBefore("#")
    val body = message.substringAfter("#")

    return if (type == "make_turn") {
        Json.decodeFromString(message)
    } else MakeTurn(-1, -1)

}