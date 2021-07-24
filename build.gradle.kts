import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Variables
var jvmTarget = "1.8"
var koinVersion = "3.1.2"
var gsonVersion = "2.8.7"
var mockitoVersion = "3.2.0"
group = "com.some.authorizer"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.5.20"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Tests
    testImplementation(kotlin("test-junit"))
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")

    // Koin core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-test-junit4:$koinVersion")
    // GSON
    implementation("com.google.code.gson:gson:$gsonVersion")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = jvmTarget
}

application {
    mainClass.set("MainKt")
}