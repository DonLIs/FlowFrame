package me.donlis.lib_utils

import java.util.*

object DeviceUtil {

    private var uuid: String? = null

    private fun createUUid(): String {
        return UUID.randomUUID().toString()
    }

    fun init() {
        uuid = createUUid()
    }

    fun getUUid(): String {
        return uuid ?: createUUid().also { uuid = it }
    }

}