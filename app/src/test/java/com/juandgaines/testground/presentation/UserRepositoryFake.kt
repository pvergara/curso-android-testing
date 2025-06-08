package com.juandgaines.testground.presentation

import com.juandgaines.testground.domain.Coordinates
import com.juandgaines.testground.domain.Place
import com.juandgaines.testground.domain.Profile
import com.juandgaines.testground.domain.User
import com.juandgaines.testground.domain.UserRepository
import java.util.UUID

class UserRepositoryFake: UserRepository {
    var profileToReturn: Profile = this.generateProfile()
    var errorToReturn: Exception? = null

    override suspend fun getProfile(userId: String): Result<Profile> {
        return if (this.errorToReturn!=null){
            Result.failure(this.errorToReturn!!)
        }else{
            Result.success(this.profileToReturn)
        }
    }

    override suspend fun getPlaces(userId: String): Result<List<Place>> {
        return Result.success(this.profileToReturn.places)
    }

    private fun generateUser(): User{
        return User(
            id= UUID.randomUUID().toString(),
            username = "test-user"
        )
    }

    private fun generatePlace(): Place{
        return Place(
            id= UUID.randomUUID().toString(),
            name="",
            coordinates = Coordinates(1.0,1.0)
        )
    }

    private fun generateProfile(): Profile{
        return Profile(
            user = generateUser(),
            places = listOf(generatePlace())
        )
    }

}