package me.donlis.module_base.core

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<B : ViewDataBinding> : BaseActivity() {

    protected lateinit var binding: B

    override fun onCreateView() {
        binding = DataBindingUtil.setContentView<B>(this, getLayoutResId())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}