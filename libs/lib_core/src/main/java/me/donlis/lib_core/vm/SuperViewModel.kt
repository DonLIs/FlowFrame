package me.donlis.lib_core.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.donlis.lib_core.common.SuperCallBack
import me.donlis.lib_core.exceptions.ApiException
import me.donlis.lib_core.exceptions.NetWorkException
import me.donlis.lib_core.interfaces.ILoading
import me.donlis.lib_core.interfaces.IUiIntent
import me.donlis.lib_core.interfaces.IUiState
import me.donlis.lib_core.ktx.dismissLoadingInMain
import me.donlis.lib_core.ktx.showLoadingInMain
import me.donlis.lib_core.model.SuperData
import kotlin.coroutines.CoroutineContext

abstract class SuperViewModel<UiState : IUiState, UiIntent: IUiIntent> : ViewModel() {

    //-------------------State-----------------------

    private val _uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(initUiState())

    val uiStateFlow: StateFlow<UiState> = _uiStateFlow

    protected abstract fun initUiState(): UiState

    protected fun updateUiState(state: UiState.() -> UiState) {
        _uiStateFlow.update { state(_uiStateFlow.value) }
    }

    //-------------------State-----------------------

    //-------------------Intent-----------------------

    private val _uiIntentFlow: Channel<UiIntent> = Channel()

    val uiIntentFlow: Flow<UiIntent> = _uiIntentFlow.receiveAsFlow()

    protected abstract fun handleIntent(uiIntent: IUiIntent)

    init {
        viewModelScope.launch {
            uiIntentFlow.collect {
                handleIntent(it)
            }
        }
    }

    fun sendUiIntent(uiIntent: UiIntent) {
        viewModelScope.launch {
            _uiIntentFlow.send(uiIntent)
        }
    }

    //-------------------Intent-----------------------

    //-------------------协程-----------------------

    suspend inline fun <T> collect(
        crossinline map: (value: UiState) -> T,
        crossinline callback: (value: T) -> Unit
    ) {
        uiStateFlow.map(map).distinctUntilChanged().collect(callback)
    }

    /**
     * 启动一个协程任务 (Flow)
     * @param request 请求任务
     * @param loading loading
     * @param success 成功
     * @param failure 失败
     * @param context 默认IO
     * @param start 默认启动方式
     */
    fun <T> launchWithFlow(
        request: suspend () -> Flow<T>,
        loading: ILoading? = null,
        success: ((T) -> Unit)? = null,
        failure: ((Throwable) -> Unit)? = null,
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
    ): Job {
        return viewModelScope.launch(context = context, start = start) {
            request.invoke().onStart {
                loading?.showLoadingInMain()
            }.onCompletion {
                loading?.dismissLoadingInMain()
            }.catch {
                val exception = analysisException(it)
                failure?.invoke(exception)
            }.collectLatest { result ->
                success?.invoke(result)
            }
        }
    }

    /**
     * 启动一个协程任务 (Flow)
     * @param request 请求任务
     * @param loading loading
     * @param success 成功
     * @param failure 失败
     * @param context 默认IO
     * @param start 默认启动方式
     */
    fun <T> launchWithApi(
        request: suspend () -> SuperData<T>,
        loading: ILoading? = null,
        success: ((T) -> Unit)? = null,
        failure: ((Throwable) -> Unit)? = null,
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
    ): Job {
        return viewModelScope.launch(context = context, start = start) {
            try {
                loading?.showLoadingInMain()
                val data = request.invoke()
                if (data.isSuccess()) {
                    data.data?.let { success?.invoke(it) }
                } else {
                    data.message?.let { failure?.invoke(Throwable(it)) }
                }
                loading?.dismissLoadingInMain()
            } catch (e: Exception) {
                val exception = analysisException(e)
                failure?.invoke(exception)
                loading?.dismissLoadingInMain()
            }
        }
    }

    /**
     * 启动一个协程任务 (Flow)
     * @param callBack callBack
     * @param context 默认IO
     * @param start 默认启动方式
     * @param block 执行的任务
     */
    fun <T> launch(
        callBack: SuperCallBack<T>? = null,
        loading: ILoading? = null,
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend () -> Flow<T>
    ): Job {
        return viewModelScope.launch(context = context, start = start, block = {
            handleFlow(block.invoke(), callBack, loading)
        })
    }

    /**
     * 处理流（Flow）结果
     * 耗时操作
     */
    private suspend fun <T> handleFlow(
        flow: Flow<T>, callBack: SuperCallBack<T>? = null, loading: ILoading? = null
    ) {
        return flow.onStart {
            loading?.showLoadingInMain()
        }.onCompletion {
            loading?.dismissLoadingInMain()
        }.catch {
            val exception = analysisException(it)
            callBack?.onFailure(exception)
        }.collectLatest { result ->
            callBack?.onSuccess(result)
        }
    }

    //-------------------协程-----------------------

    //-------------------响应&异常-----------------------

    /**
     * 对异常 进行分析处理
     */
    protected open fun analysisException(
        throwable: Throwable
    ): Throwable {
        //
        return when (throwable) {
            is ApiException -> {
                //接口不成功异常
                throwable
            }
            is NetWorkException -> {
                //网络异常
                throwable
            }
            else -> throwable
        }
    }

    //-------------------响应&异常-----------------------

}