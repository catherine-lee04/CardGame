package com.appknot.bentleymembership.base

import androidx.lifecycle.MutableLiveData
import com.appknot.core_rx.api.ApiResponse
import com.appknot.core_rx.base.RxBaseViewModel
import com.appknot.core_rx.extensions.CODE_SUCCESS
import com.appknot.core_rx.extensions.networkThread
import com.appknot.core_rx.util.SingleLiveEvent
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Response
import java.lang.RuntimeException

/**
 *
 * @author Ethan on 2021-05-26
 */
open class BaseViewModel : RxBaseViewModel() {
    val isLoading = SingleLiveEvent<Boolean>()
    val alertMessage = MutableLiveData<String>()
    val successToastMessage = MutableLiveData<String>()

    init {
        isLoading.value = false
    }

    /**
     * API code값 분기 실행을 위한 extensions
     */
    fun Single<Response<ApiResponse>>.codeApi(): Single<ApiResponse> =
        networkThread()
            .flatMap { response ->
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            when (it.code) {
                                CODE_SUCCESS -> { Single.just(it) }
                                else -> {
                                    Single.error(RuntimeException(Gson().toJson(it)))
                                }
                            }
                        }
                    }
                    else -> Single.error(
                        Throwable(
                            Gson().toJson(
                                ApiResponse().apply {
                                    code = ""
                                    msg = Msg().apply {
                                        en = ""
                                        ko = "네트워크 오류가 발생했습니다. 다시 시도해 주세요."
                                    }
                                }
                            )
                        )
                    )
                }
            }.onErrorResumeNext {
                Single.error(Throwable("네트워크 오류가 발생했습니다. 다시 시도해 주세요."))
            }
}