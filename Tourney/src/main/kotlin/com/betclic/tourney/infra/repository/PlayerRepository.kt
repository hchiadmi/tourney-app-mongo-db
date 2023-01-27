package com.betclic.tourney.infra.repository

import com.betclic.tourney.domain.model.Player
import org.springframework.data.mongodb.repository.MongoRepository

interface PlayerRepository : MongoRepository<Player, String>
