package com.appknot.cardgame.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.appknot.bentleymembership.base.BaseViewModel
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author Catherine on 2021-07-14
 */
class PlayViewModel : BaseViewModel() {
    val cardResource = ArrayList<Int>()
    val tryCount = ObservableField("0")
    val matchedCount = MutableLiveData(0)

    val tryAction: () -> Unit = {
        tryCount.set(
            ((tryCount.get() ?: "0").toInt() + 1).toString())
    }
    val matchedAction: () -> Unit = {
        matchedCount.value = matchedCount.value?.plus(1)
    }
}