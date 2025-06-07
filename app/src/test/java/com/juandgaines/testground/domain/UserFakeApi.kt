package com.juandgaines.testground.domain

import com.juandgaines.testground.data.UserApi
import java.util.UUID

class UserFakeApi : UserApi {
    private val users = (1..10).map {
        User(
            id = it.toString(),
            username = "User$it"
        )
    }

    private val places = (1..10).map {
        Place(
            id = UUID.randomUUID().toString(),
            name = "Place$it",
            coordinates = Coordinates(
                latitude = it.toDouble(),
                longitude = it.toDouble()
            )
        )
    }

    fun getPlaces(): List<Place>{
        return this.places;
    }

    override suspend fun getUser(userId: String): User {
        return this.users.find { it.id == userId }?: throw Exception("User not found")
    }

    override suspend fun getPlaces(placeId: String): List<Place> {
        return this.places.filter { it.id == placeId }
    }

    override suspend fun getProfile(userId: String): Profile {
        return Profile(
            user = getUser(userId),
            places = getPlaces(userId)
        )
    }
}