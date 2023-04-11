package com.example.rafaelanastacioalves.zemoga.ui.postdetailing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.rafaelanastacioalves.zemoga.domain.entities.Comment
import com.example.rafaelanastacioalves.zemoga.domain.entities.Post
import com.example.rafaelanastacioalves.zemoga.domain.entities.Resource
import com.example.rafaelanastacioalves.zemoga.domain.entities.User
import com.example.rafaelanastacioalves.zemoga.domain.interactors.PostCommentsInteractor
import com.example.rafaelanastacioalves.zemoga.domain.interactors.PostDetailsInteractor
import com.example.rafaelanastacioalves.zemoga.domain.interactors.UserDetailInteractor
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class PostDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var userDetailsInteractor: UserDetailInteractor

    @MockK
    lateinit var postDetailsInteractor: PostDetailsInteractor

    @MockK
    lateinit var postCommentsInteractor: PostCommentsInteractor

    private lateinit var postDetailViewModel: PostDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkConstructor(UserDetailInteractor::class)
        postDetailViewModel = PostDetailViewModel(
            userDetailsInteractor,
            postDetailsInteractor,
            postCommentsInteractor
        )

    }

    @Test
    fun `loadData with valid postId Should post Resource loading`() = runTest {
        // Given
        val postId = "1"
        val observer = mockk<Observer<Resource<Post>>>(relaxed = true)

        every {
            postDetailsInteractor.execute(
                any(),
                any(),
                any<(Resource<Post>?) -> Unit>()
            )
        } answers {
            thirdArg<(Resource<Post>?) -> Unit>().invoke(Resource.loading())
        }

        postDetailViewModel.postDetails.observeForever(observer)

        val slot = slot<Resource<Post>>()
        every { observer.onChanged(capture(slot)) } just Runs
        // When
        val result = postDetailViewModel.loadData(postId)

        // Then
        assertTrue(slot.isCaptured)
        assert(slot.captured.status == Resource.Status.LOADING)
        verify { postDetailsInteractor.execute(any(), any(), any<(Resource<Post>?) -> Unit>()) }

    }

    @Test
    fun `load data Should update comments, post and user info and update livedatas`() =
        runTest {


            val postId = "1"
            val post = Post(postId.toLong(), "title", "body", "userId")
            val expectedUser = User("userId", "username", "email")
            val expectedComments = listOf(Comment(1, 2, "name", "email", "body"))

            val postDetailsResource = Resource.success(post)
            val commentsResource = Resource.success(expectedComments)
            val userResource = Resource.success(expectedUser)

            coEvery { postDetailsInteractor.execute(any(), any(), any()) } answers {
                val onResult = thirdArg<(Resource<Post>?) -> Unit>()
                onResult(postDetailsResource)
            }

            coEvery { postCommentsInteractor.execute(any(), any(), any()) } answers {
                val onResult = thirdArg<(Resource<List<Comment>>?) -> Unit>()
                onResult(commentsResource)
            }

            coEvery { userDetailsInteractor.execute(any(), any(), any()) } answers {
                val onResult = thirdArg<(Resource<User>?) -> Unit>()
                onResult(userResource)
            }

            val result = postDetailViewModel.loadData(postId)

            coVerify { postDetailsInteractor.execute(any(), any(), any()) }
            coVerify { postCommentsInteractor.execute(any(), any(), any()) }
            coVerify { userDetailsInteractor.execute(any(), any(), any()) }

            assertEquals(postDetailsResource, result.value)

            assertEquals(expectedUser, postDetailViewModel.userInfo.value?.data)

            assertEquals(expectedComments, postDetailViewModel.postComments.value?.data)
        }
}
