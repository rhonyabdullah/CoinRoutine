package com.learn.cmm.theme

import java.util.Locale

actual fun setLanguage(language: String) {
    val locale = Locale.forLanguageTag(language)
    Locale.setDefault(locale)
}
