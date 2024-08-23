package com.carbon.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * A generic class that holds data and status.
 */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String
) {
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, "")
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, "")
        }

        fun <T> nothing(data: T? = null, message: String = ""): Resource<T> {
            return Resource(Status.NOTHING, data, message)
        }
    }

    fun isSuccess(): Boolean =
        status == Status.SUCCESS

    fun isLoading(): Boolean =
        status == Status.LOADING

    fun isError(): Boolean =
        status == Status.ERROR

    fun isNothing(): Boolean =
        status == Status.NOTHING
}

fun <T> Flow<T>.asResource(): Flow<Resource<T>> {
    return this
        .map<T, Resource<T>> {
            Resource.success(it)
        }
        .onStart { emit(Resource.loading()) }
        .catch { emit(Resource.error(it.message ?: "")) }
}

enum class Status {

    SUCCESS,

    ERROR,

    LOADING,

    NOTHING
}
