package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.*
import store.service.gateway.Store

@ExperimentalSerializationApi
fun createGetStoresResponse(stores: List<Store>? = null, response: Response? = null): GetStoresResponse =
    stores?.map { it.mapToProtoStore() }.let {
        GetStoresResponse.newBuilder()
            .setStores(
                Stores.newBuilder()
                    .addAllStores(stores?.map { it.mapToProtoStore() })
                    .build()
            )
            .setResponse(response)
            .build()
    }

@ExperimentalSerializationApi
fun createGetStoreResponse(store: Store? = null, response: Response? = null): GetStoreResponse =
    GetStoreResponse.newBuilder()
        .setResponse(response)
        .setStore(store?.mapToProtoStore())
        .build()

@ExperimentalSerializationApi
fun createUpdatedStoreResponse(update: Store? = null, response: Response? = null): UpdateStoreResponse =
    UpdateStoreResponse.newBuilder()
        .setUpdate(update?.mapToProtoStore())
        .setResponse(response)
        .build()

fun createCreatedStoreResponse(create: Unit? = null, response: Response? = null): CreatedStoreResponse =
    CreatedStoreResponse.newBuilder()
        .setResponse(response)
        .build()

fun createDeletedStoreResponse(create: Unit? = null, response: Response? = null): DeleteStoreResponse =
    DeleteStoreResponse.newBuilder()
        .setResponse(response)
        .build()
