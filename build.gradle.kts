import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "me.daewon"
version = "2022.44.0"

repositories {
    maven("https://repo.spring.io/milestone")
    mavenCentral()
}

dependencies {
    implementation("io.projectreactor.rabbitmq:reactor-rabbitmq:1.5.5")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
