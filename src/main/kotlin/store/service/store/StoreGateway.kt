package store.service.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrElse
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.lang.Exception

interface StoreGateway {
    suspend fun getStores(): Flow<Store>
    suspend fun getStore(id: String): Store
}

class StoreGatewayImpl(private val db: ReactiveMongoTemplate) : StoreGateway {

    override suspend fun getStores(): Flow<Store> = db.findAll(Store::class.java).asFlow()

    override suspend fun getStore(id: String): Store = findOne(Criteria.where("id").`is`(id), Store::class.java)

    private suspend fun <T> findOne(criteria: Criteria, entity: Class<T>) = db.findOne(Query(criteria), entity).awaitFirstOrElse { throw EntityNotFoundException(criteria, entity.name) }
}

class EntityNotFoundException(criteria: Criteria, entityType: String) : Exception("Entity $entityType cannot be found for $criteria")
