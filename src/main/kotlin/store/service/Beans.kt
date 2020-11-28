package store.service

import io.grpc.BindableService
import io.grpc.ServerBuilder
import io.micronaut.context.ApplicationContext

class BeanInitializer {
    var applicationContext = ApplicationContext.run()

}

/*@ObsoleteCoroutinesApi
fun beans() = beans {

    bean<StoreGatewayImpl>()
    bean { StoreServiceImpl(ref(), newFixedThreadPoolContext(4, "grpc-server")) }
    bean { create(env["grpc.port"]!!.toInt(), ref()) }
}*/

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
