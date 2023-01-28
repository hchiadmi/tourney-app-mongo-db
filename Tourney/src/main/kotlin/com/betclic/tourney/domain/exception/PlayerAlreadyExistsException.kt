package com.betclic.tourney.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class PlayerAlreadyExistsException(message: String) : RuntimeException(message)