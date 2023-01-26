package com.betclic.tourney.e2e

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
abstract class E2ETest {

    @LocalServerPort
    var serverPort: Int = 0

    protected fun applicationUrl(): String {
        return "http://localhost:$serverPort"
    }
}
