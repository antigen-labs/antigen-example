buildscript {
    repositories {
        mavenLocal()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("io.antigen:antigen:1.0.0-SNAPSHOT")
    }
}

plugins {
    id("java")
}
apply(plugin = "io.antigen")

group = "io.example.antigen"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("io.rest-assured:json-path:5.3.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("com.github.antigen-framework:antigen:v0.3")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

tasks.test {
    useJUnitPlatform()

    doFirst {
        System.getProperty("runWithAntigen")?.let { jvmArgs("-DrunWithAntigen=$it") }
        System.getProperty("antigen.report.path")?.let { jvmArgs("-Dantigen.report.path=$it") }

        if (System.getProperty("runWithAntigen") == "true") {
            configurations.testRuntimeClasspath.get()
                .files.find { it.name.contains("aspectjweaver") }
                ?.let { jvmArgs("-javaagent:${it.absolutePath}") }
        }
    }
}
