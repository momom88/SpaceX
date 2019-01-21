package com.example.marti.spacex.data.networking

import com.example.marti.spacex.model.SpaceX
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("launches/{launches}")
    fun getLaunches(@Path("launches") launches: String?): Flowable<List<SpaceX>>
}