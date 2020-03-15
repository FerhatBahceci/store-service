package store.viewer.repositories

import org.springframework.data.repository.CrudRepository

interface StoreDao : CrudRepository<Store, Long> {

    fun findByName(name: String): Store

    override fun findAll(): Set<Store>

    fun save(store: Store)
}

