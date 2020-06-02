package com.example.revolut_test.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.revolut_test.R
import com.example.revolut_test.model.Currency
import kotlinx.android.synthetic.main.view_currency_card_merge.view.*

class CurrencyCardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.view_currency_card_merge, this, true)
    }

    fun bind(currency: Currency) {
        val androidCurrency: java.util.Currency = java.util.Currency.getInstance(currency.name)
        currencyCardViewTitleTextView.text = androidCurrency.displayName
        currencyCardViewSubtitleTextView.text = currency.name
    }
}