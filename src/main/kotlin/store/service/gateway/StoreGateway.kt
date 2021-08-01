package store.service.gateway

import kotlinx.serialization.ExperimentalSerializationApi
import store.service.Store

@ExperimentalSerializationApi
interface StoreGateway {
    suspend fun getAllStores(): List<Store>
    suspend fun getStoreByName(name: String): Store
    suspend fun getStoreByType(type: Store.Type): List<Store>
    suspend fun createStore(store: Store)
    suspend fun deleteStore(id: String)
    suspend fun updateStore(store: Store, id: String): Store
}
