package store.service

import kotlinx.serialization.ExperimentalSerializationApi
import utility.UnitData

@ExperimentalSerializationApi
interface StoreGateway {
    suspend fun getAllStores(request: GetAllStoresRequest): List<Store>
    suspend fun getStoreById(request: GetStoreByIdRequest): Store
    suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store>
    suspend fun createStore(request: CreateStoreRequest): UnitData
    suspend fun deleteStore(request: DeleteStoreByIdRequest): UnitData
    suspend fun updateStore(request: UpdateStoreRequest): UnitData
}
