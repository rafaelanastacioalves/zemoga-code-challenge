package com.example.rafaelanastacioalves.zemoga.ui.postlisting

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.domain.entities.Resource
import com.example.rafaelanastacioalves.zemoga.domain.interactors.PostListInteractor
import com.example.rafaelanastacioalves.zemoga.repository.AppRepository
import com.example.rafaelanastacioalves.zemoga.repository.database.AppDataBase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PostListingViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: PostListingViewModel
    private lateinit var postListInteractor: PostListInteractor
    private lateinit var repository: AppRepository
    private lateinit var postListLiveDataObserver: Observer<Resource<List<Post>>>
    private lateinit var appDataBase: AppDataBase


    @Before
    fun setUp() {
        unmockkAll()

        val context  = ApplicationProvider.getApplicationContext<Context>()
        postListInteractor = mockk(relaxed = true)
        mockkStatic(AppDataBase::class)
        AppDataBase.setupAtApplicationStartup(context)
        appDataBase = mockk()
        repository = mockk()
        viewModel = PostListingViewModel(postListInteractor, repository)
        postListLiveDataObserver = mockk(relaxed = true)
        viewModel.postListLiveData.observeForever(postListLiveDataObserver)
    }

    @After
    fun tearDown() {
        viewModel.postListLiveData.removeObserver(postListLiveDataObserver)
        unmockkAll()
    }

    @Test
    fun `loadDataIfNecessary should call PostListInteractor execute`() {
        runBlocking {
            coEvery { postListInteractor.execute(any(), any(), any()) } just Runs

            viewModel.loadDataIfNecessary()

            coVerify { postListInteractor.execute(any(), any(), any()) }
        }
    }

    @Test
    fun `onDeleteItemPosition should call savePost on repository`() {
        val postId = "1"
        val post = Post(postId.toLong(), "title", "body", "userId")
        runBlocking {
            coEvery { repository.savePost(any()) } just Runs

            viewModel.onDeleteItemPosition(post)

            coVerify { repository.savePost(post) }
        }
    }

    @Test
    fun `onFavoriteItemPosition should call savePost on repository`() {
        val postId = "1"
        val post = Post(postId.toLong(), "title", "body", "userId")
        runBlocking {
            coEvery { repository.savePost(any()) } just Runs

            viewModel.onFavoriteItemPosition(post)

            coVerify { repository.savePost(post) }
        }
    }

    @Test
    fun `showOnlyFavorites should update isOnlyFavorites value and load data`() = runBlocking {

        //define an observer
        val observer = mockk<Observer<Boolean>>(relaxed = true)

        // create slot and mocks its capture mechanisms


        every { postListInteractor.execute(any(), any(), any()) } just Runs

        viewModel.showOnlyFavorites()

        val slot = slot<Boolean>()
        justRun { observer.onChanged(capture(slot)) }
        viewModel.isOnlyFavorites.observeForever(observer)

        assertEquals(slot.isCaptured, true)
        assertEquals(slot.captured, true)
        coVerify { postListInteractor.execute(any(), any(), any()) }
    }

    @Test
    fun `finalPostLiveData should filter only favorite posts if isOnlyFavorites is true`() {
        val observer = mockk<Observer<Resource<List<Post>>>>(relaxed = true)

        val post1 = Post(1.toLong(), "1", "body", "userId",favorited = true)
        val post2 = Post(2.toLong(), "2", "body", "userId", favorited = false)
        val resource = Resource.success(listOf(post1, post2))

        val slot = slot<Resource<List<Post>>>()
        every { observer.onChanged(capture(slot)) } just Runs

        viewModel._isOnlyFavorites.value = true
        viewModel.postListLiveData.value = resource
        viewModel.finalPostLiveData.observeForever(observer)

        Assertions.assertTrue(slot.isCaptured)
        assertEquals(slot.captured.data, listOf(post1))

    }
}