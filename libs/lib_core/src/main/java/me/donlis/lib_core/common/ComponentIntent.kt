package me.donlis.lib_core.common

import me.donlis.lib_core.interfaces.IUiIntent


open class ComponentIntent : IUiIntent {

    class ShowLoading(val message: CharSequence? = null): ComponentIntent()

    class DismissLoading: ComponentIntent()

    class Toast(val message: CharSequence? = null): ComponentIntent()

    class Jump(val path: String): ComponentIntent()

}