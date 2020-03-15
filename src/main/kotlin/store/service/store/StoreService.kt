package store.viewer.service

import kotlinx.coroutines.*
import store.viewer.StoreServiceImplBase
import store.viewer.Stores
import store.viewer.repositories.StoreDao
import kotlin.coroutines.Continuation
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StoreService(private val storeDao: StoreDao) : StoreServiceImplBase(
        coroutineContext = newFixedThreadPoolContext(4, "grpc-server-pool")) {

    override suspend fun geAllStores(request: store.viewer.GetAllStoresRequest): Stores =
            Dispatchers.IO {
                getStores()
            }

    suspend fun getStores(): Stores = suspendCoroutine { continuation ->
        val stores = mapToStores(storeDao.findAll())
        tryToRetrieveResult(stores, continuation)
    }
}

private fun <T> tryToRetrieveResult(response: T, continuation: Continuation<T>) {
    return try {
        continuation.resumeWith(Result.success(response))
    } catch (e: Exception) {
        continuation.resumeWithException(e)
    }
}



