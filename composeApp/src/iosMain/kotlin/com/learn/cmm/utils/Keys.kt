package com.learn.cmm.utils

import org.jetbrains.compose.resources.getString
import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.Foundation.dictionaryWithContentsOfFile

actual object Keys {
    actual val coinRankingApiKey: String get() = getStringResource(
        filename = "Secrets",
        fileType = "plist",
        valueKey = "apiKey",
    ).orEmpty()

    private fun getStringResource(
        filename: String,
        fileType: String,
        valueKey: String,
    ): String? {
        val result = NSBundle.mainBundle.pathForResource(filename, fileType)?.let {
            val map = NSDictionary.dictionaryWithContentsOfFile(it)
            map?.get(valueKey) as? String
        }
        return result
    }
}
