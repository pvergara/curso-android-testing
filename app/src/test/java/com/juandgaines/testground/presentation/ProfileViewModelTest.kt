package com.juandgaines.testground.presentation

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    private lateinit var viewModel : ProfileViewModel
    private lateinit var repository : UserRepositoryFake
    private val testDispatcher = UnconfinedTestDispatcher()
    @Before
    fun setUp(){
        Dispatchers.setMain(this.testDispatcher)

        repository = UserRepositoryFake()
        
        val savedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "userId" to repository.profileToReturn.user.id
            )
        )
        
        this.viewModel = ProfileViewModel(
            repository,
            savedStateHandle
        )
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun givenValidUserId_whenLoadProfile_thenProfileIsLoaded() = runTest {
        //Act
        viewModel.loadProfile()

        //Assert
        Truth.assertThat(viewModel.state.value.profile).isEqualTo(repository.profileToReturn)
        Truth.assertThat(viewModel.state.value.isLoading).isFalse()
    }
}