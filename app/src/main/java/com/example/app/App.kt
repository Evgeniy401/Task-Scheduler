package com.example.app

import android.app.Application
import com.example.data.network.NetworkConnectivityObserver
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.repository.StatisticRepository

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var statisticRepository: StatisticRepository
    @Inject lateinit var networkConnectivityObserver: NetworkConnectivityObserver

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            statisticRepository.initializeStatistics()
        }

        networkConnectivityObserver.startObserving()
    }
}
