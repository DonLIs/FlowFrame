package me.donlis.flowframe.ui.main

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import me.donlis.flowframe.R
import me.donlis.lib_core.component.SuperActivity

class MainActivity : SuperActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onInitialize() {
        super.onInitialize()

    }

    override fun onInitializeAfter() {
        super.onInitializeAfter()
        lifecycleScope.launchWhenStarted {
            viewModel.collect(map = { it.listUiState }, callback = {
                when(it) {
                    is ListUiState.DEFAULT -> {}
                    is ListUiState.SUCCESS -> {}
                    is ListUiState.FAILURE -> {}
                }
            })
        }

    }

}