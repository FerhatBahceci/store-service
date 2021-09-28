package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import store.service.gateway.Store
import store.service.gateway.validateStore
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class CreateStoreRequest(val store: Store,
                              override val type: Request.Type = Request.Type.POST) : Request<CreateStoreRequest> {
    init {
        validate()
    }

    override fun validate() {
        store.validateStore()
    }
}

@Serializable
data class DeleteStoreByIdRequest(val id: String,
                                  override val type: Request.Type = Request.Type.DELETE) : Request<DeleteStoreByIdRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@Serializable
data class GetAllStoresRequest(override val type: Request.Type = Request.Type.GET) : Request<GetAllStoresRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@Serializable
data class GetStoreByNameRequest(val name: String,
                                 override val type: Request.Type = Request.Type.GET) : Request<GetStoreByNameRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val storeType: Store.Type,
                                 override val type: Request.Type = Request.Type.GET) : Request<GetStoreByTypeRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String,
                              val store: Store,
                              override val type: Request.Type = Request.Type.PUT) : Request<UpdateStoreRequest> {
    init {
        validate()
    }

    override fun validate() {
    }
}
