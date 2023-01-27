package com.betclic.tourney.infra.repository

import com.betclic.tourney.domain.model.Player
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : MongoRepository<Player, String> {
    fun findByName(name: String): Player?

    fun findAllByOrderByScoreDesc(): List<Player>
}
