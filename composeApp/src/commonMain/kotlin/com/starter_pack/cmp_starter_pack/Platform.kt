package com.starter_pack.cmp_starter_pack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform