package io.bertalt.yalato.base

import android.support.v7.app.AppCompatActivity
import io.bertalt.yalato.utils.showMessage
import io.reactivex.disposables.CompositeDisposable

/**
 * Realize attach/detach functions for our presentation layer
 */
abstract class BaseActivity<in V : BaseViewContract, out T : ViewModelContract<V>>
    : AppCompatActivity(), BaseViewContract {
    /**
     * Base ViewModel
     */
    protected var disposables = CompositeDisposable()

    protected abstract fun getViewModel(): T

    open fun subscribe() {
        if (disposables.isDisposed)
            disposables = CompositeDisposable()
        disposables.add(getViewModel().getMessageObservable().subscribe { showMessage(it) })
    }

    protected fun dispose() {
        if (disposables.isDisposed.not())
            disposables.dispose()
    }

    override fun onStart() {
        super.onStart()
        getViewModel().onStart()
        subscribe()
    }

    override fun onStop() {
        super.onStop()
        getViewModel().onStop()
        dispose()
    }

    override fun showError(error: String?) {
        getRootView().showMessage(error)
    }

    override fun showError(stringResId: Int) {
        getRootView().showMessage(getString(stringResId))
    }

    override fun showMessage(srtResId: Int) {
        getRootView().showMessage(getString(srtResId))
    }

    override fun showMessage(message: String) {
        getRootView().showMessage(message)
    }
}
