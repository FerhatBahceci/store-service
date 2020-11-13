package store.service.store

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

interface StoreGateway {
    suspend fun getStores(): List<Store>
    suspend fun getStore(id: String): Store
}

class StoreGatewayImpl(private val db: ReactiveMongoTemplate) : StoreGateway {

    override suspend fun getStores() = db.findAll(Store::class.java).collectList().awaitFirst()

    override suspend fun getStore(id: String) = db.findById(id,Store::class.java).awaitFirst()
}

