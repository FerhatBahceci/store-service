package store.service

import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import store.service.store.StoreGatewayImpl
import store.service.store.StoreServiceImpl

@ObsoleteCoroutinesApi
class BeanInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(applicationContext: GenericApplicationContext) {
        beans().initialize(applicationContext)
    }
}

@ObsoleteCoroutinesApi
fun beans() = beans {
    bean<StoreGatewayImpl>()
    bean { StoreServiceImpl(ref()) }
}