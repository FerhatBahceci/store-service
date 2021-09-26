import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

group = "store.service"
version = "0.0.1-SNAPSHOT"

val protobufVersion = "1.41.0"
val micronautVersion = "3.0.2"
val kotlinCoroutineVersion = "1.5.2"

repositories {
    mavenCentral()
}

plugins {
    application
    idea
    java

    val kotlinVersion = "1.5.31"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("io.micronaut.application") version "2.0.6"
    id("com.google.protobuf") version "0.8.17"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

application {
    mainClass.set("store.service.App")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("store.service.*")
    }
}

dependencies {

    annotationProcessor("io.micronaut:micronaut-http-validation")
    compileOnly("org.graalvm.nativeimage:svm")

    implementation("javax.inject:javax.inject:1")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive:4.0.0")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime:3.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${kotlinCoroutineVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinCoroutineVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.3.0")

    implementation("io.grpc:grpc-protobuf-lite:${protobufVersion}")
    implementation("io.grpc:grpc-protobuf:${protobufVersion}")
    implementation("io.grpc:grpc-kotlin-stub-lite:1.0.0")

    implementation("com.google.protobuf:protobuf-java:3.18.0")
    implementation("com.google.guava:guava:31.0-jre")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    testImplementation("io.micronaut.test:micronaut-test-kotlintest:2.3.7")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
    testImplementation("io.micronaut.grpc:micronaut-grpc-client-runtime:3.0.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("io.micronaut.test:micronaut-test-junit5:${micronautVersion}")
}

tasks {

/*    val moduleName by extra("store.service")

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
        kotlinOptions.jvmTarget = "11"
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.11.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.25.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.2.0:jdk7@jar"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}
