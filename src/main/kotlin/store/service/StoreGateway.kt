package store.service

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
interface StoreGateway {
    suspend fun getAllStores(request: GetAllStoresRequest): List<Store>
    suspend fun getStoreById(request: GetStoreByIdRequest): Store
    suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store>
    suspend fun createStore(request: CreateStoreRequest): InsertOneResult
    suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteResult
    suspend fun updateStore(request: UpdateStoreRequest): UpdateResult
}
