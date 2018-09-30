package io.bertalt.yalato.base

import io.reactivex.disposables.CompositeDisposable


interface ViewModelContract<in V : BaseViewContract> {
    fun onStart()
    fun onStop()
}