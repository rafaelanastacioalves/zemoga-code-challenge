package com.example.rafaelanastacioalves.moby.domain.entities

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class PostTest {

    @Test
    fun should_giveBasicProperties_WhenMainEntityCreatedOnlyWithTitle() {

        var testedMainEntitty: Post = createPost(1,)
        assertThat(testedMainEntitty.id, `is`(equalTo(1)))
        assertThat(testedMainEntitty.title, `is`(equalTo("title")))
        assertThat(testedMainEntitty.deleted, `is`(equalTo(false)))
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