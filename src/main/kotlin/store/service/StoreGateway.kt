package store.service

import kotlinx.serialization.ExperimentalSerializationApi
import utility.UnitData

@ExperimentalSerializationApi
interface StoreGateway {
    suspend fun getAllStores(r: GetAllStoresRequest): List<Store>
    suspend fun getStoreById(r: GetStoreByIdRequest): Store
    suspend fun getStoreByType(r: GetStoreByTypeRequest): List<Store>
    suspend fun createStore(r: CreateStoreRequest): UnitData
    suspend fun deleteStore(r: DeleteStoreByIdRequest): UnitData
    suspend fun updateStore(r: UpdateStoreRequest): UnitData
}
