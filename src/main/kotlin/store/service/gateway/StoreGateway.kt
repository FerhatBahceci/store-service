package store.service.gateway

import kotlinx.serialization.ExperimentalSerializationApi
import store.service.*

@ExperimentalSerializationApi
interface StoreGateway {
    suspend fun getAllStores(request: GetAllStoresRequest): List<Store>
    suspend fun getStoreByName(request: GetStoreByNameRequest): Store
    suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store>
    suspend fun createStore(request: CreateStoreRequest)
    suspend fun deleteStore(request: DeleteStoreByIdRequest)
    suspend fun updateStore(request: UpdateStoreRequest)
}
