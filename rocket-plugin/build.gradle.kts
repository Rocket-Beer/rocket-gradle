import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    `kotlin-dsl`
    java
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.18.0"
}

pluginBundle {
    website = "https://github.com/Rocket-Beer/rocket-gradle"
    vcsUrl = "https://github.com/Rocket-Beer/rocket-gradle.git"
    tags = listOf("Rocket", "Rocket-Beer", "publish", "detekt")
}

repositories {
    maven("https://plugins.gradle.org/m2/")
    google()
    mavenCentral()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.1.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.17.1")
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

gradlePlugin {
    plugins {
        create("rocket-plugin") {
            id = "rocket-plugin"
            displayName = "Rocket Plugin"
            description = "Plugin for detekt and publish configurations"
            implementationClass = "RocketPlugin"
        }
    }
}

version = "1.0-dev02"

publishing {
    publications {
        create<MavenPublication>("gpr") {
            artifactId = "rocket-plugin"
            groupId = "com.rocket.gradle.plugins"
            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Rocket-Beer/rocket-gradle")
            credentials {
                var userName: String?
                var token: String?

                try {
                    val properties = loadProperties("$rootDir/local.properties")
                    userName = properties.getProperty("github.username")
                    if (userName.isEmpty()) userName = System.getenv("GITHUB_ACTOR")

                    token = properties.getProperty("github.token")
                    if (token.isEmpty()) token = System.getenv("GITHUB_TOKEN")
                } catch (e: Exception) {
                    userName = System.getenv("GITHUB_ACTOR")
                    token = System.getenv("GITHUB_TOKEN")
                }

                username = userName
                password = token
            }
        }
    }
}

//test {
//    useJUnitPlatform()
//}