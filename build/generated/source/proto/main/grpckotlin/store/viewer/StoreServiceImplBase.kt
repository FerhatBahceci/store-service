package store.viewer

import store.viewer.StoreServiceGrpc.*

import io.grpc.*
import io.grpc.stub.*
import io.rouz.grpc.*

import kotlin.coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*



@javax.annotation.Generated(
    value = ["by gRPC Kotlin generator"],
    comments = "Source: api.proto"
)
abstract class StoreServiceImplBase(
    coroutineContext: CoroutineContext = Dispatchers.Default
) : BindableService, CoroutineScope {

    private val _coroutineContext: CoroutineContext = coroutineContext

    override val coroutineContext: CoroutineContext
        get() = ContextCoroutineContextElement() + _coroutineContext

    
    
    
    open suspend fun geAllStores(request: store.viewer.GetAllStoresRequest): store.viewer.Stores {
        throw unimplemented(getGeAllStoresMethod()).asRuntimeException()
    }

    internal fun geAllStoresInternal(
        request: store.viewer.GetAllStoresRequest,
        responseObserver: StreamObserver<store.viewer.Stores>
    ) {
        launch {
            tryCatchingStatus(responseObserver) {
                val response = geAllStores(request)
                onNext(response)
            }
        }
    }

    override fun bindService(): ServerServiceDefinition {
        return ServerServiceDefinition.builder(getServiceDescriptor())
            .addMethod(
                getGeAllStoresMethod(),
                ServerCalls.asyncUnaryCall(
                    MethodHandlers(METHODID_GE_ALL_STORES)
                )
            )
            .build()
    }

    private fun unimplemented(methodDescriptor: MethodDescriptor<*, *>): Status {
        return Status.UNIMPLEMENTED
            .withDescription("Method ${methodDescriptor.fullMethodName} is unimplemented")
    }

    private fun <E> handleException(t: Throwable?, responseObserver: StreamObserver<E>) {
        when (t) {
            null -> return
            is CancellationException -> handleException(t.cause, responseObserver)
            is StatusException, is StatusRuntimeException -> responseObserver.onError(t)
            is RuntimeException -> {
                responseObserver.onError(Status.UNKNOWN.asRuntimeException())
                throw t
            }
            is Exception -> {
                responseObserver.onError(Status.UNKNOWN.asException())
                throw t
            }
            else -> {
                responseObserver.onError(Status.INTERNAL.asException())
                throw t
            }
        }
    }

    private suspend fun <E> tryCatchingStatus(responseObserver: StreamObserver<E>, body: suspend StreamObserver<E>.() -> Unit) {
        try {
            responseObserver.body()
            responseObserver.onCompleted()
        } catch (t: Throwable) {
            handleException(t, responseObserver)
        }
    }

    private val METHODID_GE_ALL_STORES = 0

    private inner class MethodHandlers<Req, Resp> internal constructor(
        private val methodId: Int
    ) : ServerCalls.UnaryMethod<Req, Resp>,
        ServerCalls.ServerStreamingMethod<Req, Resp>,
        ServerCalls.ClientStreamingMethod<Req, Resp>,
        ServerCalls.BidiStreamingMethod<Req, Resp> {

        @Suppress("UNCHECKED_CAST")
        override fun invoke(request: Req, responseObserver: StreamObserver<Resp>) {
            when (methodId) {
                METHODID_GE_ALL_STORES ->
                    this@StoreServiceImplBase.geAllStoresInternal(
                        request as store.viewer.GetAllStoresRequest,
                        responseObserver as StreamObserver<store.viewer.Stores>
                    )
                else -> throw AssertionError()
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun invoke(responseObserver: StreamObserver<Resp>): StreamObserver<Req> {
            when (methodId) {
                else -> throw AssertionError()
            }
        }
    }
}
