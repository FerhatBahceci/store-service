package store.service

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
interface StoreGateway {
    suspend fun getAllStores(r: GetAllStoresRequest): List<Store>
    suspend fun getStoreByName(r: GetStoreByNameRequest): Store
    suspend fun getStoreByType(r: GetStoreByTypeRequest): List<Store>
    suspend fun createStore(r: CreateStoreRequest)
    suspend fun deleteStore(r: DeleteStoreByIdRequest)
    suspend fun updateStore(r: UpdateStoreRequest)
}
