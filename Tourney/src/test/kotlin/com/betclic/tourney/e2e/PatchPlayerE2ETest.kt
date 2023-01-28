package com.betclic.tourney.e2e

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.model.Player
import com.betclic.tourney.infra.repository.PlayerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.patch

@AutoConfigureMockMvc
class PatchPlayerE2ETest : E2ETest(){

	@Autowired
	private lateinit var playerRepository: PlayerRepository

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Autowired
	private lateinit var objectMapper: ObjectMapper

	@BeforeEach
	fun emptyDatabase(){
		playerRepository.deleteAll()
	}

	@Test
	fun `should patch player score with 200 status`() {
		//
		val player = playerRepository.save(
			Player(
				"63d3db86d029c7506ddacfff",
				"Bob",
				0
			)
		)

		val playerRequest = PlayerRequest(player.name, 12)

		//When
		val response = mockMvc.patch("${applicationUrl()}/api/player"){
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writer().writeValueAsString(playerRequest)
		}

		// Then
		response.andExpect {
			status { isOk() }
			content {
				contentType(MediaType.APPLICATION_JSON)
				json(objectMapper.writeValueAsString(PlayerResponse(player.id!!, player.name, 12)))
			}
		}
	}

	@Test
	fun `should not patch unknown player score and return status 400`() {
		//
		val unSavedPlayer = Player(
			"63d3db86d029c7506ddacfff",
			"Bob",
			0
		)

		val playerRequest = PlayerRequest(unSavedPlayer.name, 12)

		//When
		val response = mockMvc.patch("${applicationUrl()}/api/player"){
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writer().writeValueAsString(playerRequest)
		}

		// Then
		val content = response.andExpect {
			status { isBadRequest() }
		}.andReturn()

		Assertions.assertEquals("Player with name [${playerRequest.name}] is unknown", content.response.contentAsString)
	}
}
