package com.betclic.tourney.e2e

import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.factory.PlayerFactory
import com.betclic.tourney.e2e.helper.HttpHelper
import com.betclic.tourney.infra.repository.PlayerRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class FindPlayerE2ETest : E2ETest(){

	@Autowired
	private lateinit var playerRepository: PlayerRepository

	@BeforeEach
	fun emptyDatabase(){
		playerRepository.deleteAll()
	}

	@Test
	fun `should find given player id from database with 200 status`() {
		// Given
		val player = playerRepository.save(
			PlayerFactory.create(
				"63d3db86d029c7506ddacfff",
				"Bob"
			)
		)

		// When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/players/${player.id}"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertEquals(player.id, response.body!!.id)
		assertEquals(player.name, response.body!!.name)
		assertEquals(player.score, response.body!!.score)
	}

	@Test
	fun `should throw error response when unknown player id is given`() {
		// Given
		val player = PlayerFactory.create(
			"63d3db86d029c7506ddacfff",
			"Bob"
		)

		// When
		val response: ResponseEntity<String>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/players/${player.id}"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Player with id [${player.id}] is unknown" ,response.body)
	}

	@Test
	fun `should find given player name from database with 200 status`() {
		// Given
		val player = playerRepository.save(
			PlayerFactory.create(
				"63d3db86d029c7506ddacfff",
				"Bob"
			)
		)

		// When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/players/name/${player.name}"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertEquals(player.id, response.body!!.id)
		assertEquals(player.name, response.body!!.name)
		assertEquals(player.score, response.body!!.score)
	}

	@Test
	fun `should throw error response when unknown player name is given`() {
		// Given
		val player = PlayerFactory.create(
			"63d3db86d029c7506ddacfff",
			"Bob"
		)

		// When
		val response: ResponseEntity<String>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/players/name/${player.name}"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Player with name [${player.name}] is unknown" ,response.body)
	}
}
