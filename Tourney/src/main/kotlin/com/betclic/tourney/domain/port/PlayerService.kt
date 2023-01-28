package com.betclic.tourney.domain.port

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.domain.model.Player

interface PlayerService {
    fun createPlayer(request: PlayerRequest): Player

    fun findById(id: String): Player

    fun findByName(name: String): Player

    fun findAll(): List<Player>

    fun deleteAllPlayers()

    fun patchPlayerScore(request: PlayerRequest): Player
}
