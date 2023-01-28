package com.betclic.tourney.unit.domain.helper

import com.betclic.tourney.domain.factory.PlayerFactory
import com.betclic.tourney.domain.helpers.PlayerHelper.updatePlayersRanks
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PlayerHelperTest {

    @Test
    fun `should update players ranks by their scores rank in descending order`() {
        // Given
        val bob = PlayerFactory.create(
            "000001",
            "Bob",
            12
        )

        val tom = PlayerFactory.create(
            "000002",
            "Tom",
            12
        )

        val alex = PlayerFactory.create(
            "000003",
            "alex",
            15
        )

        // When
        val result = listOf(bob, alex, tom).updatePlayersRanks()

        // Then
        assertEquals(alex.copy(rank = 1), result.first())
        assertEquals(bob.copy(rank = 2), result[1])
        assertEquals(tom.copy(rank = 2), result[2])
    }

    @Test
    fun `should have max ranking to 1`() {
        // Given
        val bob = PlayerFactory.create(
            "000001",
            "Bob",
            0
        )

        // When
        val result = listOf(bob).updatePlayersRanks()

        // Then
        assertEquals(bob.copy(rank = 1), result.first())
    }
}
