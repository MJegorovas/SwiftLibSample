package com.mjegorovas.swiftlibsample

import platform.UIKit.UIDevice
import swift.cryptoKit.CryptoKitWrapper

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    fun a() {
//        CryptoKitWrapper.decryptWithKey()
    }
}