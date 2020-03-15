package store.viewer;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.25.0)",
    comments = "Source: api.proto")
public final class StoreServiceGrpc {

  private StoreServiceGrpc() {}

  public static final String SERVICE_NAME = "store.viewer.StoreService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<store.viewer.GetAllStoresRequest,
      store.viewer.Stores> getGeAllStoresMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GeAllStores",
      requestType = store.viewer.GetAllStoresRequest.class,
      responseType = store.viewer.Stores.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<store.viewer.GetAllStoresRequest,
      store.viewer.Stores> getGeAllStoresMethod() {
    io.grpc.MethodDescriptor<store.viewer.GetAllStoresRequest, store.viewer.Stores> getGeAllStoresMethod;
    if ((getGeAllStoresMethod = StoreServiceGrpc.getGeAllStoresMethod) == null) {
      synchronized (StoreServiceGrpc.class) {
        if ((getGeAllStoresMethod = StoreServiceGrpc.getGeAllStoresMethod) == null) {
          StoreServiceGrpc.getGeAllStoresMethod = getGeAllStoresMethod =
              io.grpc.MethodDescriptor.<store.viewer.GetAllStoresRequest, store.viewer.Stores>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GeAllStores"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  store.viewer.GetAllStoresRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  store.viewer.Stores.getDefaultInstance()))
              .setSchemaDescriptor(new StoreServiceMethodDescriptorSupplier("GeAllStores"))
              .build();
        }
      }
    }
    return getGeAllStoresMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StoreServiceStub newStub(io.grpc.Channel channel) {
    return new StoreServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StoreServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StoreServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class StoreServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void geAllStores(store.viewer.GetAllStoresRequest request,
        io.grpc.stub.StreamObserver<store.viewer.Stores> responseObserver) {
      asyncUnimplementedUnaryCall(getGeAllStoresMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGeAllStoresMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                store.viewer.GetAllStoresRequest,
                store.viewer.Stores>(
                  this, METHODID_GE_ALL_STORES)))
          .build();
    }
  }

  /**
   */
  public static final class StoreServiceStub extends io.grpc.stub.AbstractStub<StoreServiceStub> {
    private StoreServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StoreServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StoreServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StoreServiceStub(channel, callOptions);
    }

    /**
     */
    public void geAllStores(store.viewer.GetAllStoresRequest request,
        io.grpc.stub.StreamObserver<store.viewer.Stores> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGeAllStoresMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StoreServiceBlockingStub extends io.grpc.stub.AbstractStub<StoreServiceBlockingStub> {
    private StoreServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StoreServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StoreServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StoreServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public store.viewer.Stores geAllStores(store.viewer.GetAllStoresRequest request) {
      return blockingUnaryCall(
          getChannel(), getGeAllStoresMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StoreServiceFutureStub extends io.grpc.stub.AbstractStub<StoreServiceFutureStub> {
    private StoreServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StoreServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StoreServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StoreServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<store.viewer.Stores> geAllStores(
        store.viewer.GetAllStoresRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGeAllStoresMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GE_ALL_STORES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StoreServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StoreServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GE_ALL_STORES:
          serviceImpl.geAllStores((store.viewer.GetAllStoresRequest) request,
              (io.grpc.stub.StreamObserver<store.viewer.Stores>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StoreServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return store.viewer.Api.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StoreService");
    }
  }

  private static final class StoreServiceFileDescriptorSupplier
      extends StoreServiceBaseDescriptorSupplier {
    StoreServiceFileDescriptorSupplier() {}
  }

  private static final class StoreServiceMethodDescriptorSupplier
      extends StoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StoreServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StoreServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StoreServiceFileDescriptorSupplier())
              .addMethod(getGeAllStoresMethod())
              .build();
        }
      }
    }
    return result;
  }
}
