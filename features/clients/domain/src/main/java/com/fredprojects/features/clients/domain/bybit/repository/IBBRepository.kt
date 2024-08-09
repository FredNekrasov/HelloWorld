package com.fredprojects.features.clients.domain.bybit.repository

import com.fredprojects.features.clients.domain.bybit.models.BBInfo
import com.fredprojects.features.clients.domain.utils.ConnectionStatus
import kotlinx.coroutines.flow.StateFlow

/**
 * IBBRepository represents the interface for the bybit repository.
 * The bybit repository is used to get data from the server and save it in the database.
 */
interface IBBRepository {
    /**
     * getData is used to get data from the server or get data from the cache.
     *
     * @return the flow of data received from the server
     */
    suspend fun getData(): StateFlow<ConnectionStatus<BBInfo>>
}