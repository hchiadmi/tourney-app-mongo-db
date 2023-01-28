package com.betclic.tourney.domain.helpers

import com.betclic.tourney.domain.model.Player

object PlayerHelper {
    fun List<Player>.updatePlayersRanks() : List<Player> {
        var previousPlayerScore = maxOf { it.score }
        var rank = 1
        return sortedByDescending{ player ->
            player.score
        }.map { sortedPlayer ->
            if (sortedPlayer.score < previousPlayerScore) {
                rank ++
            }
            previousPlayerScore = sortedPlayer.score
            sortedPlayer.copy(rank = rank)
        }
    }
}
