package com.example.revolut_test.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut_test.R
import com.example.revolut_test.util.inflate
import java.util.LinkedList
import javax.inject.Inject

class CurrencyAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currencies: LinkedList<CurrencyCardViewData> = LinkedList()
    private val currentAmounts: LinkedList<Double?> = LinkedList()
    private var highlightedItemWithOldIndex: Pair<CurrencyCardViewData?, Int> = null to -1
    private var highlightedAmount: Double? = null

    var onCardClick: (() -> Unit)? = null

    fun update(updatedCurrencies: List<CurrencyCardViewData>) {
        currencies.clear()
        currentAmounts.clear()
        if (highlightedItemWithOldIndex.first == null) {
            highlightedItemWithOldIndex = highlightedItemWithOldIndex.copy(updatedCurrencies.first(), 0)
        }
        updatedCurrencies.forEach {
            if (it.title == highlightedItemWithOldIndex.first?.title) {
                currencies.addFirst(it)
                currentAmounts.addFirst(highlightedAmount)
            } else {
                currencies += it
                currentAmounts += highlightedAmount?.div(highlightedItemWithOldIndex.first!!.rate)?.times(it.rate)
            }
        }
        notifyItemRangeChanged(1, currencies.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            CurrencyViewHolder(parent.inflate(R.layout.view_currency_card_impl))

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            holder.setIsRecyclable(false)
        }
        (holder as CurrencyViewHolder).bind(
                currency = currencies[position],
                amount = currentAmounts[position],
                onCardClick = ::focusItem,
                onAmountUpdated = {
                    highlightedAmount = it
                },
                isHighlighted = position == 0
        )
    }

    private fun focusItem(data: CurrencyCardViewData) {
        highlightedItemWithOldIndex.first?.let { it ->
            val index = currencies.indexOf(it)
            currencies.removeAt(index)
            currentAmounts.removeAt(index)
            currencies.add(highlightedItemWithOldIndex.second, it)
            notifyItemMoved(0, highlightedItemWithOldIndex.second)
        }
        val currencyIndex = currencies.indexOf(data)
        highlightedItemWithOldIndex = data to currencyIndex
        highlightedAmount = currentAmounts[currencyIndex]
        val tempIndex = currencies.indexOf(data)
        currencies.remove(data)
        currentAmounts.removeAt(tempIndex)
        currencies.addFirst(data)
        currentAmounts.addFirst(highlightedAmount)
        notifyItemMoved(tempIndex, 0)
        notifyItemChanged(0)
        onCardClick?.invoke()
    }

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            currency: CurrencyCardViewData,
            amount: Double?,
            onCardClick: ((data: CurrencyCardViewData) -> Unit),
            onAmountUpdated: (Double?) -> Unit,
            isHighlighted: Boolean
        ) {
            val orderCardView = itemView as CurrencyCardView
            orderCardView.bind(currency, amount, onCardClick, onAmountUpdated, isHighlighted)
        }
    }
}