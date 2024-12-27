plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("war")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

    // Utility Libraries
    implementation("org.jsoup:jsoup:1.18.1")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Selenium
    implementation("org.seleniumhq.selenium:selenium-java:4.14.0")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.14.0")

    // Redis
    implementation("org.redisson:redisson-spring-boot-starter:3.23.5")
    implementation("com.github.kstyrc:embedded-redis:0.6")

    // Database
    runtimeOnly("com.h2database:h2")
    implementation("com.mysql:mysql-connector-j")

    // Jakarta
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")

    // Logging
    implementation("org.slf4j:slf4j-api")

    // Lombok (optional in Kotlin)
    implementation("org.projectlombok:lombok:1.18.30")
    kapt("org.projectlombok:lombok:1.18.30")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("it.ozimov:embedded-redis:0.7.3")
}

configurations.all {
    resolutionStrategy {
        force("org.slf4j:slf4j-api:1.7.36")
    }
    exclude(group = "org.slf4j", module = "slf4j-simple")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.wrapper {
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.BIN
}