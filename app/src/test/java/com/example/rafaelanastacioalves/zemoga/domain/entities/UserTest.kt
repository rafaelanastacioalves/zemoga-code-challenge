package com.example.rafaelanastacioalves.zemoga.domain.entities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UserTest {

    private val USER_ID = "123"
    private val USERNAME = "john_doe"
    private val EMAIL = "johndoe@example.com"

    @Test
    fun `user properties should be set correctly`() {
        val user = User(USER_ID, USERNAME, EMAIL)
        assertEquals(USER_ID, user.id)
        assertEquals(USERNAME, user.username)
        assertEquals(EMAIL, user.email)
    }

    @Test
    fun `two users with different ids should not be equal`() {
        val user1 = User(USER_ID, USERNAME, EMAIL)
        val user2 = User("456", "jane_doe", "janedoe@example.com")
        assertNotEquals(user1, user2)
    }
}
