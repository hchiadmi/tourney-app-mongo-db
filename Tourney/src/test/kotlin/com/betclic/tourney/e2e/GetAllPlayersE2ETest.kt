package com.betclic.tourney.e2e

import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.factory.PlayerFactory
import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.e2e.helper.HttpHelper
import com.betclic.tourney.infra.repository.PlayerRepository
import com.fasterxml.jackson.module.kotlin.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class GetAllPlayersE2ETest : E2ETest(){

	@Autowired
	private lateinit var playerRepository: PlayerRepository

	@BeforeEach
	fun emptyDatabase(){
		playerRepository.deleteAll()
	}

	@Test
	fun `should find given player id from database with 200 status`() {
		// Given
		val bob = playerRepository.save(
			PlayerFactory.create(
				"1",
				"Bob",
				5
			)
		)

		val alex = playerRepository.save(
			PlayerFactory.create(
				"2",
				"Alex",
				12
			)
		)

		val tom = playerRepository.save(
			PlayerFactory.create(
				"3",
				"tom",
				7
			)
		)

		// When
		val response: ResponseEntity<List<PlayerResponse>>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/players"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)

		val players = readPlayerResponses(response)

		assertEquals(3, players.size)
		assertPlayerIsEqualToPlayerResponse(alex, players.first())
		assertPlayerIsEqualToPlayerResponse(tom, players[1])
		assertPlayerIsEqualToPlayerResponse(bob, players[2])
	}

	private fun readPlayerResponses(response: ResponseEntity<List<PlayerResponse>>): List<PlayerResponse> {
		val objectMapper = jacksonObjectMapper()
		val responseData = objectMapper.writeValueAsString(response.body!!)
		return jacksonObjectMapper().readValue(responseData)
	}

	private fun assertPlayerIsEqualToPlayerResponse(
		player: Player,
		playerResponse: PlayerResponse?
	) {
		assertNotNull(playerResponse)
		assertEquals(player.id, playerResponse!!.id)
		assertEquals(player.name, playerResponse.name)
		assertEquals(player.score, playerResponse.score)
	}
}
