pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "SwiftLibSample"
include(":androidApp")
include(":shared")
include(":CryptoKitWrapper")