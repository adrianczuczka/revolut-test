package com.example.revolut_test.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut_test.R
import com.example.revolut_test.util.inflate
import java.util.LinkedList
import javax.inject.Inject

class CurrencyAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

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
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            CurrencyViewHolder(parent.inflate(R.layout.view_currency_card_impl))

    override fun getItemCount(): Int = currencies.size

    override fun getItemId(position: Int): Long {
        return currencies[position].title.hashCode().toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CurrencyViewHolder).bind(
                currency = currencies[position],
                amount = currentAmounts[position],
                onCardClick = ::focusItem,
                onAmountUpdated = {
                    highlightedAmount = it
                },
                isHighlighted = position == 0,
                amountIfHighlighted = highlightedAmount
        )
    }

    private fun focusItem(data: CurrencyCardViewData) {
        highlightedItemWithOldIndex.first?.let { highlightedData ->
            moveItem(highlightedData, highlightedItemWithOldIndex.second)
        }
        val index = currencies.indexOf(data)
        highlightedItemWithOldIndex = data to index
        highlightedAmount = currentAmounts[index]
        moveItem(data, 0)
        notifyItemChanged(0)
    }

    private fun moveItem(data: CurrencyCardViewData, index: Int) {
        val tempIndex = currencies.indexOf(data)
        val amount = currentAmounts[tempIndex]
        currencies.removeAt(tempIndex)
        currentAmounts.removeAt(tempIndex)
        currencies.add(index, data)
        currentAmounts.add(index, amount)
        notifyItemMoved(tempIndex, index)
    }

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            currency: CurrencyCardViewData,
            amount: Double?,
            onCardClick: ((data: CurrencyCardViewData) -> Unit),
            onAmountUpdated: (Double?) -> Unit,
            isHighlighted: Boolean,
            amountIfHighlighted: Double?
        ) {
            val orderCardView = itemView as CurrencyCardView
            orderCardView.bind(currency, amount, onCardClick, onAmountUpdated, isHighlighted, amountIfHighlighted)
        }
    }
}