import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

java.sourceCompatibility = JavaVersion.VERSION_11
group = "store.service"
version = "0.0.1-SNAPSHOT"

buildscript {

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.10"))
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.12")
    }
}

repositories {
    mavenCentral()
}

plugins {
    idea
    kotlin("jvm") version "1.4.10"
    id("com.google.protobuf") version "0.8.12"

    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("plugin.spring") version "1.4.10"
}


dependencies {


    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.7")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")

    implementation("io.rouz:grpc-kotlin-gen:0.1.4")
    implementation("io.grpc:grpc-netty:1.29.0")
    implementation("io.grpc:grpc-protobuf:1.29.0")
    implementation("io.grpc:grpc-stub:1.29.0")
    implementation("io.grpc:grpc-services:1.29.0")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    implementation("io.kotest:kotest-runner-junit5-jvm:4.2.5")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
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
        id("grpckotlin") {
            artifact = "io.rouz:grpc-kotlin-gen:0.1.4"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckotlin")
            }
        }
    }
}
