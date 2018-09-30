package io.bertalt.yalato.ui.list

import io.bertalt.yalato.api.GetPhotosService
import io.bertalt.yalato.api.response.PhotoRest
import io.bertalt.yalato.utils.Constant

class PhotosRepository(val apiService: GetPhotosService, query : String = "") {

    val localPhoto : MutableList<PhotoRest> = mutableListOf()

    fun loadPhotos(page: Int) = apiService.listPhotosObservable(Constant.userId, page)



}