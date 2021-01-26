package store.service

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable;
import utility.Request

//TODO, once more knowledge about the Store requirements are gathered, validate() method should be implemented properly rather than only null asserting.

@Serializable
class GetAllStoresRequest : Request<GetAllStoresRequest> {
    override fun validate() {
        TODO("Not yet implemented")
    }
}

@Serializable
data class GetStoreByIdRequest(val id: String?) : Request<GetStoreByIdRequest> {
    override fun validate() {
        checkNotNull(id, { "Id cannot be null" })
    }
}

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val type: Store.Type?) : Request<GetStoreByTypeRequest> {
    override fun validate() {
        checkNotNull(type, { "Type cannot be null" })
    }
}

@ExperimentalSerializationApi
@Serializable
data class CreateStoreRequest(val store: Store?) : Request<CreateStoreRequest> {
    override fun validate() {
        checkNotNull(store, { "Store cannot be null" })
    }
}

@Serializable
data class DeleteStoreByIdRequest(val id: String?) : Request<DeleteStoreByIdRequest> {
    override fun validate() {
        checkNotNull(id, { "Id cannot be null" })
    }
}

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String?, val store: Store?) : Request<UpdateStoreRequest> {
    override fun validate() {
        checkNotNull(id, { "Id cannot be null" })
        checkNotNull(store, { "Store cannot be null" })
    }
}
