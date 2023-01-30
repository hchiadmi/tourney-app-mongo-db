package com.betclic.tourney.e2e

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.factory.PlayerFactory
import com.betclic.tourney.infra.repository.PlayerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
		// Given
		val player = playerRepository.save(
			PlayerFactory.create(
				"63d3db86d029c7506ddacfff",
				"Bob",
				0
			)
		)

		val playerRequest = PlayerRequest(player.name, 12)

		//When
		val response = mockMvc.patch("${applicationUrl()}/players"){
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writer().writeValueAsString(playerRequest)
		}

		// Then
		response.andExpect {
			status { isOk() }
			content {
				contentType(MediaType.APPLICATION_JSON)
				json(objectMapper.writeValueAsString(PlayerResponse(player.id!!, player.name, 12, 1)))
			}
		}
	}

	@Test
	fun `should update all players rank after patching a player score`() {
		// Given
		val bob = playerRepository.save(
			PlayerFactory.create(
				"00001",
				"Bob",
				12,
				1
			)
		)
		val alex = playerRepository.save(
			PlayerFactory.create(
				"000002",
				"Alex",
				10,
				2
			)
		)

		val playerRequest = PlayerRequest(alex.name, 14)

		//When
		val response = mockMvc.patch("${applicationUrl()}/players"){
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writer().writeValueAsString(playerRequest)
		}

		// Then
		response.andExpect {
			status { isOk() }
			content {
				contentType(MediaType.APPLICATION_JSON)
				json(objectMapper.writeValueAsString(PlayerResponse(alex.id!!, alex.name, 14, 1)))
			}
		}
		val bobAfterPatch = playerRepository.findByName(bob.name)
		assertNotNull(bobAfterPatch)
		assertEquals(2, bobAfterPatch!!.rank)
	}

	@Test
	fun `should not patch unknown player score and return status 404`() {
		// Given
		val unSavedPlayer = PlayerFactory.create(
			"63d3db86d029c7506ddacfff",
			"Bob",
			0
		)

		val playerRequest = PlayerRequest(unSavedPlayer.name, 12)

		// When
		val response = mockMvc.patch("${applicationUrl()}/players"){
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writer().writeValueAsString(playerRequest)
		}

		// Then
		val content = response.andExpect {
			status { isNotFound() }
		}.andReturn()

		assertEquals("Player with name [${playerRequest.name}] is unknown", content.response.contentAsString)
	}
}
