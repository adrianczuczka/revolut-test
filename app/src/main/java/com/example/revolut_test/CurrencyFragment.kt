package com.example.revolut_test

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.revolut_test.injection.RevolutTestApplication
import com.example.revolut_test.view.CurrencyAdapter
import com.example.revolut_test.viewmodel.currencyfragment.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currency.*
import javax.inject.Inject

class CurrencyFragment : Fragment() {

    @Inject
    lateinit var currencyAdapter: CurrencyAdapter

    @Inject
    lateinit var viewModel: CurrencyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_currency, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        currencyAdapter.onCardClick = { layoutManager.scrollToPositionWithOffset(0, 0) }
        currencyFragmentRecyclerView.layoutManager = layoutManager
        currencyFragmentRecyclerView.adapter = currencyAdapter
        viewModel.getCurrencies("EUR").observe(this, Observer { list ->
            currencyAdapter.update(list.map { currency -> viewModel.transformCurrencies(currency) })
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appContext = activity?.applicationContext as RevolutTestApplication
        appContext.appComponent.inject(this)
    }

    companion object {
        fun newInstance(): CurrencyFragment = CurrencyFragment()
    }
}