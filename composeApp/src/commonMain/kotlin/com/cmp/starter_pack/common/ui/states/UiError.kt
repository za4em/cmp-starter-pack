package com.cmp.starter_pack.common.ui.states

import com.cmp.starter_pack.common.result.Result
import com.cmp.starter_pack.common.result.ResultCombined

data class UiError(
    val throwable: Throwable,
    val dataLoaded: Boolean,
)

fun <T> Result<T>.toUiError(): UiError? =
    error?.let { UiError(throwable = it, dataLoaded = data != null) }

fun ResultCombined.toUiError(): UiError? {
    val throwable = results.firstNotNullOfOrNull { result -> result.error }

    return throwable?.let {
        UiError(
            throwable = it,
            dataLoaded = results.all { result -> result.data != null || result.isSuccess },
        )
    }
}