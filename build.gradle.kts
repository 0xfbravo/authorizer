import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Variables
var koinVersion = "3.1.2"
var jvmTarget = "1.8"
group = "com.some.authorizer"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.4.32"
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