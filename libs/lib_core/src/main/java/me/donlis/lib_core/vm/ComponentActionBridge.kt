package me.donlis.lib_core.vm

import me.donlis.lib_core.common.ComponentIntent


interface ComponentActionBridge {
    /**
     ** 发送组件命令
     */
    fun sendAction(action: ComponentIntent)
}