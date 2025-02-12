package org.example.appcompose.common

data class BaseScreenState<T>(
    override val data: T? = null,
    override val isLoading: Boolean = false,
    override val error: String? = null
): BaseScreenStateI<T>

interface BaseScreenStateI<T> {
    val data: T?
        get() = null
    val isLoading: Boolean
        get() = false
    val error: String?
        get() = null
}