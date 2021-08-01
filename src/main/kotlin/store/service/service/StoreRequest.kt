package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import store.service.Store
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class CreateStoreRequest(val store: Store,
                              override val type: Request.Type = Request.Type.POST,
                              override val expectedStatus: Int = 201) : Request<CreateStoreRequest> {
    init {
        validate()
    }

    override fun validate() {
        store.validateStore()
    }
}

@Serializable
data class DeleteStoreByIdRequest(val id: String,
                                  override val type: Request.Type = Request.Type.DELETE,
                                  override val expectedStatus: Int = 204) : Request<DeleteStoreByIdRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@Serializable
data class GetAllStoresRequest(override val type: Request.Type = Request.Type.GET,
                               override val expectedStatus: Int = 200) : Request<GetAllStoresRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@Serializable
data class GetStoreByNameRequest(val name: String,
                                 override val type: Request.Type = Request.Type.GET,
                                 override val expectedStatus: Int = 200) : Request<GetStoreByNameRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val storeType: Store.Type,
                                 override val type: Request.Type = Request.Type.GET,
                                 override val expectedStatus: Int = 200) : Request<GetStoreByTypeRequest> {
    init {
        validate()
    }

    override fun validate() {}
}

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String,
                              val store: Store,
                              override val type: Request.Type = Request.Type.PUT,
                              override val expectedStatus: Int = 204) : Request<UpdateStoreRequest> {
    init {
        validate()
    }

    override fun validate() {
    }
}
