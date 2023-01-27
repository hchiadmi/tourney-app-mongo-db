package com.betclic.tourney.infra.service

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.domain.port.PlayerService
import com.betclic.tourney.infra.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
class PlayerDatabaseService(
    private val playerRepository: PlayerRepository
) : PlayerService {
    override fun createPlayer(request: PlayerRequest): Player {
        return playerRepository.save(
            Player(
                name = request.name,
            )
        )
    }
}
