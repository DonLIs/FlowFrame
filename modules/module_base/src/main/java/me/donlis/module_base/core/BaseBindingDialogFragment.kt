package me.donlis.module_base.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Desc:DialogFragment - DataBinding
 */
open class BaseBindingDialogFragment<B : ViewDataBinding> : BaseDialogFragment() {

    protected lateinit var binding: B

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.bind<B>(view!!)!!
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            binding.unbind()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}