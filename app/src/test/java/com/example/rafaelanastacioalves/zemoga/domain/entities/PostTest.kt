package com.example.rafaelanastacioalves.zemoga.domain.entities

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class PostTest {

    @Test
    fun should_giveBasicProperties_WhenPostsCreatedOnlyWithTitle() {

        var testedPost: Post = createPost(1,)
        assertThat(testedPost.id, `is`(equalTo(1)))
        assertThat(testedPost.title, `is`(equalTo("title")))
        assertThat(testedPost.deleted, `is`(equalTo(false)))
    }

    fun createPost(id: Long): Post {
        return Post(
                id,
                "123",
                "title",
                "body"
        )
    }
}