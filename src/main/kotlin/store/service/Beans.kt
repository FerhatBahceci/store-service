/*
package store.service

import io.grpc.BindableService
import io.grpc.ServerBuilder
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.core.env.get
import store.service.GrpcFactory.Companion.create
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
    bean { StoreServiceImpl(ref(), newFixedThreadPoolContext(4, "grpc-server")) }
    bean { create(env["grpc.port"]!!.toInt(), ref()) }
}

class GrpcFactory {
    companion object {
        fun create(port: Int, vararg services: BindableService) =
                ServerBuilder.forPort(port)
                        .apply { services.map { service -> addService(service) } }
                        .build()
                        .start()
                        .awaitTermination() // Remove or not?
    }
}
*/
