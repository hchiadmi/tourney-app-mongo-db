package com.betclic.tourney.unit

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.domain.exception.InvalidRequestArgumentsException
import com.betclic.tourney.infra.repository.PlayerRepository
import com.betclic.tourney.infra.service.PlayerDatabaseService
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PlayerDatabaseServiceTest {

    private val playerRepository = mockk<PlayerRepository>()

    private val playerDatabaseService = PlayerDatabaseService(playerRepository)

    @Test
    fun `should throw InvalidRequestArgumentsException when player name is null`(){
        val exception = assertThrows<InvalidRequestArgumentsException> {
            playerDatabaseService.createPlayer(PlayerRequest(null))
        }

        assertEquals("Bad arguments for add player request", exception.message)
    }

    @Test
    fun `should throw InvalidRequestArgumentsException when player name is empty`(){
        val exception = assertThrows<InvalidRequestArgumentsException> {
            playerDatabaseService.createPlayer(PlayerRequest(""))
        }

        assertEquals("Bad arguments for add player request", exception.message)
    }
}