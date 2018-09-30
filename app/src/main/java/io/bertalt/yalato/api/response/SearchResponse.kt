package io.bertalt.yalato.api.response

import io.bertalt.yalato.api.response.PhotoRest

/**
 * Created by beretta on 13.10.2017.
 */
class SearchResponse(val total: Int, val total_pages: Int, val results: List<PhotoRest>) {

}