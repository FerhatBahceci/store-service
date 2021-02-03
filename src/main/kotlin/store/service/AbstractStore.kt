package store.service

import kotlinx.serialization.*

@ExperimentalSerializationApi
interface AbstractStore {
    val coordinates: Coordinates?
    val description: String?
    val id: String?
    val name: String?
    val phoneNo: String?
}

@ExperimentalSerializationApi
@Serializable
data class Coordinates(val longitude: Long?, val latitude: Long?)
