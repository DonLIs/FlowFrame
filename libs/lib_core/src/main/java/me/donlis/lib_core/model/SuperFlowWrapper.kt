package me.donlis.lib_core.model

data class SuperFlowWrapper<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val throwable: Throwable? = null,
)