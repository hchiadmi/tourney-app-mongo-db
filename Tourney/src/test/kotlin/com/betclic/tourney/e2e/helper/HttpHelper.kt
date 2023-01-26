package com.betclic.tourney.e2e.helper

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

object HttpHelper {
    inline fun <reified T> sendRequest(url: String, method: HttpMethod = HttpMethod.GET): ResponseEntity<T>? {
        val headers = HttpHeaders()

        return TestRestTemplate().exchange(
            url,
            method,
            HttpEntity<String>(headers),
            T::class.java,
        )
    }
}
