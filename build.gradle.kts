val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kotlin_logging_version: String by project
val ktorm_version: String by project
val kotlinx_datetime_version: String by project
val mysql_version: String by project

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.ktor.plugin") version "2.3.6"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:$kotlinx_datetime_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.github.oshai:kotlin-logging-jvm:$kotlin_logging_version")

    implementation("org.ktorm:ktorm-core:${ktorm_version}")
    implementation("org.ktorm:ktorm-support-mysql:${ktorm_version}")
    implementation("mysql:mysql-connector-java:$mysql_version")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
