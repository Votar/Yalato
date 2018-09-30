package io.bertalt.yalato.base

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject


abstract class BaseViewModel<V : BaseViewContract> : ViewModelContract<V>, ViewModel() {

    var disposables = CompositeDisposable()
    val loadingSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()
    val messagesSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    override fun getLoadingObservable(): Observable<Boolean> = loadingSubject
    override fun getMessageObservable(): Observable<Int> = messagesSubject

    override fun onStart() {
        if (disposables.isDisposed)
            disposables = CompositeDisposable()
    }

    override fun onStop() {
        if (disposables.isDisposed.not())
            disposables.dispose()
    }

}