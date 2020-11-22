package store.service.store

import com.mongodb.client.result.DeleteResult
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.boot.autoconfigure.session.StoreType
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

interface StoreGateway {
    suspend fun getAllStores(): List<Store>
    suspend fun getStoreById(id: String): Store
    suspend fun getStoreByType(type : StoreType) : List<Store>
    suspend fun createStore (store : Store) : Store
    suspend fun deleteStore(id : String): DeleteResult
    suspend fun updateStore(store: Store) : Store
}

class StoreGatewayImpl(private val db: ReactiveMongoTemplate) : StoreGateway {

    override suspend fun getAllStores(): List<Store> = db.findAll(Store::class.java).collectList().awaitFirst()

    override suspend fun getStoreById(id: String): Store = db.findById(id,Store::class.java).awaitFirst()

    override suspend fun getStoreByType(type: StoreType): List<Store> =
        db.find(Query.query(Criteria.where("type").`is`(type)), Store::class.java).collectList().awaitFirst()

    override suspend fun createStore(store: Store): Store =
        db.insert(store).awaitFirst()

    override suspend fun deleteStore(id: String) =
        db.remove(Query.query(Criteria.where("id").`is`(id))).awaitFirst()!!

    override suspend fun updateStore(store: Store): Store {
        TODO("Not yet implemented")
    }
}
