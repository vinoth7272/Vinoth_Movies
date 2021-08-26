package com.example.movierating

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movierating.data.model.MoviePage
import com.example.movierating.data.network.Resource
import com.example.movierating.data.repository.AppRemoteRepository
import com.example.movierating.viewmodel.MovieViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var remoteRepository: AppRemoteRepository

    private lateinit var apiKey: String

    private lateinit var movieViewModel: MovieViewModel

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<MoviePage>>

    @Before
    fun setUp() {
        apiKey = BuildConfig.API_KEY
        remoteRepository = mock(AppRemoteRepository::class.java)
        movieViewModel= MovieViewModel(remoteRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMovieList_whenFetch_shouldReturnSuccess() {
        runBlockingTest {
            movieViewModel.getMovies().observeForever(apiUsersObserver)
            `when`(remoteRepository.getMovieList(apiKey, "Star")).thenAnswer {
                Resource.success(MoviePage())
            }
            movieViewModel.fetchMovies(apiKey, "Star")
            assertNotNull(movieViewModel.getMovies().value)
            assertEquals(Resource.success(MoviePage()), movieViewModel.getMovies().value)
        }
    }
    @Test
    fun getMovieList_whenFetch_shouldLoading() {
        testCoroutineRule.runBlockingTest {
            movieViewModel.getMovies().observeForever(apiUsersObserver)
            movieViewModel.fetchMovies(apiKey,"Star")
            verify(apiUsersObserver).onChanged(Resource.loading())
        }
    }

    @Test
    fun getMovieList_whenFetch_shouldReturnError() {
        val exception = mock(HttpException::class.java)
        testCoroutineRule.runBlockingTest {
            movieViewModel.getMovies().observeForever(apiUsersObserver)
            `when`(remoteRepository.getMovieList(apiKey, "Star")).thenAnswer {
                Resource.error(exception.message(),null)
            }
            movieViewModel.fetchMovies(apiKey, "Star")
            assertNotNull(movieViewModel.getMovies().value)
            assertEquals(Resource.error(exception.message(),null), movieViewModel.getMovies().value)
        }
    }

    @After
    fun tearDown() {
        movieViewModel.getMovies().removeObserver(apiUsersObserver)
    }
}