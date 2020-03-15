package store.viewer

import io.rouz.grpc.*
import store.viewer.StoreServiceGrpc.StoreServiceStub

import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.suspendCoroutine

import io.grpc.Metadata
import io.grpc.stub.MetadataUtils

/**
 * Kotlin extension functions for [store.viewer.StoreServiceGrpc.StoreServiceStub]
 *
 * Generated by gRPC Kotlin generator
 * Source: api.proto
 */




suspend inline fun StoreServiceStub.geAllStores(request: store.viewer.GetAllStoresRequest): store.viewer.Stores {
    return suspendCoroutine {
        geAllStores(request, ContinuationStreamObserver(it))
    }
}



suspend inline fun StoreServiceStub.getStore(request: store.viewer.GetStoreRequest): store.viewer.Store {
    return suspendCoroutine {
        getStore(request, ContinuationStreamObserver(it))
    }
}

/**
* Adds new binary header and returns the client
*/
fun StoreServiceStub.addBinaryHeader(
    header: String,
    bytes: ByteArray
): StoreServiceStub {
    val headers = Metadata()
    val key = Metadata.Key.of(header, Metadata.BINARY_BYTE_MARSHALLER)
    headers.put(key, bytes)

    return MetadataUtils.attachHeaders(this, headers)
}
