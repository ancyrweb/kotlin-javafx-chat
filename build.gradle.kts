plugins {
  kotlin("jvm") version "1.9.22"
  id("java")
  id("application")
  id("org.javamodularity.moduleplugin") version "1.8.12"
  id("org.openjfx.javafxplugin") version "0.0.13"
  id("org.beryx.jlink") version "2.25.0"
}

allprojects {
  apply(plugin = "java")
  apply(plugin = "org.javamodularity.moduleplugin")
  apply(plugin = "org.openjfx.javafxplugin")
  apply(plugin = "org.beryx.jlink")
  apply(plugin = "kotlin")

  repositories {
    mavenCentral()
  }

  tasks.test {
    useJUnitPlatform()
  }

  kotlin {
    jvmToolchain(21)
  }
}