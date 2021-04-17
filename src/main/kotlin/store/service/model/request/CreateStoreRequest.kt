package store.service.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class CreateStoreRequest(val store: Store,
                              override val type: Request.Type = Request.Type.POST,
                              override val status: Int = 201) : Request<CreateStoreRequest> {

    init {
        validate()
    }

    override fun validate() {
        store.validateStore()
    }
}
