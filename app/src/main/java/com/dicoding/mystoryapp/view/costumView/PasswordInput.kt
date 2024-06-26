package com.dicoding.mystoryapp.view.costumView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.mystoryapp.R

class PasswordInput (
    context: Context, attrs: AttributeSet
) : AppCompatEditText(context,attrs), View.OnTouchListener {

    private var clearButtonImage: Drawable

    init {
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24) as Drawable
        setOnTouchListener(this)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    error = if(it.length < 8) {
                        context.getString(R.string.pw_len_error)
                    } else {
                        null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }
        })
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Masukkan Password Anda"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun showClearButton() {
        setButtonDrawables(endOfTheText = clearButtonImage)
    }
    private fun hideClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                if (event != null) {
                    when {
                        event.x < clearButtonEnd -> isClearButtonClicked = true
                    }
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                if (event != null) {
                    when {
                        event.x > clearButtonStart -> isClearButtonClicked = true
                    }
                }
            }
            if (isClearButtonClicked) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

}