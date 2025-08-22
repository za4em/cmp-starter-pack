package com.cmp.starter_pack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform