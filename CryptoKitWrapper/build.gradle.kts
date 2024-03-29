listOf("iphoneos", "iphonesimulator", "macosx").forEach { sdk ->
    tasks.create<Exec>("build${sdk.capitalize()}") {
        group = "build"

        commandLine(
            "xcodebuild",
            "-project", "CryptoKitWrapper.xcodeproj",
            "-target", "CryptoKitWrapper",
            "-sdk", sdk
        )
        workingDir(projectDir)

        inputs.files(
            fileTree("$projectDir/CryptoKitWrapper.xcodeproj") { exclude("**/xcuserdata") },
            fileTree("$projectDir/CryptoKitWrapper")
        )
        outputs.files(
            fileTree("$projectDir/build/Release-${sdk}")
        )
    }
}

tasks.create<Delete>("clean") {
    group = "build"

    delete("$projectDir/build")
}
