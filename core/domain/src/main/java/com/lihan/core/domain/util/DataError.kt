package com.lihan.core.domain.util

sealed interface DataError : Error {
    enum class Network : DataError {
        BAD_REQUEST,
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERVICE_UNAVAILABLE,
        SERIALIZATION,
        UNKNOWN,
        CANCELED
    }

    enum class Firebase: DataError {
        NOT_SUPPORT_CREDENTIAL,
        GET_CREDENTIAL_FAILED,
        FIREBASE_GET_DATA_FAILED,
        GET_SIGNING_CANCELLED,
        SIGNING_FAILED,
        UNKNOWN,
        UNKNOWN_USER,
        CANCELED,

    }

    enum class Local : DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }
}
