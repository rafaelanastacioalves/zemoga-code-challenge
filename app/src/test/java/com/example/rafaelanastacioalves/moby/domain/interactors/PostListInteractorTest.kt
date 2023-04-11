
import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.rafaelanastacioalves.moby.application.TestApp
import com.example.rafaelanastacioalves.moby.domain.entities.Post
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import com.example.rafaelanastacioalves.moby.domain.interactors.PostListInteractor
import com.example.rafaelanastacioalves.moby.repository.AppRepository
import com.example.rafaelanastacioalves.moby.repository.database.AppDataBase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config( application = TestApp::class)
@ExperimentalCoroutinesApi
class PostListInteractorTest {

    lateinit var appDataBase: AppDataBase
    lateinit var repositoryMock: AppRepository
    @Before
    fun setup() {
        unmockkAll()
        val context  = ApplicationProvider.getApplicationContext<Context>()
        mockkStatic(AppDataBase::class)
        AppDataBase.setupAtApplicationStartup(context)
        appDataBase = mockk()
        repositoryMock = mockk()
    }

    @After
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun testPostListInteractorReturnsPostList() = runBlocking {
        // Given
        val postListInteractor = PostListInteractor(repositoryMock)
        val posts = listOf(
            Post(1L, "1", "title", "body"),
            Post(2L, "2", "title2", "body2")
        )
        coEvery { repositoryMock.getPosts() } returns Resource.success(posts)

        // When
        val result = postListInteractor.run(PostListInteractor.RequestValues(null))

        // Then
        assertEquals(posts, result.data)
    }

    @Test
    fun testPostListInteractorFiltersAndSortsPosts() = runBlocking {
        // Given
        val postListInteractor = PostListInteractor(repositoryMock)
        val deletedPost = Post(3L, "3", "deleted post", "body", deleted = true)
        val favoritePost = Post(1L, "1", "favorite post", "body", favorited = true)
        val posts = listOf(
            deletedPost,
            favoritePost,
            Post(4L, "4", "fourth post", "body4"),
            Post(5L, "5", "fifth post", "body5"),
            Post(6L, "6", "sixth post", "body6"),
            Post(7L, "7", "seventh post", "body7"),
            Post(8L, "8", "eighth post", "body8")
        )
        val filteredPosts = listOf(
            favoritePost,
            Post(4L, "4", "fourth post", "body4"),
            Post(5L, "5", "fifth post", "body5"),
            Post(6L, "6", "sixth post", "body6"),
            Post(7L, "7", "seventh post", "body7"),
            Post(8L, "8", "eighth post", "body8"),
        )

        coEvery { repositoryMock.getPosts() } returns Resource.success(posts)
        coEvery { repositoryMock.savePost(deletedPost) } just Runs
        coEvery { repositoryMock.savePost(favoritePost) } just Runs

        // When
        val filteredResult = postListInteractor.run(PostListInteractor.RequestValues(null))

        // Then
        assertEquals(filteredPosts, filteredResult.data)
    }

}
