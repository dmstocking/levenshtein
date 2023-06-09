plugins {
    kotlin("jvm") version "1.8.21"
}

group = "com.github.dmstocking"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}