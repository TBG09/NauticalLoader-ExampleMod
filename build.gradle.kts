plugins {
    id("java")
}

group = "org.learn.java"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.json:json:20211205")
}

tasks.test {
    useJUnitPlatform()
}