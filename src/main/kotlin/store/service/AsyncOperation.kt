@file:Suppress("UNCHECKED_CAST")

package store.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Objects.nonNull
import java.util.concurrent.ConcurrentMap
import kotlin.coroutines.Continuation
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface AsyncOperation<V> {
    fun performAsync(params: Map<String, String>, callback: (V?) -> Unit)

    @ExperimentalCoroutinesApi
    suspend fun <K, V> AsyncOperation<V>.readFromCacheOrExecuteCall(params: Map<String, K>,
                                                                    cache: ConcurrentMap<K, V>): V =
                suspendCoroutine { continuation ->
                    val readFromCache = cache["key" as K]
                    if (nonNull((readFromCache))) {
                        tryToRetrieveResult(readFromCache, continuation)
                    } else {
                        performAsync(params as Map<String, String>) { value ->
                            tryToRetrieveResult(value, continuation)
                        }
                    }
                }
}

fun <V> tryToRetrieveResult(response: V?, continuation: Continuation<V>) = try {
    continuation.resumeWith(Result.success(response as V))
} catch (e: Exception) {
    continuation.resumeWithException(e)
}

