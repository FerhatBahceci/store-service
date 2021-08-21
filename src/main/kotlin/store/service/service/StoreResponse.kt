package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.*
import store.service.Store

@ExperimentalSerializationApi
fun getStoresResponse(stores: List<Store>? = null): GetStoresResponse =
        stores?.map { it.mapToProtoStore() }.let {
            GetStoresResponse.newBuilder()
                    .setStores(Stores.newBuilder().addAllStores(it).build())
                    .build()
        }

@ExperimentalSerializationApi
fun getStoreResponse(store: Store? = null): GetStoreResponse =
        GetStoreResponse.newBuilder()
                .setStore(store?.mapToProtoStore())
                .build()

@ExperimentalSerializationApi
fun updatedStoreResponse(update: Store? = null): UpdateStoreResponse =
        UpdateStoreResponse.newBuilder()
                .setUpdate(update?.mapToProtoStore())
                .build()

fun createdStoreResponse(create: Unit? = null): CreatedStoreResponse =
        CreatedStoreResponse.newBuilder()
                .build()

fun deletedStoreResponse(create: Unit? = null): DeleteStoreResponse =
        DeleteStoreResponse.newBuilder()
                .build()

