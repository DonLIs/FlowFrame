package me.donlis.lib_core.utils

import android.os.CountDownTimer
import androidx.lifecycle.*
import kotlin.math.roundToLong


/**
 * 定时器相关
 */

object TimerUtils {

    /**
     * 计时/计数任务（可正序 0-N，可倒序N-0）
     * @param lifecycleOwner 绑定的生命周期持有者
     * @param isVisiblePost 是否`可见状态`时才更新状态(可见状态==lifecycleOwner生命周期为可见状态)
     * @param isDesc 是否倒序 (false:0-totalCount , true:totalCount-0)
     * @param totalCount 总次数
     * @param countUnit 计时单位（毫秒）
     * @param onTick 单次的触发
     * @param onFinish 结束的触发
     * @return CountDownTimer
     */
    fun count(
        lifecycleOwner: LifecycleOwner,
        isVisiblePost: Boolean = true,
        isDesc: Boolean = false,
        totalCount: Long,
        countUnit: Long = 1000,
        onTick: ((value: Long) -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): TimerTask {
        val millisInFuture = totalCount * countUnit
        var countDownTimer: CountDownTimer? = null
        val observer = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                countDownTimer?.cancel()
            }
        }

        //发送通知
        fun onPost(num: Long) {
            if (num == millisInFuture + 1) {
                onFinish?.invoke()
            } else {
                if (isDesc) {
                    //倒叙
                    onTick?.invoke((num.toDouble() / countUnit).roundToLong())
                } else {
                    //正序
                    onTick?.invoke(((millisInFuture - num.toDouble()) / countUnit).roundToLong())
                }
            }
        }

        val liveData = MutableLiveData<Long>().apply {

            if (isVisiblePost) {
                observe(lifecycleOwner, Observer<Long> {
                    onPost(it)
                })
            } else {
                observeForever {
                    onPost(it)
                }
            }
        }

        countDownTimer = object : CountDownTimer(millisInFuture, countUnit) {
            override fun onFinish() {
                lifecycleOwner.lifecycle.removeObserver(observer)
                liveData.postValue(millisInFuture + 1)
            }

            override fun onTick(millisUntilFinished: Long) {
                liveData.postValue(millisUntilFinished)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        countDownTimer.start()
        return object : TimerTask {
            override fun cancel() {
                lifecycleOwner.lifecycle.removeObserver(observer)
                countDownTimer.cancel()
            }
        }
    }


    /**
     * 无限轮询任务
     * @param lifecycleOwner 绑定的生命周期持有者
     * @param isVisiblePost 是否`可见状态`时才更新状态(可见状态==lifecycleOwner生命周期为可见状态)
     * @param countInterval 间隔时长（毫秒）
     * @param onTick 单次的触发
     * @return CountDownTimer
     */
    fun loop(
        lifecycleOwner: LifecycleOwner,
        isVisiblePost: Boolean = true,
        countInterval: Long = 1000,
        onTick: ((value: Long) -> Unit)? = null
    ): TimerTask {
        val liveData = MutableLiveData<Long>().apply {
            if (isVisiblePost) {
                observe(lifecycleOwner, Observer<Long> {
                    onTick?.invoke(it)
                })
            } else {
                observeForever {
                    onTick?.invoke(it)
                }
            }
        }

        var countDownTimer: CountDownTimer? = null

        val observer = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                lifecycleOwner.lifecycle.removeObserver(this)
                countDownTimer?.cancel()
            }
        }

        val millisInFuture = Long.MAX_VALUE - Long.MAX_VALUE % countInterval
        countDownTimer =
            object : CountDownTimer(millisInFuture, countInterval) {
                override fun onFinish() {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                    start()
                }

                override fun onTick(millisUntilFinished: Long) {
                    liveData.postValue(millisInFuture - millisUntilFinished)
                }
            }

        lifecycleOwner.lifecycle.addObserver(observer)
        countDownTimer.start()
        return object : TimerTask {
            override fun cancel() {
                lifecycleOwner.lifecycle.removeObserver(observer)
                countDownTimer.cancel()
            }
        }
    }
}


/**
 * 定时任务
 */
interface TimerTask {
    //取消
    fun cancel()
}
