package com.example.marti.spacex.ui.repository

import androidx.lifecycle.LiveData
import com.example.marti.spacex.data.database.SpaceXDao
import com.example.marti.spacex.data.networking.ApiInterface
import com.example.marti.spacex.model.SpaceX
import com.example.marti.spacex.utils.LAUNCHES_PAST
import com.example.marti.spacex.utils.LAUNCHES_UPCOMING
import com.example.marti.spacex.utils.SORTING_BY_ROCKET_ID
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val spaceXDao: SpaceXDao
) {

    private lateinit var launchesFromDatabase: LiveData<List<SpaceX>>

    fun getLaunches(): Flowable<List<SpaceX>> {
       val launchesPast= apiInterface.getLaunches(LAUNCHES_PAST)
            .doOnNext {
                for (item in it) {
                    item.launch = LAUNCHES_PAST
                    spaceXDao.insertLaunches(item)
                }
            }
        val launchesUpcoming = apiInterface.getLaunches(LAUNCHES_UPCOMING)
            .doOnNext {
                for (item in it) {
                    item.launch = LAUNCHES_UPCOMING
                    spaceXDao.insertLaunches(item)
                }
            }
        return Flowable.concat(launchesPast,launchesUpcoming)
    }

    fun getLaunchById(launchId: Int): LiveData<SpaceX> {
        return spaceXDao.getLaunchById(launchId)
    }

    fun launchesFromDatabaseBy(launches: String, sortedBy: String): LiveData<List<SpaceX>> {
        return if (sortedBy == SORTING_BY_ROCKET_ID) {
            launchesFromDatabase = spaceXDao.queryLaunchesByRocketId(launches)
            launchesFromDatabase
        } else {
            launchesFromDatabase = spaceXDao.queryLaunchesByLaunchYear(launches)
            launchesFromDatabase
        }
    }
}