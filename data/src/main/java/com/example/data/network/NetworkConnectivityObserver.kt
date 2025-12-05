package com.example.data.network

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.domain.usecase.SyncTasksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectivityObserver @Inject constructor(
    private val networkManager: NetworkManager,
    private val syncTasksUseCase: SyncTasksUseCase
) {
    private var observationJob: Job? = null

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun startObserving() {
        stopObserving()
        observationJob = networkManager.observeNetworkChanges()
            .onEach { isConnected ->
                if (isConnected) {
                    try {
                        syncTasksUseCase()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun stopObserving() {
        observationJob?.cancel()
        observationJob = null
    }
}