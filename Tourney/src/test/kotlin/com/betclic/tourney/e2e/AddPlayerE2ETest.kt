package com.betclic.tourney.e2e

import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.e2e.helper.HttpHelper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


class AddPlayerE2ETest : E2ETest(){

	@Test
	fun `should add player to database with 200 status`() {

		// Given
		val player = Player(
			name = "Axa"
		)

		val playerRequestBody = """
			{
			    "name": "${player.name}"
			}
		""".trimIndent()

		// When
		val response: ResponseEntity<PlayerResponse>? = HttpHelper.sendPostRequest(
			"${applicationUrl()}/api/player",
			playerRequestBody
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertNotNull(response.body!!.id)
		assertEquals(player.name, response.body!!.name)
		assertEquals(0, response.body!!.score)
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
			"${applicationUrl()}/api/player",
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
			"${applicationUrl()}/api/player",
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
			"${applicationUrl()}/api/player",
			playerRequestBody
		)


		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Bad arguments for add player request", response.body)
	}
}
