import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*
import java.net.URI;

group = "store.service"
version = "0.0.1-SNAPSHOT"

val micronautVersion = "3.4.2"
val protobufVersion = "3.20.1"
val grpcVersion = "1.45.1"
val kotlinVersion = "1.6.21"
val kotlinCoroutineVersion = "1.6.1"

repositories {
    mavenCentral()
    maven { url = URI("https://packages.confluent.io/maven/") }
}

plugins {
    application
    idea
    java

    val kotlinVersion = "1.6.21"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
    id("io.micronaut.application") version "3.3.2"
    id("com.google.protobuf") version "0.8.18"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

application {
    mainClass.set("store.service.App")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("store.service.*")
    }
}

/*
kapt {
    correctErrorTypes = true
}
*/

dependencies {

/*
    //TODO future possible improvements
    compileOnly("org.graalvm.nativeimage:svm")
*/

    /* Other */
    implementation("javax.inject:javax.inject:1")
    implementation("org.slf4j:slf4j-simple:1.7.36")

    /* Micronaut */
    kapt("io.micronaut:micronaut-validation:$micronautVersion")
    kapt("io.micronaut:micronaut-inject-java:$micronautVersion")
    implementation("io.micronaut:micronaut-management:$micronautVersion")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive:4.0.0")
    implementation("io.micronaut.configuration:micronaut-openapi:1.5.3")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime:3.0.0")
    implementation("io.micronaut.kafka:micronaut-kafka:4.3.0")
    implementation("io.confluent:kafka-protobuf-serializer:7.1.0")
    implementation("io.micronaut.reactor:micronaut-reactor:2.2.2")

    /* Kotlin */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinCoroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutineVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.3.2")

    /* Grpc */
    kapt("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("io.grpc:grpc-kotlin-stub:1.2.1")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")

    /* Use following dependencies strictly since latest Micronaut application plugin is not compatible with latest versions of Micronaut dependencies*/
    implementation("io.grpc:grpc-stub") {
        version {
            strictly(grpcVersion)
        }
    }

    implementation("io.micronaut:micronaut-http-server") {
        version {
            strictly(micronautVersion)
        }
    }

    implementation("io.micronaut:micronaut-http-server-netty") {
        version {
            strictly(micronautVersion)
        }
    }

    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime") {
        version {
            strictly("3.1.3")
        }
    }

    /* Test*/
    kaptTest("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("io.micronaut.grpc:micronaut-grpc-client-runtime:3.2.0")
    testImplementation("io.micronaut.test:micronaut-test-kotlintest:2.3.7")
    testRuntimeOnly("io.micronaut:micronaut-http-server-netty:${micronautVersion}")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks {

/*
//TODO duplicated dependencies due to micronaut-app plugin
val moduleName by extra("store.service")

    compileJava {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf(
                    "--module-path", classpath.asPath,
                    "--patch-module", "$moduleName=${sourceSets["main"].output.asPath}"
            )
            classpath = files()
        }
    }*/

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.2.1:jdk7@jar"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.generateDescriptorSet = true
            it.descriptorSetOptions.includeImports = true
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}
