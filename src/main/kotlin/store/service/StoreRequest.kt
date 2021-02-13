package store.service

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import utility.request.Request
//TODO, once more knowledge about the Store requirements are gathered, validate() method should be implemented properly rather than only null asserting.

@Serializable
class GetAllStoresRequest  : Request<GetAllStoresRequest>() {
    override fun validate() {
    }
}

@Serializable
data class GetStoreByNameRequest(val name: String?) : Request<GetStoreByNameRequest>() {
    override fun validate() {
    }
}

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val type: Store.Type?) : Request<GetStoreByTypeRequest>() {
    override fun validate() {
    }
}

@ExperimentalSerializationApi
@Serializable
data class CreateStoreRequest(val store: Store?) : Request<CreateStoreRequest>() {
    override fun validate() {
    }
}

@Serializable
data class DeleteStoreByIdRequest(val id: String?) : Request<DeleteStoreByIdRequest>() {
    override fun validate() {
    }
}

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String?, val store: Store?) : Request<UpdateStoreRequest>() {
    override fun validate() {
    }
}
