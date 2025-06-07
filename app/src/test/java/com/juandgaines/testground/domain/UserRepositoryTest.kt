package com.juandgaines.testground.domain

import com.google.common.truth.Truth
import com.juandgaines.testground.data.UserRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var userApi: UserFakeApi

    @Before
    fun setUp(){
        this.userApi = UserFakeApi()
        this.userRepository = UserRepositoryImpl(userApi)
    }

    @Test
    fun givenValidUserId_whenGetProfileWithFakeApi_theReturnsProfile() = runTest {
        //Arrange
        val userId = "1"

        //Act
        var profileResult = userRepository.getProfile(userId)

        //Assertions
        Truth.assertThat(profileResult.isSuccess).isTrue()
        Truth.assertThat(profileResult.getOrThrow().user.id).isEqualTo(userId)

        //Ummm, not sure this assertion is too "white-boxed"
        val expectedPlaces = userApi.getPlaces().filter { it.id == userId }
        Truth.assertThat(profileResult.getOrThrow().places).isEqualTo(expectedPlaces)

    }

}