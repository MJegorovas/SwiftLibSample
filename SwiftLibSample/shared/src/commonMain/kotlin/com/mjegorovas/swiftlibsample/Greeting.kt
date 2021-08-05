package com.mjegorovas.swiftlibsample

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}