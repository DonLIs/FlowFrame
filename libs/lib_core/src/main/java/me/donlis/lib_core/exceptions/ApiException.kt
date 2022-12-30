package me.donlis.lib_core.exceptions

import me.donlis.lib_core.model.SuperResponse


open class ApiException(val response: SuperResponse<*>) : SuperException(
    code = response.getCode() ?: 0, message = response.getMessage()
) {
}