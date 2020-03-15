import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

extra["springCloudVersion"] = "Hoxton.SR3"

java.sourceCompatibility = JavaVersion.VERSION_11
group = "store.service"
version = "0.0.1-SNAPSHOT"

buildscript {

	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.12")
	}
}

plugins {
	idea
	id("org.springframework.boot") version "2.2.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
	id("com.google.protobuf") version "0.8.12"
}

repositories {
	mavenCentral()
}

dependencies {
/*
	implementation("org.springframework.boot:spring-boot-starter-web")
*/
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework:spring-core")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")
	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.61")
	implementation("io.rouz:grpc-kotlin-gen:0.1.4")
	implementation("io.grpc:grpc-stub:1.25.0")
/*	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}*/
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
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


