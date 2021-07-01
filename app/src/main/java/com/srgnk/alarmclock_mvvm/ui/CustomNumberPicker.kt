package com.srgnk.alarmclock_mvvm.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.srgnk.alarmclock_mvvm.R


class CustomNumberPicker : NumberPicker {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        processXmlAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        processXmlAttributes(attrs, defStyleAttr)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        processXmlAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun processXmlAttributes(attrs: AttributeSet, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.MyNumberPicker, defStyleAttr, defStyleRes)

        try {
            this.minValue = attributes.getInt(R.styleable.MyNumberPicker_minValue, 0)
            this.maxValue = attributes.getInt(R.styleable.MyNumberPicker_maxValue, 0)
            this.value = attributes.getInt(R.styleable.MyNumberPicker_defaultValue, 0)
        } finally {
            attributes.recycle()
        }
    }

    companion object {
        @BindingAdapter("custom:defaultValue")
        @JvmStatic
        fun setValue(view: NumberPicker, value: Int) {
            if (view.value != value) {
                view.value = value
            }
        }

        @InverseBindingAdapter(attribute = "defaultValue")
        @JvmStatic
        fun getValue(view: NumberPicker): Int {
            return view.value
        }

        @BindingAdapter("app:defaultValueAttrChanged")
        @JvmStatic fun setListeners(
            view: NumberPicker,
            attrChange: InverseBindingListener
        ) {
            view.setOnScrollListener { _, _ ->
                attrChange.onChange()
            }
        }
    }
}