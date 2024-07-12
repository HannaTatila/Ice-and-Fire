package com.example.iceandfire.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater.from
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.example.iceandfire.R
import com.example.iceandfire.databinding.ComponentItemDetailsDataBinding

private const val DATA_UNAVAILABLE = "-"

class ItemDetailsData @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = ComponentItemDetailsDataBinding.inflate(from(context), this)

    init {
        orientation = VERTICAL
        parseAttributes()
    }

    private fun parseAttributes() {
        context.withStyledAttributes(attributeSet, R.styleable.ItemDetailsData) {
            setLabel(this.getString(R.styleable.ItemDetailsData_itemDetailsData_label).orEmpty())
            val value = this.getString(R.styleable.ItemDetailsData_itemDetailsData_value).orEmpty()
            if (value.isNotEmpty()) setValue(value)
        }
    }

    fun setValue(value: String) {
        binding.textValue.text = safeData(value)
    }

    fun setLabel(label: String) {
        if (label.isNotEmpty()) binding.textLabel.text = label
    }

    private fun safeData(value: String): String {
        return value.ifEmpty { DATA_UNAVAILABLE }
    }

}