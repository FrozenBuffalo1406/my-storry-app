package com.dicoding.mystoryapp.view.costumView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.mystoryapp.R

class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val editTextList = mutableListOf<EditText>()
    private var allFieldsFilled = false

    private var txtColor = ContextCompat.getColor(context, android.R.color.background_light)
    private var enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
    private var disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable

    fun linkEditText(editText: EditText) {
        editTextList.add(editText)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text= if (allFieldsFilled) STRING else "Isi Dulu"
    }

    private fun updateButtonState() {
        allFieldsFilled = true
        for (editText in editTextList) {
            if (TextUtils.isEmpty(editText.text.toString())) {
                allFieldsFilled = false
                break
            }
        }
        invalidate()
    }

    companion object  {
        var STRING = "STRING"
    }
}