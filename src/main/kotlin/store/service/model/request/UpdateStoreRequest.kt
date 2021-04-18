package store.service.model.request

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import store.service.model.Store
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String?,
                              val store: Store,
                              override val type: Request.Type = Request.Type.PUT,
                              override val expectedStatus: Int = 204) : Request<UpdateStoreRequest> {
    init {
        validate()
    }

    override fun validate() {
    }
}
