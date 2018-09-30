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

    }

    interface ViewModel : ViewModelContract<View> {
        fun submitSearch(query: String?)
        fun onRetryClick()

        fun getListObservable(): Observable<List<PhotoRest>>
        fun onScrollStateChanged(itemCount: Int, lastVisiblePosition: Int, newState: Int)

    }

}