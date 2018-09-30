package io.bertalt.yalato.ui.list

import io.bertalt.yalato.api.response.PhotoRest
import io.bertalt.yalato.base.BaseViewContract
import io.bertalt.yalato.base.ViewModelContract
import io.reactivex.Observable

/**
 * Created by beretta on 12.10.2017.
 */
interface ImageListContract {
    interface View : BaseViewContract {
        fun showProgress()
        fun hideProgress()
        fun showEmptyView()
        fun hideEmptyView()
        fun bindResult(result: List<PhotoRest>)
    }

    interface ViewModel : ViewModelContract<View> {
        fun submitSearch(query: String?)
        fun onRefresh()
        fun onRetryClick()

        fun getListObservable(): Observable<List<PhotoRest>>
        fun getMessageObservable(): Observable<Int>
        fun getLoadingSubject(): Observable<Boolean>

    }

}