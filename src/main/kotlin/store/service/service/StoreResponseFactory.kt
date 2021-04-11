package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.*
import store.service.gateway.Store
import store.service.service.StoreMapper.Companion.mapToProtoStore

class StoreResponseFactory {

    companion object {

        @ExperimentalSerializationApi
        fun getStoresResponse(stores: List<Store>? = null, response: Response): GetStoresResponse =
                stores?.map { it.mapToProtoStore() }.let {
                    GetStoresResponse.newBuilder()
                            .setResponse(response)
                            .setStores(Stores.newBuilder().addAllStores(it).build())
                            .build()
                }

        @ExperimentalSerializationApi
        fun getStoreResponse(store: Store? = null, response: Response) =
                GetStoreResponse.newBuilder()
                        .setResponse(response)
                        .setStore(store?.mapToProtoStore())
                        .build()

        @ExperimentalSerializationApi
        fun updatedStoreResponse(update: Store? = null, response: Response) =
                UpdateStoreResponse.newBuilder()
                        .setResponse(response)
                        .setUpdate(update?.mapToProtoStore())
                        .build()

        fun createdStoreResponse(create: Unit? = null, response: Response) =
                CreatedStoreResponse.newBuilder()
                        .setResponse(response)
                        .build()

        fun deletedStoreResponse(create: Unit? = null, response: Response) =
                DeleteStoreResponse.newBuilder()
                        .setResponse(response)
                        .build()

        fun createResponse(status: Int = 200, errorMessage: String? = null) =
                Response.newBuilder()
                        .setStatus(status)
                        .setMessage(errorMessage)
                        .build()
    }
}


