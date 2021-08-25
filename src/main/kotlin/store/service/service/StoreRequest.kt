package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import store.service.Store
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class CreateStoreRequest(val store: Store) : Request<CreateStoreRequest> {
    init {
        validate()
    }

    override fun validate() {
        store.validateStore()
    }
}

@Serializable
data class DeleteStoreByIdRequest(val id: String) : Request<DeleteStoreByIdRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@Serializable
class GetAllStoresRequest : Request<GetAllStoresRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@Serializable
data class GetStoreByNameRequest(val name: String) : Request<GetStoreByNameRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val type: Store.Type) : Request<GetStoreByTypeRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String,
                              val store: Store) : Request<UpdateStoreRequest> {
    init {
        validate()
    }

    override fun validate() {
    }
}
