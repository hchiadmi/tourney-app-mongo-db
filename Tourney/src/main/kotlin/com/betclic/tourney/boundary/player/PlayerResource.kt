package com.betclic.tourney.boundary.player

import com.betclic.tourney.boundary.request.PlayerRequest
import com.betclic.tourney.boundary.response.PlayerResponse
import com.betclic.tourney.domain.exception.InvalidRequestArgumentsException
import com.betclic.tourney.domain.exception.NotFoundException
import com.betclic.tourney.domain.exception.PlayerAlreadyExistsException
import com.betclic.tourney.domain.port.PlayerService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/players")
class PlayerResource(
    private val playerService: PlayerService
) {

    @PostMapping
    fun createPlayer(@RequestBody request: PlayerRequest): ResponseEntity<Any> {
        val createdPlayer = try {
            playerService.createPlayer(request)
        } catch (e : InvalidRequestArgumentsException) {
            return ResponseEntity.badRequest().body(e.message)
        } catch (e : PlayerAlreadyExistsException) {
            return ResponseEntity.status(409).body(e.message)
        }

        return ResponseEntity
            .ok(
                PlayerResponse.fromModel(createdPlayer)
            )
    }

    @GetMapping("/{id}")
    fun findPlayerById(@PathVariable id: String): ResponseEntity<Any> {
        val player = try {
            playerService.findById(id)
        } catch (e: NotFoundException) {
            return ResponseEntity.status(404).body(e.message)
        }

        return ResponseEntity
            .ok(
                PlayerResponse.fromModel(player)
            )
    }

    @GetMapping("/name/{name}")
    fun findPlayerByName(@PathVariable name: String): ResponseEntity<Any> {
        val player = try {
            playerService.findByName(name)
        } catch (e: NotFoundException) {
            return ResponseEntity.status(404).body(e.message)
        }

        return ResponseEntity
            .ok(
                PlayerResponse.fromModel(player)
            )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllPlayers(): ResponseEntity<List<PlayerResponse>> {
        val players = playerService.findAll()

        return ResponseEntity
            .ok(
                players.map { PlayerResponse.fromModel(it) }
            )
    }

    @DeleteMapping
    fun deleteAllPlayers(): ResponseEntity<String> {
        playerService.deleteAllPlayers()

        return ResponseEntity.ok("Deleted all players")
    }

    @PatchMapping
    fun patchPlayer(
        @RequestBody request: PlayerRequest
    ): ResponseEntity<Any> {
        val patchedPlayer = try {
            playerService.patchPlayerScore(request)
        } catch (e: NotFoundException) {
            return ResponseEntity.status(404).body(e.message)
        }

        return ResponseEntity
            .ok(
                PlayerResponse.fromModel(patchedPlayer)
            )
    }
}
