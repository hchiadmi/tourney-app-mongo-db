package com.betclic.tourney.unit.domain.factory

import com.betclic.tourney.domain.factory.PlayerFactory
import com.betclic.tourney.domain.model.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PlayerFactoryTest {
    private val id = "0000001"
    private val name = "Bob"
    private val score = 12
    private val rank = 1

    @Test
    fun `should return player with full attributes specified`() {
        // When
        val player = PlayerFactory.create(
            id,
            name,
            score,
            rank
        )

        // Then
        assertEquals(id, player.id)
        assertEquals(name, player.name)
        assertEquals(score, player.score)
        assertEquals(rank, player.rank)
    }

    @Test
    fun `should return player with default attributes specified`() {
        // When
        val player = PlayerFactory.create(
            name = name
        )

        // Then
        assertEquals(null, player.id)
        assertEquals(name, player.name)
        assertEquals(Player.DEFAULT_SCORE, player.score)
        assertEquals(null, player.rank)
    }
}
