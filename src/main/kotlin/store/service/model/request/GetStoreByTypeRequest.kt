package store.service.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import store.service.model.request.validators.OpeningHoursValidator.Companion.validateHours
import utility.request.Request

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val storeType: Store.Type?,
                                 override val type: Request.Type = Request.Type.GET,
                                 override val status: Int = 200) : Request<GetStoreByTypeRequest> {
    init {
        validate()
    }

    override fun validate() {}
}
