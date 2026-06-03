plugins {
    id("java")
}

group = "io.example.antigen"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("io.rest-assured:json-path:5.3.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
    implementation("io.antigen:antigen:1.0.0-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

tasks.test {
    useJUnitPlatform()

    doFirst {
        val runWithAntigen = System.getProperty("runWithAntigen") == "true"
        jvmArgs("-DrunWithAntigen=$runWithAntigen")

        if (runWithAntigen) {
            val agent = configurations.testRuntimeClasspath.get()
                .files.find { it.name.contains("aspectjweaver") }?.absolutePath
            if (agent != null) {
                jvmArgs("-javaagent:$agent")
            }
        }
    }
}
