package com.example.revolut_test.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.revolut_test.R
import kotlinx.android.synthetic.main.view_currency_card_merge.view.*

class CurrencyCardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private var textWatcher: TextWatcher? = null

    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.view_currency_card_merge, this, true)
    }

    fun bind(
        data: CurrencyCardViewData,
        amount: Double?,
        onCardClick: (CurrencyCardViewData) -> Unit,
        onAmountUpdated: (Double?) -> Unit,
        isHighlighted: Boolean,
        amountIfHighlighted: Double?
    ) {
        currencyCardViewTitleTextView.text = data.title
        currencyCardViewSubtitleTextView.text = data.subtitle
        setOnClickListener {
            onCardClick.invoke(data)
            currencyCardViewAmountEditText.requestFocus()
        }
        currencyCardViewAmountEditText.removeTextChangedListener(textWatcher)
        if (!isHighlighted) {
            currencyCardViewAmountEditText.setText(amount?.toString() ?: "")
        } else if (currencyCardViewAmountEditText.text.isEmpty()) {
            currencyCardViewAmountEditText.setText(amountIfHighlighted?.toString() ?: "")
        }
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isHighlighted) {
                    val inputtedAmount = p0?.toString()?.toDoubleOrNull()
                    onAmountUpdated(inputtedAmount)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        currencyCardViewAmountEditText.addTextChangedListener(textWatcher)
        Glide.with(this).load(data.flag).into(currencyCardViewCountryImageView)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        currencyCardViewAmountEditText.removeTextChangedListener(textWatcher)
    }
}