plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "web"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20231013")
    implementation(files("libs/fastcgi-lib.jar"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}