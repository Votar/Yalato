package io.bertalt.yalato.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


interface ViewModelContract<in V : BaseViewContract> {
    fun onStart()
    fun onStop()
    fun getLoadingObservable(): Observable<Boolean>
    fun getMessageObservable(): Observable<Int>


}