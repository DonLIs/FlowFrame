package me.donlis.lib_core.ktx

import androidx.lifecycle.LifecycleOwner
import me.donlis.lib_core.utils.TimerTask
import me.donlis.lib_core.utils.TimerUtils


/**
 * 计时/计数任务（可正序 0-N，可倒序N-0）
 * @param isVisiblePost 是否`可见状态`时才更新状态(可见状态==lifecycleOwner生命周期为可见状态)
 * @param isDesc 是否倒序 (false:0-totalCount , true:totalCount-0)
 * @param totalCount 总次数
 * @param countUnit 计时单位（毫秒）
 * @param onTick 单次的触发
 * @param onFinish 结束的触发
 * @return CountDownTimer
 */
fun LifecycleOwner.countTask(
    isVisiblePost: Boolean = true,
    isDesc: Boolean = false,
    totalCount: Long,
    countUnit: Long = 1000,
    onTick: ((value: Long) -> Unit)? = null,
    onFinish: (() -> Unit)? = null
): TimerTask {
    return TimerUtils.count(
        lifecycleOwner = this,
        isVisiblePost = isVisiblePost,
        isDesc = isDesc,
        totalCount = totalCount,
        countUnit = countUnit,
        onTick = onTick,
        onFinish = onFinish
    )
}


/**
 * 无限轮询任务
 * @param isVisiblePost 是否`可见状态`时才更新状态(可见状态==lifecycleOwner生命周期为可见状态)
 * @param countInterval 间隔时长（毫秒）
 * @param onTick 单次的触发
 * @return CountDownTimer
 */
fun LifecycleOwner.loopTask(
    isVisiblePost: Boolean = true,
    countInterval: Long = 1000,
    onTick: ((value: Long) -> Unit)? = null
): TimerTask {
    return TimerUtils.loop(
        lifecycleOwner = this,
        isVisiblePost = isVisiblePost,
        countInterval = countInterval,
        onTick = onTick
    )
}