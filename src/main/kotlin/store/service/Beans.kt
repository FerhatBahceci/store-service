package store.service

import com.mongodb.MongoClient
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import store.viewer.repositories.StoreDao
import store.viewer.service.StoreService

class BeanInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(applicationContext: GenericApplicationContext) {
        beans().initialize(applicationContext)
    }
}

fun beans() = beans {
/*
    bean { MongoClient(env["app.spring.datasource.url"])}
*/
    bean { StoreDao::class }
    bean { StoreService(ref())}
}

