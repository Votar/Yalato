package io.bertalt.yalato.api.response

/**
 * Created by beretta on 12.10.2017.
 */

data class PhotoRest(
        var current_user_collections: Array<String>,

        var urls: UrlsRest,

        var width: Int,

        var links: LiknsRest,

        var id: String,

        var updated_at: String,

        var height: Int,

        var color: String,

        var likes: String,

        var created_at: String,

        var categories: Array<String>,

        var user: UserRest,

        var liked_by_user: String)
