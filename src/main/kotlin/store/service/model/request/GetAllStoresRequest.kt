package store.service.model

import kotlinx.serialization.Serializable
import utility.request.Request

//TODO, once more knowledge about the Store requirements are gathered, validate() method should be implemented properly rather than only null asserting.

@Serializable
data class GetAllStoresRequest(override val type: Request.Type = Request.Type.GET,
                               override val status: Int = 200) : Request<GetAllStoresRequest> {

    init {
        validate()
    }

    override fun validate() {}
}
