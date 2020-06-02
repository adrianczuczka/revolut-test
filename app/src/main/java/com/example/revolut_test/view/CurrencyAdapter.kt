package com.example.revolut_test.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut_test.R
import com.example.revolut_test.model.Currency
import com.example.revolut_test.util.CurrenciesDiffCallback
import com.example.revolut_test.util.inflate
import javax.inject.Inject

class CurrencyAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currencies: MutableList<Currency> = mutableListOf()

    var onCardClick: ((currencyName: String) -> Unit)? = null

    fun update(updatedCurrencies: List<Currency>) {
        val diff = DiffUtil.calculateDiff(CurrenciesDiffCallback(currencies, updatedCurrencies))
        currencies.clear()
        currencies.addAll(updatedCurrencies)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            CurrencyViewHolder(parent.inflate(R.layout.view_currency_card))

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            (holder as CurrencyViewHolder).bind(currencies[position], onCardClick)

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(currency: Currency, onCardClick: ((currencyName: String) -> Unit)?) {
            val orderCardView = itemView as CurrencyCardView
            orderCardView.bind(currency)
            orderCardView.setOnClickListener {
                onCardClick?.invoke(currency.name)
            }
        }
    }
}