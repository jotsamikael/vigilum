package com.example.utilities

sealed class ResourceState<out T> {
    class Loading<out T>: ResourceState<T>()
    data class Success<out T>(val data: T): ResourceState<T>()
    data class Error<T>(val data:String): ResourceState<T>()
}