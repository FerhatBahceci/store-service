import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

group = "store.service"
version = "0.0.1-SNAPSHOT"

buildscript {

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.20"))
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.14")
        classpath("io.micronaut.gradle:micronaut-gradle-plugin:1.2.0")
    }
}

plugins {
    idea
    kotlin("jvm") version "1.4.20"
    kotlin("plugin.serialization") version "1.4.20"
    id("com.google.protobuf") version "0.8.14"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("store.service.*")
    }
}

application {
    mainClass.set("store.service.App.java")
}

java {
    sourceCompatibility = JavaVersion.toVersion("1.8")
    targetCompatibility = JavaVersion.toVersion("1.8")
}


repositories {
    mavenCentral()
    jcenter()
}

dependencies {

    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime:2.2.0")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive:3.1.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.0.1")
    
    implementation("io.grpc:grpc-protobuf-lite:1.33.1")
    implementation("io.grpc:grpc-protobuf:1.33.1")
    implementation("io.grpc:grpc-kotlin-stub-lite:0.2.1")
    implementation("io.grpc:grpc-netty:1.33.1")
    implementation("com.google.protobuf:protobuf-java:3.14.0")
    implementation("com.google.guava:guava:30.0-jre")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.2.5")
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
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
