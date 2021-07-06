group = "me.jakejmattson"
version = "2.0.0"

plugins {
    kotlin("jvm") version "1.5.20"
    id("com.github.ben-manes.versions") version "0.39.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.bytedeco.javacpp-presets:opencv-platform:4.0.1-1.4.4")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}