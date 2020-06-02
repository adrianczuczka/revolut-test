package com.example.revolut_test.util

import androidx.recyclerview.widget.DiffUtil
import com.example.revolut_test.view.CurrencyCardViewData

class CurrenciesDiffCallback(
    private val oldItems: List<CurrencyCardViewData>,
    private val newItems: List<CurrencyCardViewData>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

}
