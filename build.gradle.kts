import java.time.Duration

plugins {
    `java-library`
    `maven-publish`
    signing
    id("io.spring.dependency-management") version "1.1.7"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "de.octalog.lexware"
version = "3.4.0-SNAPSHOT"
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

tasks.withType<Javadoc> {
    options {
        // Disable doclint (equivalent to Maven's <doclint>none</doclint>)
        (this as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }
}

tasks.test {
    useJUnitPlatform()
}

// Publishing configuration
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("Lexware Java SDK")
                description.set("Unofficial Java SDK for the Lexware Public API.")
                url.set("https://github.com/octalog-de/lexware-java-sdk")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://spdx.org/licenses/MIT.html")
                    }
                }

                developers {
                    developer {
                        name.set("Marcel Richter")
                        email.set("info@octalog.de")
                        organization.set("octalog")
                        organizationUrl.set("https://octalog.de/")
                    }
                }

                scm {
                    url.set("https://github.com/octalog-de/lexware-java-sdk")
                    connection.set("scm:git:git@github.com:octalog-de/lexware-java-sdk.git")
                    developerConnection.set("scm:git:git@github.com:octalog-de/lexware-java-sdk.git")
                }
            }
        }
    }
}

// GPG Signing configuration (uses in-memory keys from 1Password)
signing {
    // Only sign if publishing
    setRequired { gradle.taskGraph.hasTask("publish") || gradle.taskGraph.hasTask("publishToSonatype") }

    val signingKey = System.getenv("ORG_GRADLE_PROJECT_signingKey")
    val signingPassword = System.getenv("ORG_GRADLE_PROJECT_signingPassword")
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
    }

    sign(publishing.publications["mavenJava"])
}

// Nexus publishing configuration for Maven Central via Sonatype Central Portal
nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://central.sonatype.com/api/v1/publisher/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/api/v1/publisher/"))
            username.set(System.getenv("CENTRAL_USERNAME"))
            password.set(System.getenv("CENTRAL_PASSWORD"))
        }
    }

    // Equivalent to autoPublish=true and waitMaxTime=3600 (1 hour)
    transitionCheckOptions {
        maxRetries.set(180) // 180 * 20s = 3600s = 1 hour
        delayBetween.set(Duration.ofSeconds(20))
    }
}
