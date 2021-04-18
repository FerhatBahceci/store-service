package store.service.model.request

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import store.service.model.Store
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val storeType: Store.Type?,
                                 override val type: Request.Type = Request.Type.GET,
                                 override val expectedStatus: Int = 200) : Request<GetStoreByTypeRequest> {
    init {
        validate()
    }

    override fun validate() {}
}
