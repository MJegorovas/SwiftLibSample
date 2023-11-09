plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(iosArm64(), iosSimulatorArm64(), macosArm64()).forEach {
        with (it) {
            val platform = when (name) {
                "iosArm64" -> "iphoneos"
                "iosSimulatorArm64" -> "iphonesimulator"
                "macosArm64" -> "macosx"
                else -> error("Unsupported target $name")
            }
            compilations.getByName("main") {
                cinterops.create("CryptoKitWrapper") {
                    val interopTask = tasks[interopProcessingTaskName]
                    interopTask.dependsOn(":CryptoKitWrapper:build${platform.capitalize()}")
                    includeDirs.headerFilterOnly("$rootDir/CryptoKitWrapper/build/Release-$platform/include")
                }
            }
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        osx.deploymentTarget = "13.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.mjegorovas.swiftlibsample"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}