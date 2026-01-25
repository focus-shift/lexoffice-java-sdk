import org.gradle.kotlin.dsl.configure

plugins {
    `java-library`
    id("io.spring.dependency-management") version "1.1.7"
    id("com.vanniktech.maven.publish") version "0.36.0"
}

group = "de.octalog.lexware"
val releaseVersion = providers.gradleProperty("VERSION_NAME").orNull
version = releaseVersion ?: "0.0.0-SNAPSHOT"
description = "Unofficial Java SDK for the Lexware Public API."

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
    withJavadocJar()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.5")
    }
}

dependencies {
    // Core dependencies
    implementation("org.springframework:spring-web")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.apache.httpcomponents.client5:httpclient5")

    // Lombok (optional + annotation processor)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Lombok for tests
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("generateMetadataFileForMavenPublication") {
    dependsOn(tasks.named("plainJavadocJar"))
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    pom {
        name.set("Lexware Java SDK")
        description.set("Unofficial Java SDK for the Lexware Public API.")
        url.set("https://github.com/octalog-de/lexware-java-sdk")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("marcelrichter")
                name.set("Marcel Richter")
                email.set("marcel@octalog.de")
                url.set("https://github.com/octalog-de")
            }
        }
        scm {
            url.set("https://github.com/octalog-de/lexware-java-sdk")
            connection.set("scm:git:git://github.com/octalog-de/lexware-java-sdk.git")
            developerConnection.set("scm:git:ssh://git@github.com:octalog-de/lexware-java-sdk.git")
        }
    }
}
