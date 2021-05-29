package com.hefny.hady.marvelstudios.utils

/**
 * A generic class that contains data and status about loading this data.
 */
data class Resource<T>(
    var loading: Boolean = false,
    var error: Event<String>? = null,
    var data: Event<T>? = null,
    var message: String? = null
) {
    companion object {
        fun <T> loading(isLoading: Boolean): Resource<T> {
            return Resource(isLoading)
        }

        fun <T> error(message: String?): Resource<T> {
            return Resource(error = Event.responseEvent(message))
        }

        fun <T> data(data: T? = null, message: String? = null): Resource<T> {
            return Resource(data = Event.dataEvent(data), message = message)
        }

    }
}

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun toString(): String {
        return "Event(content=$content, hasBeenHandled=$hasBeenHandled)"
    }

    companion object {
        // we don't want an event if the data is null
        fun <T> dataEvent(data: T?): Event<T>? {
            data?.let {
                return Event(it)
            }
            return null
        }

        // we don't want an event if the response is null
        fun responseEvent(response: String?): Event<String>? {
            response?.let {
                return Event(response)
            }
            return null
        }
    }
}