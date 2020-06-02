package com.example.revolut_test.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.revolut_test.R
import com.example.revolut_test.util.CurrenciesDiffCallback
import com.example.revolut_test.util.inflate
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.LinkedList
import javax.inject.Inject

class CurrencyAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currencies: LinkedList<CurrencyCardViewData> = LinkedList()
    private var highlightedItemWithOldIndex: Pair<CurrencyCardViewData?, Int> = null to -1
    private val amountsUpdater: BehaviorSubject<String> = BehaviorSubject.create()

    var onCardClick: (() -> Unit)? = null

    fun update(updatedCurrencies: List<CurrencyCardViewData>) {
        val diff = DiffUtil.calculateDiff(CurrenciesDiffCallback(currencies, updatedCurrencies))
        currencies.clear()
        currencies.addAll(updatedCurrencies)
        if (highlightedItemWithOldIndex.first == null) {
            highlightedItemWithOldIndex = highlightedItemWithOldIndex.copy(updatedCurrencies.first(), 0)
        }
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            CurrencyViewHolder(parent.inflate(R.layout.view_currency_card_impl))

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            (holder as CurrencyViewHolder).bind(currencies[position], ::focusItem, amountsUpdater)

    private fun focusItem(data: CurrencyCardViewData) {
        highlightedItemWithOldIndex.first?.let { it ->
            currencies.remove(it)
            currencies.add(highlightedItemWithOldIndex.second, it)
            notifyItemMoved(0, highlightedItemWithOldIndex.second)
        }
        highlightedItemWithOldIndex = data to currencies.indexOf(data)
        val tempIndex = currencies.indexOf(data)
        currencies.remove(data)
        currencies.addFirst(data)
        notifyItemMoved(tempIndex, 0)
        onCardClick?.invoke()
    }

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            currency: CurrencyCardViewData,
            onCardClick: ((data: CurrencyCardViewData) -> Unit),
            amountsUpdater: BehaviorSubject<String>
        ) {
            val orderCardView = itemView as CurrencyCardView
            orderCardView.bind(currency, onCardClick, amountsUpdater)
        }
    }
}