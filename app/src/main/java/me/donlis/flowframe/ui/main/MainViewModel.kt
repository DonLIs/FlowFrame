package me.donlis.flowframe.ui.main

import me.donlis.lib_core.interfaces.IUiIntent
import me.donlis.lib_core.model.SuperData
import me.donlis.lib_core.vm.SuperViewModel


class MainViewModel : SuperViewModel<MainState, MainIntent>() {

    override fun initUiState(): MainState {
        return MainState(ListUiState.DEFAULT)
    }

    override fun handleIntent(uiIntent: IUiIntent) {
        when(uiIntent) {
            is MainIntent.GetList -> {
                launchWithApi(request = {
                    SuperData<List<String>>(code = 0)
                }, success = { data ->
                    updateUiState {
                        copy(listUiState = ListUiState.SUCCESS(data))
                    }
                }, failure = { e ->
                    updateUiState {
                        copy(listUiState = ListUiState.FAILURE(e))
                    }
                })
            }
        }
    }
}