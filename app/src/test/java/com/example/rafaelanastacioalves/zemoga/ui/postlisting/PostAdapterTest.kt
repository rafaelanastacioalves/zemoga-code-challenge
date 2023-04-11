package com.example.rafaelanastacioalves.zemoga.ui.postlisting

import android.content.Context
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import kotlin.collections.ArrayList


@RunWith(MockitoJUnitRunner::class)
class PostAdapterTest {

    private val context : Context = Mockito.mock(Context::class.java)
    private var adapter: PostListAdapter = Mockito.spy(PostListAdapter(context))

    @Test
    open fun should_UpdatePost_WhenSetItemList(): Unit {
        doNothing().`when`(adapter).updateList()
        val mockedPostLists: List<Post> = ArrayList(Arrays.asList(
                Post(
                        1,
                        "title",
                        "price",
                        "dolar",
                        false
                ),
                Post(
                        2,
                        "title",
                        "price",
                        "dolar",
                        true
                )
        ))
        adapter.setItems(mockedPostLists)
        verify(adapter).updateList()
        val itemCount = adapter.itemCount
        assertThat(itemCount, `is`(2))
    }





}