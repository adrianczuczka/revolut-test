package com.example.revolut_test.view

import android.content.Context
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

    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.view_currency_card_merge, this, true)
    }

    fun bind(data: CurrencyCardViewData) {
        currencyCardViewTitleTextView.text = data.title
        currencyCardViewSubtitleTextView.text = data.subtitle
        Glide.with(this).load(data.flag).into(currencyCardViewCountryImageView)
    }
}