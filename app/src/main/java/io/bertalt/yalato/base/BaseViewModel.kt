package io.bertalt.yalato.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel<V : BaseViewContract> : ViewModelContract<V>, ViewModel() {

    var disposables = CompositeDisposable()

    override fun onStart() {
        if(disposables.isDisposed)
            disposables = CompositeDisposable()
    }

    override fun onStop() {
        if(disposables.isDisposed.not())
            disposables.dispose()
    }

}