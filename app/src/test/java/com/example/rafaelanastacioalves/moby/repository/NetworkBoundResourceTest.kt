package com.example.rafaelanastacioalves.moby.repository

import android.net.http.HttpResponseCache
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.rafaelanastacioalves.moby.domain.entities.Resource
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class NetworkBoundResourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var networkBoundResource: NetworkBoundResource<String, String>
    private val fetchFromHttpMock: suspend () -> String? = mockk()
    private val getFromDBMock: suspend () -> String? = mockk()
    private val saveIntoDBMock:  (String?) -> Unit = mockk()

    @Before
    fun setUp() {
        networkBoundResource = object : NetworkBoundResource<String, String>() {
            override suspend fun fecthFromHttp(): String? {
                return fetchFromHttpMock()
            }

            override suspend fun getFromDB(): String? {
                return getFromDBMock()
            }

            override fun saveIntoDB(resultData: String?) {
                saveIntoDBMock(resultData)
            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testFetchFromNetwork_Success() = runBlocking {
        // Given
        val expectedData = "Test Data"
        coEvery { fetchFromHttpMock() } returns expectedData

        // When
        val result = networkBoundResource.fromHttpOnly()
        // Then
        assertEquals(Resource.Status.SUCCESS, result.status)
        assertEquals(expectedData, result.data)
        assertNull(result.message)
        coVerify (exactly = 1) { fetchFromHttpMock() }
        coVerify (exactly = 0) { getFromDBMock() }
        verify(exactly = 0) { saveIntoDBMock(any()) }
    }

    @Test
    fun testFetchFromNetwork_Exception() = runBlocking {
        // Given
        val exception = Exception("Test Exception")
        coEvery { fetchFromHttpMock() } throws exception

        // When
        val result = networkBoundResource.fromHttpOnly()

        // Then
        assertEquals(Resource.Status.GENERIC_ERROR, result.status)
        assertNull(result.data)
        assertNotNull(result.message)
        assertEquals("Test Exception", result.message)
        coVerify(exactly = 1) { fetchFromHttpMock() }
        coVerify(exactly = 0) { getFromDBMock() }
        verify(exactly = 0) { saveIntoDBMock(any()) }
    }


}