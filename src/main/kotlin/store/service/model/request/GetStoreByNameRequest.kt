package store.service.model.request

import kotlinx.serialization.Serializable
import utility.request.Request

@Serializable
data class GetStoreByNameRequest(val name: String?,
                                 override val type: Request.Type = Request.Type.GET,
                                 override val status: Int = 200) : Request<GetStoreByNameRequest> {
    init {
        validate()
    }

    override fun validate() {}
}
