import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

java.sourceCompatibility = JavaVersion.VERSION_11
group = "store.service"
version = "0.0.1-SNAPSHOT"

buildscript {

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.10"))
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.14")
    }
}

repositories {
    mavenCentral()
}

plugins {
    idea
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    id("com.google.protobuf") version "0.8.13"
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
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

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
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
