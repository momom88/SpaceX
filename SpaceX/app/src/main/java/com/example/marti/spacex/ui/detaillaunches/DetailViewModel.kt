package com.example.marti.spacex.ui.detaillaunches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.marti.spacex.model.SpaceX
import com.example.marti.spacex.ui.repository.Repository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    lateinit var launchDetail: LiveData<SpaceX>

    fun getLaunch(launchId: Int): LiveData<SpaceX> {
        launchDetail = repository.getLaunchById(launchId)
        return launchDetail
    }
}