package com.betclic.tourney.e2e

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.factory.PlayerFactory
import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.e2e.helper.HttpHelper
import com.betclic.tourney.infra.repository.PlayerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class AddPlayerE2ETest : E2ETest(){

	@Autowired
	private lateinit var playerRepository: PlayerRepository

	@Autowired
	private lateinit var objectMapper: ObjectMapper

	@BeforeEach
	fun emptyDatabase(){
		playerRepository.deleteAll()
	}

	@Test
	fun `should add player to database with 200 status`() {
		// Given
		val player = PlayerFactory.create(
			name = "Axa"
		)

		val playerRequestBody = """
			{
			    "name": "${player.name}"
			}
		""".trimIndent()

		// When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendPostRequest(
			"${applicationUrl()}/players",
			playerRequestBody
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertNotNull(response.body!!.id)
		assertEquals(player.name, response.body!!.name)
		assertEquals(Player.DEFAULT_SCORE, response.body!!.score)
	}

	@Test
	fun `should add player with score to database with 200 status`() {
		// Given
		val playerRequest = PlayerRequest(
			name = "Axa",
			score = 12
		)

		// When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendPostRequest(
			"${applicationUrl()}/players",
			objectMapper.writer().writeValueAsString(playerRequest)
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertNotNull(response.body!!.id)
		assertEquals(playerRequest.name, response.body!!.name)
		assertEquals(playerRequest.score, response.body!!.score)
		assertEquals(1, response.body!!.rank)
	}

	@Test
	fun `should update all players ranks when adding a new player with status 200`() {
		// Given
		val alreadyInBasePlayer = playerRepository.save(
			PlayerFactory.create(
				"63d3db86d029c7506ddacfff",
				"Bob",
				12,
				1
			)
		)

		val playerRequest = PlayerRequest(
			name = "Axa",
			score = 13
		)

		// When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendPostRequest(
			"${applicationUrl()}/players",
			objectMapper.writer().writeValueAsString(playerRequest)
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertNotNull(response.body!!.id)
		assertEquals(playerRequest.name, response.body!!.name)
		assertEquals(playerRequest.score, response.body!!.score)
		assertEquals(1, response.body!!.rank)
		val updatedPlayer = playerRepository.findByName(alreadyInBasePlayer.name)
		assertNotNull(updatedPlayer)
		assertEquals(alreadyInBasePlayer.name, updatedPlayer!!.name)
		assertEquals(alreadyInBasePlayer.score, updatedPlayer.score)
		assertEquals(2, updatedPlayer.rank)
	}

	@Test
	fun `should throw error response when null player name is given`() {
		// Given
		val playerRequestBody = """
			{
			    "name": null
			}
		""".trimIndent()

		// When
		val response = HttpHelper.sendPostRequest<String>(
			"${applicationUrl()}/players",
			playerRequestBody
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Bad arguments for add player request", response.body)
	}

	@Test
	fun `should throw error response when empty player name is given`() {
		// Given
		val playerRequestBody = """
			{
			    "name": ""
			}
		""".trimIndent()

		// When
		val response = HttpHelper.sendPostRequest<String>(
			"${applicationUrl()}/players",
			playerRequestBody
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Bad arguments for add player request", response.body)
	}

	@Test
	fun `should throw error response when no name field is given in request`() {
		// Given
		val playerRequestBody = """
			{
			    "pseudo": "2"
			}
		""".trimIndent()

		// When
		val response = HttpHelper.sendPostRequest<String>(
			"${applicationUrl()}/players",
			playerRequestBody
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Bad arguments for add player request", response.body)
	}

	@Test
	fun `should throw error response when added player name is already taken`() {
		// Given
		val player = playerRepository.save(
			PlayerFactory.create(
				"63d3db86d029c7506ddacfff",
				"Bob"
			)
		)

		val playerRequestBody = """
			{
			    "name": "${player.name}"
			}
		""".trimIndent()

		// When
		val response = HttpHelper.sendPostRequest<String>(
			"${applicationUrl()}/players",
			playerRequestBody
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.CONFLICT, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Player with name [${player.name}] already exists", response.body)
	}
}
