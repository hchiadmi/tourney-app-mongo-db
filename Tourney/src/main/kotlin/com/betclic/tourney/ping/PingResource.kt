package com.betclic.tourney.ping

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingResource {
    @GetMapping("/ping")
    fun ping() : String = "pong"
}
