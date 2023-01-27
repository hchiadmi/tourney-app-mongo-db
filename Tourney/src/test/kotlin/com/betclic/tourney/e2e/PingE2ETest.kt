package com.betclic.tourney.e2e

import com.betclic.tourney.e2e.helper.HttpHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class PingE2ETest : E2ETest() {
    @Test
    fun `should return 200 for ping resource`() {
        // WHEN
        val result = HttpHelper.sendGetRequest<String>("${applicationUrl()}/ping")

        // THEN
        assertNotNull(result)
        assertEquals(200, result!!.statusCode.value())
        assertEquals("pong", result.body)
    }
}
