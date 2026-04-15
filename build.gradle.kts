plugins {
    id("java")
    id("maven-publish")
}

group = "org.meteordev"
version = "0.2.5"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.openjdk.jmh:jmh-core:1.36")
    testImplementation("org.openjdk.jmh:jmh-generator-annprocess:1.36")

    testAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.36")
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
}

tasks.test {
    failOnNoDiscoveredTests = false
}

publishing {
    publications {
        create<MavenPublication>("java") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "meteor-maven"
            url = uri("https://maven.meteordev.org/releases")

            credentials {
                username = System.getenv("MAVEN_METEOR_ALIAS")
                password = System.getenv("MAVEN_METEOR_TOKEN")
            }

            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}
