plugins {
    id("java")
    id("java-library")
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("maven-publish")
}

group = "com.kjson"
version = "1.0"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.kjson"
            artifactId = "release"
            version = "1.0.0"
            from(components["java"])

            pom {
                name.set("KJson")
                description.set("Simple org.json replacement for Kotlin")
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tiksem/KotlinUtils")
            credentials {
                username = "tiksem"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    testImplementation("junit:junit:4.13.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}