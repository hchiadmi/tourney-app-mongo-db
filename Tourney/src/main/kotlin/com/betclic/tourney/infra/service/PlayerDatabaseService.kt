package com.betclic.tourney.infra.service

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.domain.exception.InvalidRequestArgumentsException
import com.betclic.tourney.domain.exception.NotFoundException
import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.domain.port.PlayerService
import com.betclic.tourney.infra.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
class PlayerDatabaseService(
    private val playerRepository: PlayerRepository
) : PlayerService {
    override fun createPlayer(request: PlayerRequest): Player {
        if (request.name.isNullOrEmpty()) {
            throw InvalidRequestArgumentsException("Bad arguments for add player request")
        }

        return playerRepository.save(
            Player(
                name = request.name,
            )
        )
    }

    override fun findById(id: String): Player {
        return playerRepository.findById(id).orElseThrow{
            NotFoundException("Player with id [$id] is unknown")
        }
    }

    override fun findByName(name: String): Player {
        return playerRepository.findByName(name)
            ?: throw NotFoundException("Player with name [$name] is unknown")
    }
}
