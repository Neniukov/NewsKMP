package org.example.appcompose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform