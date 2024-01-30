package com.lunch.lunchrecommendation.extensions

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun View?.onClick(action: (view:View) -> Unit) {
    this?.let {
        it.clicks()
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { action(this) }
    }
}