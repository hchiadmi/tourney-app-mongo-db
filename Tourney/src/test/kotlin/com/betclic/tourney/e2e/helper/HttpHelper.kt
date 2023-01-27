package com.betclic.tourney.e2e.helper

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

object HttpHelper {
    inline fun <reified T> sendGetRequest(url: String, method: HttpMethod = HttpMethod.GET): ResponseEntity<T>? {
        val headers = HttpHeaders()

        return TestRestTemplate().exchange(
            url,
            method,
            HttpEntity<String>(headers),
            T::class.java,
        )
    }

    inline fun <reified T> sendPostRequest(url: String, body: String): ResponseEntity<T>? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>(body, headers)

        return TestRestTemplate().postForEntity(
            url,
            request,
            T::class.java,
        )
    }

    inline fun <reified T> sendDeleteRequest(url: String): ResponseEntity<T>? {
        val headers = HttpHeaders()

        return TestRestTemplate().exchange(
            url,
            HttpMethod.DELETE,
            HttpEntity<String>(headers),
            T::class.java,
        )
    }
}
