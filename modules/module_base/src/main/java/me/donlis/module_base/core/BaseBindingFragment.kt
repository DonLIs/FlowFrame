package me.donlis.module_base.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 *
 */
abstract class BaseBindingFragment<B : ViewDataBinding> : BaseFragment() {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = getLayoutResId()
        return if (layoutId != View.NO_ID) {
            binding = DataBindingUtil.inflate<B>(inflater, layoutId, container, false)
            binding.lifecycleOwner = viewLifecycleOwner
            binding.root
        } else {
            binding = DataBindingUtil.bind<B>(createView())!!
            binding.root
        }
    }

    override fun createView(): View {
        return View(context)
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