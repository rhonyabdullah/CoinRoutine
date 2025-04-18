package com.learn.cmm.utils

import com.learn.cmm.BuildConfig

actual object Keys {
    actual val coinRankingApiKey: String get() = BuildConfig.API_KEY
}
