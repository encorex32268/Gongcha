package com.lihan.core.presentation.ui

import com.lihan.core.domain.util.DataError
import com.lihan.core.presentation.ui.UiText.StringResource

fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> StringResource(R.string.error_disk_full)
        DataError.Local.NOT_FOUND -> StringResource(R.string.error_not_found)
        DataError.Local.UNKNOWN -> StringResource(R.string.error_unknown)
        DataError.Network.BAD_REQUEST -> StringResource(R.string.error_bad_request)
        DataError.Network.REQUEST_TIMEOUT -> StringResource(R.string.error_request_timeout)
        DataError.Network.UNAUTHORIZED -> StringResource(R.string.error_unauthorized)
        DataError.Network.FORBIDDEN -> StringResource(R.string.error_forbidden)
        DataError.Network.NOT_FOUND -> StringResource(R.string.error_not_found)
        DataError.Network.CONFLICT -> StringResource(R.string.error_conflict)
        DataError.Network.TOO_MANY_REQUESTS -> StringResource(R.string.error_too_many_requests)
        DataError.Network.NO_INTERNET -> StringResource(R.string.error_no_internet)
        DataError.Network.PAYLOAD_TOO_LARGE -> StringResource(R.string.error_payload_too_large)
        DataError.Network.SERVER_ERROR -> StringResource(R.string.error_server_error)
        DataError.Network.SERVICE_UNAVAILABLE -> StringResource(R.string.error_service_unavailable)
        DataError.Network.SERIALIZATION -> StringResource(R.string.error_serialization)
        DataError.Network.UNKNOWN -> StringResource(R.string.error_unknown)
        DataError.Network.CANCELED -> StringResource(R.string.error_canceled)
        DataError.Firebase.NOT_SUPPORT_CREDENTIAL -> StringResource(R.string.error_not_support_credential)
        DataError.Firebase.GET_CREDENTIAL_FAILED -> StringResource(R.string.error_get_credential_failed)
        DataError.Firebase.FIREBASE_GET_DATA_FAILED -> StringResource(R.string.error_firebase_get_data_failed)
        DataError.Firebase.GET_SIGNING_CANCELLED -> StringResource(R.string.error_get_signing_cancelled)
        DataError.Firebase.SIGNING_FAILED -> StringResource(R.string.error_signing_failed)
        DataError.Firebase.UNKNOWN -> StringResource(R.string.error_unknown)
        DataError.Firebase.UNKNOWN_USER -> StringResource(R.string.error_unknown_user)
        DataError.Firebase.CANCELED -> StringResource(R.string.error_canceled)
    }
}
