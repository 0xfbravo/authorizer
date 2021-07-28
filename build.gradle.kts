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
    jacoco
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
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.9".toBigDecimal()
            }
        }
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = jvmTarget
}

tasks.named<Jar>("jar") {
    archiveFileName.set("authorize.jar")
    manifest {
        attributes["Implementation-Title"] = "Authorizer"
        attributes["Implementation-Version"] = archiveVersion
        attributes["Main-Class"] = "presentation.MainKt"
    }
    from(configurations.runtimeClasspath.get()
        .onEach { println("Adding dependency: ${it.name}") }
        .map { if (it.isDirectory) it else zipTree(it) })
    exclude("**/module-info.class")
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("Adding source code: ${it.name}") }
    from(sourcesMain.output)
}

application {
    mainClass.set("presentation.MainKt")
}