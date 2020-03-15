import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

java.sourceCompatibility = JavaVersion.VERSION_11
group = "store.service"
version = "0.0.1-SNAPSHOT"

buildscript {

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.72"))
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.12")
    }
}

plugins {
    idea
    kotlin("jvm") version "1.3.72"
    id("com.google.protobuf") version "0.8.12"
}

repositories {
    mavenCentral()
}

dependencies {


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

    implementation("org.litote.kmongo:kmongo-coroutine:4.0.0")

    implementation("com.github.ben-manes.caffeine:caffeine:2.8.1")
    implementation("com.google.dagger:dagger:2.27")

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
