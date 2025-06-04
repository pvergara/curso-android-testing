package com.juandgaines.testground.domain

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class ExperienceCalculatorTest {
    private lateinit var experienceCalculator: ExperienceCalculator

    @Before
    fun setUp() {
        this.experienceCalculator = ExperienceCalculator()
    }

    @Test
    fun givenTouristStop_whenCalculatorExperience_thenReturn5Points() {
        val touristSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 40.5, longitude = -73.5)
        )
        val result = this.experienceCalculator.calculateExperience(listOf(touristSpot))

        Truth.assertThat(result).isEqualTo(5)
    }

    @Test
    fun givenNoLocation_whenCalculatorExperience_thenReturn0Points() {
        val result = this.experienceCalculator.calculateExperience(emptyList())

        Truth.assertThat(result).isEqualTo(0)
    }

    @Test
    fun givenTouristNaturalAndCulturalStops_whenCalculatorExperience_thenReturn9Points() {
        val touristSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 40.5, longitude = -73.5)
        )
        val culturalSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 38.5, longitude = -77.5)
        )
        val naturalSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 36.5, longitude = -118.5)
        )
        val result = this.experienceCalculator.calculateExperience(
            listOf(
                touristSpot,
                culturalSpot,
                naturalSpot
            )
        )

        Truth.assertThat(result).isEqualTo(9)
    }

    @Test
    fun givenTwoTouristSpotsAndOneNaturalAndCulturalStops_whenCalculatorExperience_thenReturn14Points() {
        val touristSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 40.5, longitude = -73.5)
        )
        val anotherTouristSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 40.5, longitude = -73.5)
        )
        val culturalSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 38.5, longitude = -77.5)
        )
        val naturalSpot = Place(
            id = "1",
            name = "Time square",
            coordinates = Coordinates(latitude = 36.5, longitude = -118.5)
        )
        val result = this.experienceCalculator.calculateExperience(
            listOf(
                anotherTouristSpot,
                touristSpot,
                culturalSpot,
                naturalSpot
            )
        )

        Truth.assertThat(result).isEqualTo(14)
    }

}