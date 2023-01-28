package com.betclic.tourney.boundary.response

import com.betclic.tourney.domain.model.Player

class PlayerResponse(
    val id: String,
    val name: String,
    val score: Int,
    val rank: Int?
) {
    companion object {
        fun fromModel(player: Player): PlayerResponse {
            return PlayerResponse(
                id = player.id!!,
                name = player.name,
                score = player.score,
                rank = player.rank
            )
        }
    }
}
