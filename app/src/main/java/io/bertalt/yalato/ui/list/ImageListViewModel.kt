package io.bertalt.yalato.ui.list

import io.bertalt.yalato.R
import io.bertalt.yalato.api.ApiUnsplash
import io.bertalt.yalato.api.GetPhotosService
import io.bertalt.yalato.api.response.PhotoRest
import io.bertalt.yalato.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ImageListViewModel(val api: GetPhotosService = ApiUnsplash.service) : BaseViewModel<ImageListContract.View>(), ImageListContract.ViewModel {

    val listSubject: BehaviorSubject<List<PhotoRest>> = BehaviorSubject.create()
    val messagesSubject: BehaviorSubject<Int> = BehaviorSubject.create()
    val loadingSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun getListObservable(): Observable<List<PhotoRest>> = listSubject
    override fun getMessageObservable(): Observable<Int> = messagesSubject
    override fun getLoadingSubject(): Observable<Boolean> = loadingSubject


    val repository: PhotosRepository = PhotosRepository(api)
    var lastPage = 0

    override fun onStart() {
        super.onStart()

        listSubject.onNext(repository.localPhoto)
        if (lastPage == 0)
            fetchPhotos()
    }

    private fun fetchPhotos() {
        loadingSubject.onNext(true)
        lastPage += 1
        disposables.add(repository.loadPhotos(lastPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchConsumer, errorConsumer))
    }

    override fun submitSearch(query: String?) {

    }

    override fun onRefresh() {

    }

    override fun onRetryClick() {

    }

    private val errorConsumer: Consumer<Throwable> = Consumer<Throwable> {
        messagesSubject.onNext(R.string.common_error)
        loadingSubject.onNext(false)
    }

    private val fetchConsumer = Consumer<List<PhotoRest>> { list ->
        repository.localPhoto.addAll(list)
        listSubject.onNext(repository.localPhoto)
    }

}