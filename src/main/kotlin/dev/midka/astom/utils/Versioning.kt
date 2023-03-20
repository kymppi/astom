package dev.midka.astom.utils

class Versioning(val path: String) {
    fun get(): String {
        return javaClass.getResource(path)?.readText()?.trim()?.substring(0..15) ?: "Unknown"
    }
}