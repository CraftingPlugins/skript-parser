plugins {
    id("dev.ghast.commons")
}

group = "io.github.syst3ms"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:15.0")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("dev.zenhro.bytecode:bytecode-ir")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.4.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.1")
    testImplementation("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.1")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}