package com.betclic.tourney.unit

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.domain.exception.InvalidRequestArgumentsException
import com.betclic.tourney.domain.exception.NotFoundException
import com.betclic.tourney.domain.exception.PlayerAlreadyExistsException
import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.infra.repository.PlayerRepository
import com.betclic.tourney.infra.service.PlayerDatabaseService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

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

    @Test
    fun `should throw NotFoundException when player id is unknown`(){
        every { playerRepository.findById(any()) } returns Optional.empty()

        val exception = assertThrows<NotFoundException> {
            playerDatabaseService.findById("23231314")
        }

        assertEquals("Player with id [23231314] is unknown", exception.message)
    }

    @Test
    fun `should throw NotFoundException when player name is unknown`(){
        every { playerRepository.findByName(any()) } returns null

        val exception = assertThrows<NotFoundException> {
            playerDatabaseService.findByName("Bob")
        }

        assertEquals("Player with name [Bob] is unknown", exception.message)
    }

    @Test
    fun `should throw PlayerAlreadyExistsException when added player name is taken`(){
        every { playerRepository.findByName(any()) } returns Player(name = "Bob")

        val exception = assertThrows<PlayerAlreadyExistsException> {
            playerDatabaseService.createPlayer(PlayerRequest("Bob"))
        }

        assertEquals("Player with name [Bob] already exists", exception.message)
    }
}