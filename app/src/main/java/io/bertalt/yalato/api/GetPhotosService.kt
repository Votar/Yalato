package io.bertalt.yalato.api

import io.bertalt.yalato.api.response.SearchResponse
import io.bertalt.yalato.api.response.DownloadLinkResult
import io.bertalt.yalato.api.response.PhotoRest
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by beretta on 12.10.2017.
 */

interface GetPhotosService {
    @GET("photos")
    fun listPhotos(@Query("client_id") clientId: String, @Query("page") page: Int): Call<List<PhotoRest>>

    @GET("photos")
    fun listPhotosObservable(@Query("client_id") clientId: String, @Query("page") page: Int): Observable<List<PhotoRest>>

    @GET("search/photos")
    fun searchPhotos(@Query("client_id") clientId: String, @Query("query") query: String, @Query("page") page: Int): Call<SearchResponse>

    @GET("photos/{id}/download")
    fun getPhotosLink(@Path("id") photoId: String, @Query("client_id") clientId: String): Call<DownloadLinkResult>
}
