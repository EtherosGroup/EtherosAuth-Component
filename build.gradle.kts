plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

val lombokVersion = "1.18.36"
val paperApiVersion = "1.20.5-R0.1-SNAPSHOT"
val velocityApiVersion = "3.3.0-SNAPSHOT"
val etherosFrameworkVersion = "1.0.10"
val configurateVersion = "4.1.2"

allprojects {
    group = "com.skilfully.etheros"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
        maven {
            name = "sonatype-snapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    java {
        val javaVersion = JavaVersion.toVersion(21)
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        if (JavaVersion.current() < javaVersion) {
            toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        if (JavaVersion.current().isJava10Compatible) {
            options.release.set(21)
        }
    }

    tasks.processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:$lombokVersion")
        annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    }
}
