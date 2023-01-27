package com.betclic.tourney.e2e

import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.model.Player
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
		//
		val player = playerRepository.save(
			Player(
				"63d3db86d029c7506ddacfff",
				"Bob",
				0
			)
		)

		//When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/api/player/${player.id}"
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
		//
		val player = Player(
			"63d3db86d029c7506ddacfff",
			"Bob",
			0
		)

		//When
		val response: ResponseEntity<String>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/api/player/${player.id}"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Player with id [${player.id}] is unknown" ,response.body)
	}

	@Test
	fun `should find given player name from database with 200 status`() {
		//
		val player = playerRepository.save(
			Player(
				"63d3db86d029c7506ddacfff",
				"Bob",
				0
			)
		)

		//When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/api/player/name/${player.name}"
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
		//
		val player = Player(
			"63d3db86d029c7506ddacfff",
			"Bob",
			0
		)

		//When
		val response: ResponseEntity<String>? = HttpHelper.sendGetRequest(
			"${applicationUrl()}/api/player/name/${player.name}"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Player with name [${player.name}] is unknown" ,response.body)
	}
}
