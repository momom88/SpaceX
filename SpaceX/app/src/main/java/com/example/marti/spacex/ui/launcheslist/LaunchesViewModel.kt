package com.example.marti.spacex.ui.launcheslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.marti.spacex.model.SpaceX
import com.example.marti.spacex.ui.repository.Repository
import com.example.marti.spacex.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LaunchesViewModel @Inject constructor(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private lateinit var launchesFromDatabaseBy: LiveData<List<SpaceX>>
    private var launchesApiConnectError: MutableLiveData<String> = MutableLiveData()
    private val disposable = CompositeDisposable()
    private var callApi = false

    fun launchesFromDatabase(launches: String, sortedBy: String):LiveData<List<SpaceX>>{
        launchesFromDatabaseBy = repository.launchesFromDatabaseBy(launches, sortedBy)
        return launchesFromDatabaseBy
    }

    fun launchesApiConnectError(): LiveData<String> {
        return launchesApiConnectError
    }

    fun connectToApi() {
        Log.i("test", "connect to api")
        if (!callApi) {
            callApi = true
            Log.i("test", "connect to api change to true")
            disposable.add(
                repository.getLaunches()
                    .compose(schedulerProvider.getSchedulersForFlowable())
                    .subscribeBy(
                        onError =
                        {
                            Log.i("test", "connect to api error: $it")
                            launchesApiConnectError.postValue(it.toString())
                        }
                    ))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}

