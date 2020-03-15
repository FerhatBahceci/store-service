package store.service.store

import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.group

interface StoreGateway {

    suspend fun getStores(): List<Store>

    suspend fun getStore(id: String): Store?
}

class StoreDaoImpl(private val col: CoroutineCollection<Store>) : StoreGateway {

    override suspend fun getStores(): List<Store> = col.aggregate<Store>(group(Store::name)).toList()

    override suspend fun getStore(id: String): Store? =
            col.findOneById(id)
}

