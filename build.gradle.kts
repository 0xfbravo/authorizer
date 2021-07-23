import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Variables
var jvmTarget = "1.8"
var koinVersion = "3.1.2"
var kotlinxJsonVersion = "1.2.2"
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
    testImplementation(kotlin("test-junit"))
    // Koin core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-test-junit4:$koinVersion")
    // Kotlinx JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxJsonVersion")
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