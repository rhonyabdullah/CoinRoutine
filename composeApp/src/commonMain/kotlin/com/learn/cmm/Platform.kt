package com.learn.cmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform