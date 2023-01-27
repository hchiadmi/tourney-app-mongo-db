package com.betclic.tourney.boundary.player

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.port.PlayerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/player")
class PlayerResource(
    private val playerService: PlayerService
) {

    @PostMapping
    fun createPlayer(@RequestBody request: PlayerRequest): ResponseEntity<PlayerResponse> {
        val createdPlayer = playerService.createPlayer(request)

        return ResponseEntity
            .ok(
                PlayerResponse.fromModel(createdPlayer)
            )
    }
}
