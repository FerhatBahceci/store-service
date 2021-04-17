package store.service.model

import kotlinx.serialization.Serializable
import utility.request.Request

@Serializable
data class DeleteStoreByIdRequest(val id: String?,
                                  override val type: Request.Type = Request.Type.DELETE,
                                  override val status: Int = 204) : Request<DeleteStoreByIdRequest> {

    init {
        validate()
    }

    override fun validate() {}
}
