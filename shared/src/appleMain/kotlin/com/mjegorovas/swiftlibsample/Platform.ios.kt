package com.mjegorovas.swiftlibsample

import platform.Foundation.NSProcessInfo

class IOSPlatform: Platform {
    override val name: String = NSProcessInfo.processInfo.operatingSystemName() + " " + NSProcessInfo.processInfo.operatingSystemVersionString
}

actual fun getPlatform(): Platform = IOSPlatform()