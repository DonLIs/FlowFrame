package me.donlis.lib_core.component

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.donlis.lib_core.common.ComponentIntent
import me.donlis.lib_core.common.IvmComponent
import me.donlis.lib_core.interfaces.ILoading

open class SuperActivity : AppCompatActivity(), IvmComponent, ILoading {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitializeBefore()
        onInitialize()
        onInitializeAfter()
    }

    override fun onInitializeBefore() {
        super.onInitializeBefore()
        onCreateView()
    }

    protected open fun getLayoutResId(): Int {
        return View.NO_ID
    }

    protected open fun onCreateView() {
        val layoutId = getLayoutResId()
        if (layoutId != View.NO_ID) {
            setContentView(layoutId)
        }
    }

    override fun onAction(action: ComponentIntent) {
        when(action) {
            is ComponentIntent.ShowLoading -> {
                showLoading(action.message)
            }
            is ComponentIntent.DismissLoading -> {
                dismissLoading()
            }
            is ComponentIntent.Toast -> {

            }
        }
    }

    override fun showLoading(message: CharSequence?) {

    }

    override fun dismissLoading() {

    }
}