package store.service.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import store.service.model.request.validators.OpeningHoursValidator.Companion.validateHours
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String?,
                              val store: Store,
                              override val type: Request.Type = Request.Type.PUT,
                              override val status: Int = 204) : Request<UpdateStoreRequest> {

    init {
        validate()
    }

    override fun validate() {
    }
}
