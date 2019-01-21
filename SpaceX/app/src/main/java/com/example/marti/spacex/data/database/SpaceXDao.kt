package com.example.marti.spacex.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marti.spacex.model.SpaceX
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface SpaceXDao {

    @Query("SELECT * FROM launches WHERE launch LIKE :launches")
    fun queryLaunches(launches:String): Flowable<List<SpaceX>>

    @Query("SELECT * FROM launches WHERE launch LIKE :launches ORDER BY rocket_id")
    fun queryLaunchesByRocketId(launches:String): LiveData<List<SpaceX>>

    @Query("SELECT * FROM launches WHERE launch LIKE :launches ORDER BY launch_year")
    fun queryLaunchesByLaunchYear(launches:String): LiveData<List<SpaceX>>

    @Query("SELECT * FROM LAUNCHES WHERE flight_number LIKE :launchId")
    fun getLaunchById(launchId: Int): LiveData<SpaceX>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLaunches(spaceX: SpaceX)
}