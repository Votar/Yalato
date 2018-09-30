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

    override fun getListObservable(): Observable<List<PhotoRest>> = listSubject


    val galleryRepository: PhotosRepository = PhotosRepository(api)
    val searchRepositories = HashMap<String, PhotosRepository>()

    var lastPage = 0

    override fun onStart() {
        super.onStart()

        listSubject.onNext(galleryRepository.localPhoto)
        if (lastPage == 0)
            fetchPhotos()
    }

    private fun fetchPhotos() {
        loadingSubject.onNext(true)
        disposables.add(galleryRepository.loadPhotos(lastPage.plus(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchConsumer, errorConsumer))
    }

    override fun submitSearch(query: String?) {
        if(query != null && query.isNotEmpty()){
            loadingSubject.onNext(true)
            searchPhotos(query)
        }else{
            fetchPhotos()
        }
    }

    private fun searchPhotos(query: String) {
        val repository = searchRepositories[query]
        if(repository!= null){
            
        }
    }

    override fun onRetryClick() {

    }


    override fun onScrollStateChanged(itemCount: Int, lastVisiblePosition: Int, newState: Int) {
        if (itemCount - lastVisiblePosition < 5 && loadingSubject.value.not())
            fetchPhotos()
    }

    private val errorConsumer: Consumer<Throwable> = Consumer<Throwable> {
        messagesSubject.onNext(R.string.common_error)
        loadingSubject.onNext(false)
    }

    private val fetchConsumer = Consumer<List<PhotoRest>> { list ->
        lastPage += 1
        galleryRepository.localPhoto.addAll(list)
        listSubject.onNext(galleryRepository.localPhoto)
        loadingSubject.onNext(false)
    }

}