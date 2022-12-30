package me.donlis.flowframe.ui.main

import me.donlis.lib_core.interfaces.IUiState

data class MainState(val listUiState: ListUiState): IUiState

sealed class ListUiState {
    object DEFAULT : ListUiState()
    data class SUCCESS(val data: List<String>): ListUiState()
    data class FAILURE(val throwable: Throwable): ListUiState()
}