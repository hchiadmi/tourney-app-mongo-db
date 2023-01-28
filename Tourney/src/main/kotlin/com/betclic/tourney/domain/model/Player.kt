package com.betclic.tourney.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("players")
data class Player(
    @Id
    val id: String? = null,
    var name: String,
    @Field("score")
    var score: Int = 0,
)
