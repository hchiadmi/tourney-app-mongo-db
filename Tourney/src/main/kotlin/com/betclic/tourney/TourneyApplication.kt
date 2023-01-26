package com.betclic.tourney

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TourneyApplication

fun main(args: Array<String>) {
	runApplication<TourneyApplication>(*args)
}
