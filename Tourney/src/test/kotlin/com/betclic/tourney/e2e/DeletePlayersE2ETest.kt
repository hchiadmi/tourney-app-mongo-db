package com.betclic.tourney.e2e

import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.e2e.helper.HttpHelper
import com.betclic.tourney.infra.repository.PlayerRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class DeletePlayersE2ETest : E2ETest(){

	@Autowired
	private lateinit var playerRepository: PlayerRepository

	@BeforeEach
	fun emptyDatabase(){
		playerRepository.deleteAll()
	}

	@Test
	fun `should delete all players with status 200`() {
		// Given
		playerRepository.save(
			Player(
				"63d3db86d029c7506ddacfff",
				"Bob",
				0
			)
		)

		// When
		val response: ResponseEntity<String>? = HttpHelper.sendDeleteRequest(
			"${applicationUrl()}/api/player"
		)

		// Then
		assertNotNull(response!!)
		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertEquals("Deleted all players", response.body)
		assertTrue(playerRepository.findAll().isEmpty())
	}
}
