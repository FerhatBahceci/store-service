module store.service.lib {
    requires transitive kotlinx.serialization.core.jvm;
    requires org.mongodb.driver.core;
    requires javax.inject;
    requires org.mongodb.driver.reactivestreams;
    requires kotlinx.coroutines.core.jvm;
    requires kotlin.stdlib;
    requires io.micronaut.inject;
    requires io.micronaut.grpc.grpc_annotation;
    requires kotlinx.serialization.protobuf.jvm;
    requires kotlinx.coroutines.reactive;
}

