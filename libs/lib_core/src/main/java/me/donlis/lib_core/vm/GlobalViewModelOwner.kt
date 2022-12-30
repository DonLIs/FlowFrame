package me.donlis.lib_core.vm

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import me.donlis.lib_core.interfaces.ICleared

/**
 * 全局ViewModel
 */
class GlobalViewModelOwner : ViewModelStoreOwner, ICleared {

    companion object {
        private val viewModelStore by lazy { ViewModelStore() }
        val instance by lazy { GlobalViewModelOwner() }
    }

    override fun getViewModelStore(): ViewModelStore {
        return GlobalViewModelOwner.viewModelStore
    }

    override fun onCleared() {
        viewModelStore.clear()
    }
}