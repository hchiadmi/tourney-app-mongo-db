package com.betclic.tourney.domain.factory

import com.betclic.tourney.domain.model.Player

object PlayerFactory {
    fun create(
        id: String? = null,
        name: String,
        score: Int? = null,
        rank: Int? = null
    ): Player {
        return Player(
            id,
            name,
            score?: Player.DEFAULT_SCORE,
            rank
        )
    }
}
