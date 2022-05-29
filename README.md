# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

[Micronaut framework](https://docs.micronaut.io/latest/guide/index.html)

[Micronaut MongoDB](https://micronaut-projects.github.io/micronaut-mongodb/latest/guide/)

[Micronaut gRPC](https://micronaut-projects.github.io/micronaut-grpc/latest/guide/index.html#server)

[Micronaut Test](https://micronaut-projects.github.io/micronaut-test/latest/guide/)

[Micronaut Kafka](https://micronaut-projects.github.io/micronaut-kafka/latest/guide/)

[Micronaut Reactor](https://micronaut-projects.github.io/micronaut-reactor/latest/guide/)

[Kotlin gRPC](https://grpc.io/docs/languages/kotlin/)

[Kotlin Protobuf Serialization](https://kotlin.github.io/kotlinx.serialization/kotlinx-serialization-protobuf/kotlinx-serialization-protobuf/kotlinx.serialization.protobuf/index.html)

[Kotest](https://kotest.io/)

#### Application packaging structure
[Package by feature](http://www.javapractices.com/topic/TopicAction.do?Id=205)

#### Store-service packaging and JPMS

[Java 9 Platform Module System](https://www.oracle.com/corporate/features/understanding-java-9-modules.html)


##### Docker commands

1. Stop the container(s) using the following command:
**docker-compose down**

2. Delete all containers using the following command:
**docker rm -f $(docker ps -a -q)**

3. Delete all volumes using the following command:
**docker volume rm $(docker volume ls -q)**