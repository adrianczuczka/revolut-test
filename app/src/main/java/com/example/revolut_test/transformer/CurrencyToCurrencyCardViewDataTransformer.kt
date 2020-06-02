package com.example.revolut_test.transformer

import com.blongho.country_data.World
import com.example.revolut_test.model.Currency
import com.example.revolut_test.view.CurrencyCardViewData
import com.neovisionaries.i18n.CurrencyCode
import javax.inject.Inject

/*
* Sadly this class was too hard to test due to the World.getFlagOf() call, which needs to be preceded by the World.init(context: Context) call.
* Ideally, this would be replaced by an internal database of flags and icons.
*/
class CurrencyToCurrencyCardViewDataTransformer @Inject constructor() {
    fun transform(currency: Currency): CurrencyCardViewData {
        val androidCurrency: java.util.Currency = java.util.Currency.getInstance(currency.name)
        val currencyCode = CurrencyCode.getByCode(currency.name)
        val flag = World.getFlagOf(currencyCode.countryList[0].alpha3)
        return CurrencyCardViewData(
                androidCurrency.displayName,
                currency.name,
                currency.rate,
                flag
        )
    }
}