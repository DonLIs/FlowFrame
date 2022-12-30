package me.donlis.lib_core.component

import androidx.fragment.app.Fragment

open class SuperLazyFragment : Fragment() {

    private var isLoaded = false

    private var visible = false

    fun isFragmentVisible(): Boolean {
        return if (isLoaded) visible else false
    }

    protected open fun onFragmentResume() {

    }

    protected open fun onFragmentPause() {

    }

    protected open fun onFragmentFirstVisible() {

    }

    override fun onResume() {
        super.onResume()
        dispatchVisible(true)
    }

    override fun onPause() {
        super.onPause()
        dispatchVisible(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoaded = false
    }

    private fun dispatchVisible(isVisible: Boolean) {
        this.visible = isVisible
        if (isVisible) {
            if (isLoaded) {
                onFragmentResume()
            } else if (!isHidden) {
                isLoaded = true
                onFragmentFirstVisible()
            }
        } else {
            if (isLoaded) {
                onFragmentPause()
            }
        }
    }

}