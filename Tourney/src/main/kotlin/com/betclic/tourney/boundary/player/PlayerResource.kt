package com.betclic.tourney.boundary.player

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.exception.InvalidRequestArgumentsException
import com.betclic.tourney.domain.port.PlayerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/player")
class PlayerResource(
    private val playerService: PlayerService
) {

    @PostMapping
    fun createPlayer(@RequestBody request: PlayerRequest): ResponseEntity<Any> {
        val createdPlayer = try {
            playerService.createPlayer(request)
        } catch (e : InvalidRequestArgumentsException) {
            return ResponseEntity.badRequest().body(e.message)
        }

        return ResponseEntity
            .ok(
                PlayerResponse.fromModel(createdPlayer)
            )
    }

    @GetMapping("/{id}")
    fun findPlayerById(@PathVariable id: String): ResponseEntity<PlayerResponse> {
        val player = playerService.findById(id)

        return ResponseEntity
            .ok(
                PlayerResponse.fromModel(player)
            )
    }
}
