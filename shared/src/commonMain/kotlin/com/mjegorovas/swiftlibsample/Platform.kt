package com.mjegorovas.swiftlibsample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform