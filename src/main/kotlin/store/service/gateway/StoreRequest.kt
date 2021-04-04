package store.service.gateway

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import utility.request.Request
import java.lang.IllegalArgumentException
import java.time.DayOfWeek
import java.util.regex.Pattern

//TODO, once more knowledge about the Store requirements are gathered, validate() method should be implemented properly rather than only null asserting.

@Serializable
data class GetAllStoresRequest(override val type: Request.Type = Request.Type.GET) : Request<GetAllStoresRequest> {
    override fun validate() {
    }
}

@Serializable
data class GetStoreByNameRequest(val name: String?, override val type: Request.Type = Request.Type.GET) : Request<GetStoreByNameRequest> {
    override fun validate() {
    }
}

@ExperimentalSerializationApi
@Serializable
data class GetStoreByTypeRequest(val storeType: Store.Type?, override val type: Request.Type = Request.Type.GET) : Request<GetStoreByTypeRequest> {
    override fun validate() {
    }
}

@ExperimentalSerializationApi
@Serializable
data class CreateStoreRequest(val store: Store?, override val type: Request.Type = Request.Type.POST) : Request<CreateStoreRequest> {

    override fun validate() {
        store?.validateStore()
    }
}

@Serializable
data class DeleteStoreByIdRequest(val id: String?, override val type: Request.Type = Request.Type.DELETE) : Request<DeleteStoreByIdRequest> {
    override fun validate() {
    }
}

@ExperimentalSerializationApi
@Serializable
data class UpdateStoreRequest(val id: String?, val store: Store?, override val type: Request.Type = Request.Type.PUT) : Request<UpdateStoreRequest> {
    override fun validate() {
        store?.validateStore()
    }
}

@ExperimentalSerializationApi
private fun Store.validateStore() {
    this.hours?.validateHours()
/*
    this.phoneNo?.validatePhoneNo()
*/
}

@ExperimentalSerializationApi
private fun Map<String, Store.OpeningHours>.validateHours() {
    this.keys.forEach { DayOfWeek.valueOf(it) }
    this.values.forEach { if (it.opening?.seconds ?: 0 > it.closing?.seconds ?: 0) throw IllegalArgumentException("Invalid opening hour: $it") }
}

private fun String.validatePhoneNo() {
    val pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((xn\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$") // TODO fix
    if (!pattern.matcher(this).matches()) throw IllegalArgumentException("Invalid telephone number!")
}
