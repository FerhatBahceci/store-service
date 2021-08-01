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
    id("org.jetbrains.kotlin.jvm") version "1.4.32"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.32"
    id("com.google.protobuf") version "0.8.15"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.4.2"
    id("org.jetbrains.kotlin.kapt") version "1.4.32"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
}

application {
    mainClass.set("store.service.App.java")
}

configure<JavaPluginConvention> {
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

/*
8 Generating a Micronaut Application's Native Image with GraalVM
    annotationProcessor("io.micronaut:micronaut-graal")
*/

    implementation("io.micronaut:micronaut-core-reactive:2.4.1")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive:3.1.0")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-inject:2.4.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.0.1")
    implementation("io.micronaut.grpc:micronaut-grpc-server-runtime:2.3.0")
    implementation("io.grpc:grpc-protobuf-lite:1.36.0")
    implementation("io.grpc:grpc-protobuf:1.36.0")
    implementation("io.grpc:grpc-kotlin-stub-lite:1.0.0")
    implementation("io.grpc:grpc-netty:1.36.0")

    implementation("com.google.protobuf:protobuf-java:3.15.6")
    implementation("com.google.guava:guava:30.0-jre")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")

    kapt(platform("io.micronaut:micronaut-bom:1.3.0"))
    kapt("io.micronaut:micronaut-validation:2.4.1")
    kapt("io.micronaut:micronaut-inject-java:2.4.1")
    kapt("io.micronaut.configuration:micronaut-openapi")

    kaptTest("io.micronaut.test:micronaut-test-junit5:2.3.3")
    kaptTest("org.junit.jupiter:junit-jupiter-api:5.7.1")

    testImplementation("io.micronaut.grpc:micronaut-grpc-client-runtime:2.3.0")

    kaptTest("io.micronaut:micronaut-inject-java")
    testImplementation("io.micronaut.test:micronaut-test-kotlintest:2.3.3")
    testImplementation("io.mockk:mockk:1.10.5")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.0.1")

    testRuntimeOnly("io.micronaut:micronaut-http-server-netty:2.4.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    withType<Test> {
        useJUnitPlatform()
    }
}


//TODO fix all dependencies for enabling JPMS.
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
