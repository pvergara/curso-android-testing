package com.juandgaines.testground.domain

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CoroutineExampleTest {

    private suspend fun delayOperation(): Int {
        delay(1000)
        return 42
    }

    private fun numberFlows() = flow {
        emit(1)
        delay(1000)
        emit(2)
        delay(1000)
        emit(3)
    }

    @Test
    fun givenDelayOperation_whenUsingRunBlocking_thenWaitsForRealDelay(): Unit = runBlocking {
        //Act
        val result = delayOperation()

        //Assertions
        Truth.assertThat(result).isEqualTo(42)
    }

    @Test
    fun givenDelayOperation_whenUsingRunTest_thenItDoesNotWaitForRealDelay(): Unit = runTest {
        //Act
        val result = delayOperation()

        //Assertions
        Truth.assertThat(result).isEqualTo(42)
    }

    @Test
    fun flowWithoutTimeControl() = runTest {
        val numbers = mutableListOf<Int>()

        numberFlows().collect {
            numbers.add(it)
        }

        Truth.assertThat(numbers).isEqualTo(listOf(1, 2, 3))
    }

    @Test
    fun flowWithTimeControlViaBlocking() = runBlocking {
        val numbers = mutableListOf<Int>()

        numberFlows().collect {
            numbers.add(it)
        }

        Truth.assertThat(numbers).isEqualTo(listOf(1, 2, 3))
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun flowWithTimeControl() = runTest {
        val numbers = mutableListOf<Int>()

        launch {
            numberFlows().collect {
                numbers.add(it)
            }
        }

        advanceTimeBy(500)
        Truth.assertThat(numbers).isEqualTo(listOf(1))

        advanceTimeBy(600)
        Truth.assertThat(numbers).isEqualTo(listOf(1,2))

        advanceTimeBy(1000)
        Truth.assertThat(numbers).isEqualTo(listOf(1,2,3))

    }
}