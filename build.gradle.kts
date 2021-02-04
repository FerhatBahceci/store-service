import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

group = "store.service"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

plugins {
    idea
    kotlin("jvm") version "1.4.20"
    kotlin("plugin.serialization") version "1.4.20"
    id("com.google.protobuf") version "0.8.14"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"
    id("org.jetbrains.kotlin.kapt") version "1.4.20-M2"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.20-M2"

}

application {
    mainClass.set("store.service.App.java")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
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

/*
8 Generating a Micronaut Application's Native Image with GraalVM
    annotationProcessor("io.micronaut:micronaut-graal")
*/

    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime:2.2.0")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive:3.1.0")
    implementation("io.micronaut:micronaut-management")

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

    kapt(platform("io.micronaut:micronaut-bom:1.2.0"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kapt("io.micronaut.configuration:micronaut-openapi")

    kaptTest("io.micronaut.grpc:micronaut-grpc-client-runtime:2.2.0")
    kaptTest("io.micronaut.test:micronaut-test-junit5")
    kaptTest("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-spock")
    testImplementation("org.spockframework:spock-core") {
        exclude("org.codehaus.groovy:groovy-all")
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

/*
val moduleName by extra("org.test.modularLib")

tasks {
    "compileJava"(JavaCompile::class) {
        inputs.property("moduleName", moduleName)
        doFirst {
            options.compilerArgs = listOf(
                    "--module-path", classpath.asPath,
                    "--patch-module", "$moduleName=${sourceSets["main"].output.asPath}"
            )
            classpath = files()
        }
    }
}
*/

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
