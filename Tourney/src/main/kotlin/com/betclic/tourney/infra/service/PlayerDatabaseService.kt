package com.betclic.tourney.infra.service

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.domain.exception.InvalidRequestArgumentsException
import com.betclic.tourney.domain.exception.NotFoundException
import com.betclic.tourney.domain.exception.PlayerAlreadyExistsException
import com.betclic.tourney.domain.factory.PlayerFactory
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
        if (playerRepository.findByName(request.name) != null) {
            throw PlayerAlreadyExistsException("Player with name [${request.name}] already exists")
        }

        val createdPlayer = playerRepository.save(
            PlayerFactory.create(
                name = request.name,
                score = request.score
            )
        )

        updateAllPlayersRanks()

        return playerRepository.findByName(createdPlayer.name)!!
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

    override fun deleteAllPlayers() {
        playerRepository.deleteAll()
    }

    override fun findAll(): List<Player> {
        return playerRepository.findAllByOrderByScoreDesc()
    }

    override fun patchPlayerScore(request: PlayerRequest): Player {
        val playerToPatch = findByName(request.name!!)

        return playerRepository.save(
            playerToPatch.apply {
                this.score = request.score!!
            }
        )
    }

    private fun updateAllPlayersRanks() {
        playerRepository.saveAll(
            playerRepository.findAll().updatePlayersRanks()
        )
    }
}
