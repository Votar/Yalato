package io.bertalt.yalato.api.response

/**
 * Created by beretta on 12.10.2017.
 */


data class UserRest(
    var location: String? = null,

    var profile_image: ProfileImageRest? = null,

    var portfolio_url: String? = null,

    var links: LiknsRest? = null,

    var total_photos: String? = null,

    var total_likes: String? = null,

    var id: String? = null,

    var first_name: String? = null,

    var username: String? = null,

    var updated_at: String? = null,

    var bio: String? = null,

    var name: String? = null,

    var total_collections: String? = null,

    var last_name: String? = null,

    var twitter_username: String? = null)
