package com.learn.cmm.theme

import platform.Foundation.NSUserDefaults

actual fun setLanguage(language: String) {
    NSUserDefaults.standardUserDefaults
        .setObject(
            value = arrayListOf(language),
            forKey = "AppleLanguages"
        )
}
