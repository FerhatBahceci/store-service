package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.*
import store.service.gateway.Store


@ExperimentalSerializationApi
fun getStoresResponse(stores: List<Store>? = null, response: Response? = null): GetStoresResponse =
    stores?.map { it.mapToProtoStore() }.let {
        GetStoresResponse.newBuilder().setSuccess(
            GetStoresResponse.GetStoresSuccessfulResponse.newBuilder().setResponse(response)
                .setStores(
                    Stores.newBuilder()
                        .addAllStores(it)
                        .build()
                )
                .build()
        ).build()
    }

@ExperimentalSerializationApi
fun getStoreResponse(store: Store? = null, response: Response? = null): GetStoreResponse =
    GetStoreResponse.newBuilder()
        .setSuccess(
            GetStoreResponse.GetStoreSuccessfulResponse
                .newBuilder()
                .setStore(store?.mapToProtoStore())
                .build()
        )
        .build()

@ExperimentalSerializationApi
fun updatedStoreResponse(update: Store? = null, response: Response? = null): UpdateStoreResponse =
    UpdateStoreResponse.newBuilder()
        .setSuccess(
            UpdateStoreResponse.UpdateStoreSuccessfulResponse
                .newBuilder()
                .setUpdate(update?.mapToProtoStore())
                .build()
        )
        .build()

fun createdStoreResponse(create: Unit? = null, response: Response? = null): CreatedStoreResponse =
    CreatedStoreResponse.newBuilder()
        .setSuccess(response)
        .build()

fun deletedStoreResponse(create: Unit? = null, response: Response? = null): DeleteStoreResponse =
    DeleteStoreResponse.newBuilder()
        .setSuccess(response)
        .build()
