package com.betclic.tourney.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidRequestArgumentsException(message: String) : RuntimeException(message)
