package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.*
import store.service.gateway.Store
import store.service.service.StoreMapper.Companion.mapToProtoStore

class StoreResponse {

    companion object {

        @ExperimentalSerializationApi
        fun getStoresResponse(stores: List<Store>? = null, status: Int, errorMessage: String = ""): GetStoresResponse =
                stores?.map { it.mapToProtoStore() }.let {
                    GetStoresResponse.newBuilder()
                            .setResponse(createResponse(status, errorMessage))
                            .setStores(Stores.newBuilder().addAllStores(it).build())
                            .build()
                }

        @ExperimentalSerializationApi
        fun getStoreResponse(store: Store, status: Int, errorMessage: String = "") =
                GetStoreResponse.newBuilder()
                        .setResponse(createResponse(status, errorMessage))
                        .setStore(store.mapToProtoStore())
                        .build()

        @ExperimentalSerializationApi
        fun updatedStoreResponse(update: Store, status: Int, errorMessage: String = "") =
                UpdateStoreResponse.newBuilder()
                        .setResponse(createResponse(status, errorMessage))
                        .setUpdate(update.mapToProtoStore())
                        .build()

        fun createdStoreResponse(create: Unit, status: Int, errorMessage: String = "") =
                CreatedStoreResponse.newBuilder()
                        .setResponse(createResponse(status, errorMessage))
                        .build()

        fun deletedStoreResponse(create: Unit, status: Int, errorMessage: String = "") =
                DeleteStoreResponse.newBuilder()
                        .setResponse(createResponse(status, errorMessage))
                        .build()

        fun createResponse(status: Int = 200, errorMessage: String) =
                Response.newBuilder()
                        .setStatus(status)
                        .setMessage(errorMessage)
                        .build()
    }

}


