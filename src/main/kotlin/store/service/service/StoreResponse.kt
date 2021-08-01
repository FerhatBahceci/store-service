package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.*
import store.service.Store

@ExperimentalSerializationApi
fun getStoresResponse(stores: List<Store>? = null, response: Response): GetStoresResponse =
        stores?.map { it.mapToProtoStore() }.let {
            GetStoresResponse.newBuilder()
                    .setResponse(response)
                    .setStores(Stores.newBuilder().addAllStores(it).build())
                    .build()
        }

@ExperimentalSerializationApi
fun getStoreResponse(store: Store? = null, response: Response): GetStoreResponse =
        GetStoreResponse.newBuilder()
                .setResponse(response)
                .setStore(store?.mapToProtoStore())
                .build()

@ExperimentalSerializationApi
fun updatedStoreResponse(update: Store? = null, response: Response): UpdateStoreResponse =
        UpdateStoreResponse.newBuilder()
                .setResponse(response)
                .setUpdate(update?.mapToProtoStore())
                .build()

fun createdStoreResponse(create: Unit? = null, response: Response): CreatedStoreResponse =
        CreatedStoreResponse.newBuilder()
                .setResponse(response)
                .build()

fun deletedStoreResponse(create: Unit? = null, response: Response): DeleteStoreResponse =
        DeleteStoreResponse.newBuilder()
                .setResponse(response)
                .build()

