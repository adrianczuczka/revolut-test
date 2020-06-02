package com.example.revolut_test.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.revolut_test.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.view_currency_card_merge.view.*

class CurrencyCardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        LayoutInflater
                .from(context)
                .inflate(R.layout.view_currency_card_merge, this, true)
    }

    fun bind(data: CurrencyCardViewData, amountsUpdater: BehaviorSubject<String>) {
        currencyCardViewTitleTextView.text = data.title
        currencyCardViewSubtitleTextView.text = data.subtitle
        compositeDisposable.add(
                amountsUpdater
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val amount = it.toDoubleOrNull()
                            if (!currencyCardViewAmountEditText.hasFocus()) {
                                currencyCardViewAmountEditText.setText(
                                        if (amount != null) {
                                            (amount * data.rate).toString()
                                        } else {
                                            ""
                                        }
                                )
                            }
                        }
        )
        currencyCardViewAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (currencyCardViewAmountEditText.hasFocus()) {
                    amountsUpdater.onNext(p0?.toString() ?: "")
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        Glide.with(this).load(data.flag).into(currencyCardViewCountryImageView)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        compositeDisposable.clear()
    }
}