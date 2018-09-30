package io.bertalt.yalato.api.response

/**
 * Created by beretta on 12.10.2017.
 */

class ProfileImageRest {
    var small: String? = null

    var large: String? = null

    var medium: String? = null

    override fun toString(): String {
        return "ClassPojo [small = $small, large = $large, medium = $medium]"
    }
}
