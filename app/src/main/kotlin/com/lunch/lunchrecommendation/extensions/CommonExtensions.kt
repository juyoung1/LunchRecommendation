package com.lunch.lunchrecommendation.extensions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Intent
inline fun <reified T : Parcelable> Intent.getCompatibleParcelableExtra(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
    }
}

/**
 * Double 소수점 자릿수 자르기
 *
 * @param   decimalPlaces   소수점 자릿수
 *
 * @return  String
 */
fun Double?.trimDecimalPlaces(decimalPlaces: Int): String {

    if ( this == null ) return ""

    return String.format("%.${decimalPlaces}f", this)
}
fun Float?.trimDecimalPlaces(decimalPlaces: Int): String {

    if ( this == null ) return ""

    return String.format("%.${decimalPlaces}f", this)
}

/**
 * 키보드 올리기
 */
fun EditText.focusAndShowKeyboard(context: Context) {

    CoroutineScope(Dispatchers.Main).launch {
        delay(50)

        requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this@focusAndShowKeyboard, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * 키보드 올리기
 */
fun AppCompatEditText.focusAndShowKeyboard(context: Context) {

    CoroutineScope(Dispatchers.Main).launch {
        delay(50)

        requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this@focusAndShowKeyboard, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Boolean.toGoneOrVisible(): Int {
    return if (this) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun Boolean.toVisibleOrGone(): Int {
    return if (this) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Boolean.toVisibleOrInvisible(): Int {
    return if (this) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun Boolean.toInVisibleOrVisible(): Int {
    return if (this) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

fun View.ripple(): View {

    val value = TypedValue()
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, value, true)
    setBackgroundResource(value.resourceId)
    isFocusable = true
    return this
}

fun Int.toDp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
)