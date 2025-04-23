package com.learn.cmm

import androidx.compose.ui.window.ComposeUIViewController
import com.learn.cmm.di.initKoin

@Suppress("FunctionName")
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    },
    content = { App() }
)
