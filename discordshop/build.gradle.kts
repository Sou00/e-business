val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val slack_version: String by project


plugins {
    kotlin("jvm") version "1.6.0"
    id("io.ktor.plugin") version "2.2.4"
    kotlin("plugin.serialization").version("1.6.21")
}

group = "com.discordshop"
version = "0.0.1"
application {
    mainClass.set("com.discordshop.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation ("com.slack.api:slack-api-client:$slack_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
}