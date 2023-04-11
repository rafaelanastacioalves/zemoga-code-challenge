package com.example.rafaelanastacioalves.zemoga.repository.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.core.StringContains
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule();

    private val context: Context by lazy {
        ApplicationProvider.getApplicationContext() as Context
    }

    private val testedDAO: DAO by lazy {
        AppDataBase.getInstance().appDAO()
    }

    @Test
    fun when_savingMainEntity_Should_ReturnMainEntity() {

        AppDataBase.setupAtApplicationStartup(context)
        testedDAO.deleteAllPosts()

        val mainEntityList: List<Post> = Arrays.asList(
                Post(
                        1L,
                        "1",
                        "Title",
                        "body",
                        true
                ),
                Post(2L,
                        "2",
                        "title",
                        "body",
                        false))
        
        testedDAO.savePostList(mainEntityList)

        val restoredMainEntityList = testedDAO.getPostList()
        val restoredFirstMainEntity = restoredMainEntityList.get(0)

        assertThat(restoredMainEntityList.size, CoreMatchers.`is`(2))
        assertThat(restoredFirstMainEntity.title, StringContains("Title"))
        assertThat(restoredFirstMainEntity.userId, StringContains("1"))
        assertThat(restoredFirstMainEntity.deleted, Matchers.equalTo(true))
        assertThat(restoredFirstMainEntity.favorited, Matchers.equalTo(false))

    }

    @Test
    fun when_ThereIsNoMainEntity_Should_Return_EmptyList() {
        AppDataBase.setupAtApplicationStartup(context)
        val testedDAO: DAO = testedDAO

        testedDAO.deleteAllPosts()

        val restoredMainEntityList = testedDAO.getPostList()

        assertThat(restoredMainEntityList.size, CoreMatchers.`is`(0))
    }

}