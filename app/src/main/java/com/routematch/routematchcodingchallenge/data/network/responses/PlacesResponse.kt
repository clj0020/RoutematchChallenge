package com.routematch.routematchcodingchallenge.data.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/** This class is for parsing the responses from the Google Places API using GSON. **/
class PlacesResponse {

    /** For parsing the response from calling get nearby places to the Google Places API using GSON. **/
    class GetNearbyPlaces {

        @Expose
        @SerializedName("status")
        var status: String? = null

        @Expose
        @SerializedName("results")
        var results: List<PlaceResult>? = null

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other !is GetNearbyPlaces) {
                return false
            }

            val that = other

            if (status != that.status) {
                return false
            }
            return results == that.results
        }

        override fun hashCode(): Int {
            var result = status!!.hashCode()
            result = 31 * result + results!!.hashCode()
            return result
        }

        class PlaceResult {

            @Expose
            @SerializedName("id")
            var id: String? = null

            @Expose
            @SerializedName("place_id")
            var place_id: String? = null

            @Expose
            @SerializedName("geometry")
            var geometry: Geometry? = null

            @Expose
            @SerializedName("icon")
            var icon: String? = null

            @Expose
            @SerializedName("name")
            var name: String? = null

            @Expose
            @SerializedName("opening_hours")
            var opening_hours: OpeningHours? = null

            @Expose
            @SerializedName("price_level")
            var price_level: Int? = null

            @Expose
            @SerializedName("rating")
            var rating: Number? = null

            @Expose
            @SerializedName("photos")
            var photos: List<Photo>? = null

            @Expose
            @SerializedName("scope")
            var scope: String? = null

            @Expose
            @SerializedName("reference")
            var reference: String? = null

            @Expose
            @SerializedName("types")
            var types: List<String>? = null

            @Expose
            @SerializedName("vicinity")
            var vicinity: String? = null

            override fun equals(other: Any?): Boolean {
                if (this === other) {
                    return true
                }
                if (other !is PlaceResult) {
                    return false
                }

                val that = other

                if (id != that.id) {
                    return false
                }
                if (place_id != that.place_id) {
                    return false
                }
                if (geometry != that.geometry) {
                    return false
                }
                if (icon != that.icon) {
                    return false
                }
                if (name != that.name) {
                    return false
                }
                if (opening_hours != that.opening_hours) {
                    return false
                }
                if (price_level != that.price_level) {
                    return false
                }
                if (rating != that.rating) {
                    return false
                }
                if (photos != that.photos) {
                    return false
                }
                if (scope != that.scope) {
                    return false
                }
                if (reference != that.reference) {
                    return false
                }
                if (types != that.types) {
                    return false
                }
                return vicinity == that.vicinity
            }

            override fun hashCode(): Int {
                var result = id!!.hashCode()
                result = 31 * result + place_id!!.hashCode()
                result = 31 * result + geometry!!.hashCode()
                result = 31 * result + icon!!.hashCode()
                result = 31 * result + name!!.hashCode()
                result = 31 * result + opening_hours!!.hashCode()
                result = 31 * result + photos!!.hashCode()
                result = 31 * result + scope!!.hashCode()
                result = 31 * result + reference!!.hashCode()
                result = 31 * result + price_level!!.hashCode()
                result = 31 * result + rating!!.hashCode()
                result = 31 * result + types!!.hashCode()
                result = 31 * result + vicinity!!.hashCode()
                return result
            }

            class Geometry {

                @Expose
                @SerializedName("location")
                var location: Location? = null

                override fun equals(other: Any?): Boolean {
                    if (this === other) {
                        return true
                    }
                    if (other !is Geometry) {
                        return false
                    }

                    val that = other

                    return location == that.location
                }

                override fun hashCode(): Int {
                    val result = location!!.hashCode()
                    return result
                }

                class Location {

                    @Expose
                    @SerializedName("lat")
                    var lat: Number? = null

                    @Expose
                    @SerializedName("lng")
                    var lng: Number? = null

                    override fun equals(other: Any?): Boolean {
                        if (this === other) {
                            return true
                        }
                        if (other !is Location) {
                            return false
                        }

                        val that = other

                        if (lat != that.lat) {
                            return false
                        }
                        return lng == that.lng
                    }

                    override fun hashCode(): Int {
                        var result = lat!!.hashCode()
                        result = 31 * result + lng!!.hashCode()
                        return result
                    }


                }
            }

            class OpeningHours {

                @Expose
                @SerializedName("open_now")
                var open_now: Boolean? = null


                override fun equals(other: Any?): Boolean {
                    if (this === other) {
                        return true
                    }
                    if (other !is OpeningHours) {
                        return false
                    }

                    val that = other

                    return open_now == that.open_now
                }

                override fun hashCode(): Int {
                    val result = open_now!!.hashCode()
                    return result
                }
            }

            class Photo {

                @Expose
                @SerializedName("height")
                var height: Number? = null

                @Expose
                @SerializedName("width")
                var width: Number? = null

                @Expose
                @SerializedName("html_attributions")
                var html_attributions: List<String>? = null

                @Expose
                @SerializedName("photo_reference")
                var photo_reference: String? = null

                override fun equals(other: Any?): Boolean {
                    if (this === other) {
                        return true
                    }
                    if (other !is Photo) {
                        return false
                    }

                    val that = other

                    if (height != that.height) {
                        return false
                    }
                    if (width != that.width) {
                        return false
                    }
                    if (html_attributions != that.html_attributions) {
                        return false
                    }
                    return photo_reference == that.photo_reference
                }

                override fun hashCode(): Int {
                    var result = height!!.hashCode()
                    result = 31 * result + width!!.hashCode()
                    result = 31 * result + photo_reference!!.hashCode()
                    result = 31 * result + html_attributions!!.hashCode()
                    return result
                }
            }
        }

    }

    /** For parsing the response from calling get nearby places to the Google Places API using GSON. **/
    class GetPlaceDetails {

        @Expose
        @SerializedName("status")
        var status: String? = null

        @Expose
        @SerializedName("result")
        var placeResult: PlaceResult? = null

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other !is GetPlaceDetails) {
                return false
            }

            val that = other

            if (status != that.status) {
                return false
            }
            return placeResult == that.placeResult
        }

        override fun hashCode(): Int {
            var result = status!!.hashCode()
            result = 31 * result + placeResult!!.hashCode()
            return result
        }

        class PlaceResult {

            @Expose
            @SerializedName("id")
            var id: String? = null

            @Expose
            @SerializedName("place_id")
            var place_id: String? = null

            @Expose
            @SerializedName("geometry")
            var geometry: Geometry? = null

            @Expose
            @SerializedName("icon")
            var icon: String? = null

            @Expose
            @SerializedName("name")
            var name: String? = null

            @Expose
            @SerializedName("opening_hours")
            var opening_hours: OpeningHours? = null

            @Expose
            @SerializedName("price_level")
            var price_level: Int? = null

            @Expose
            @SerializedName("rating")
            var rating: Number? = null

            @Expose
            @SerializedName("formatted_address")
            var formatted_address: String? = null

            @Expose
            @SerializedName("formatted_phone_number")
            var formatted_phone_number: String? = null

            @Expose
            @SerializedName("website")
            var website: String? = null

            @Expose
            @SerializedName("photos")
            var photos: List<Photo>? = null

            @Expose
            @SerializedName("scope")
            var scope: String? = null

            @Expose
            @SerializedName("reference")
            var reference: String? = null

            @Expose
            @SerializedName("types")
            var types: List<String>? = null

            @Expose
            @SerializedName("vicinity")
            var vicinity: String? = null

            override fun equals(other: Any?): Boolean {
                if (this === other) {
                    return true
                }
                if (other !is PlaceResult) {
                    return false
                }

                val that = other

                if (id != that.id) {
                    return false
                }
                if (place_id != that.place_id) {
                    return false
                }
                if (geometry != that.geometry) {
                    return false
                }
                if (icon != that.icon) {
                    return false
                }
                if (name != that.name) {
                    return false
                }
                if (opening_hours != that.opening_hours) {
                    return false
                }
                if (price_level != that.price_level) {
                    return false
                }
                if (rating != that.rating) {
                    return false
                }
                if (formatted_address != that.formatted_address) {
                    return false
                }
                if (formatted_phone_number != that.formatted_phone_number) {
                    return false
                }
                if (website != that.website) {
                    return false
                }
                if (photos != that.photos) {
                    return false
                }
                if (scope != that.scope) {
                    return false
                }
                if (reference != that.reference) {
                    return false
                }
                if (types != that.types) {
                    return false
                }
                return vicinity == that.vicinity
            }

            override fun hashCode(): Int {
                var result = id!!.hashCode()
                result = 31 * result + place_id!!.hashCode()
                result = 31 * result + geometry!!.hashCode()
                result = 31 * result + icon!!.hashCode()
                result = 31 * result + name!!.hashCode()
                result = 31 * result + opening_hours!!.hashCode()
                result = 31 * result + photos!!.hashCode()
                result = 31 * result + scope!!.hashCode()
                result = 31 * result + reference!!.hashCode()
                result = 31 * result + price_level!!.hashCode()
                result = 31 * result + rating!!.hashCode()
                result = 31 * result + formatted_address!!.hashCode()
                result = 31 * result + formatted_phone_number!!.hashCode()
                result = 31 * result + website!!.hashCode()
                result = 31 * result + types!!.hashCode()
                result = 31 * result + vicinity!!.hashCode()
                return result
            }

            class Geometry {

                @Expose
                @SerializedName("location")
                var location: Location? = null

                override fun equals(other: Any?): Boolean {
                    if (this === other) {
                        return true
                    }
                    if (other !is Geometry) {
                        return false
                    }

                    val that = other

                    return location == that.location
                }

                override fun hashCode(): Int {
                    val result = location!!.hashCode()
                    return result
                }

                class Location {

                    @Expose
                    @SerializedName("lat")
                    var lat: Number? = null

                    @Expose
                    @SerializedName("lng")
                    var lng: Number? = null

                    override fun equals(other: Any?): Boolean {
                        if (this === other) {
                            return true
                        }
                        if (other !is Location) {
                            return false
                        }

                        val that = other

                        if (lat != that.lat) {
                            return false
                        }
                        return lng == that.lng
                    }

                    override fun hashCode(): Int {
                        var result = lat!!.hashCode()
                        result = 31 * result + lng!!.hashCode()
                        return result
                    }


                }
            }

            class OpeningHours {

                @Expose
                @SerializedName("open_now")
                var open_now: Boolean? = null

                @Expose
                @SerializedName("weekday_text")
                var weekday_text: List<String>? = null

                override fun equals(other: Any?): Boolean {
                    if (this === other) {
                        return true
                    }
                    if (other !is OpeningHours) {
                        return false
                    }

                    val that = other

                    if (weekday_text != that.weekday_text) {
                        return false
                    }
                    return open_now == that.open_now
                }

                override fun hashCode(): Int {
                    var result = open_now!!.hashCode()
                    result = 31 * result + weekday_text!!.hashCode()
                    return result
                }
            }

            class Photo {

                @Expose
                @SerializedName("height")
                var height: Number? = null

                @Expose
                @SerializedName("width")
                var width: Number? = null

                @Expose
                @SerializedName("html_attributions")
                var html_attributions: List<String>? = null

                @Expose
                @SerializedName("photo_reference")
                var photo_reference: String? = null

                override fun equals(other: Any?): Boolean {
                    if (this === other) {
                        return true
                    }
                    if (other !is Photo) {
                        return false
                    }

                    val that = other

                    if (height != that.height) {
                        return false
                    }
                    if (width != that.width) {
                        return false
                    }
                    if (html_attributions != that.html_attributions) {
                        return false
                    }
                    return photo_reference == that.photo_reference
                }

                override fun hashCode(): Int {
                    var result = height!!.hashCode()
                    result = 31 * result + width!!.hashCode()
                    result = 31 * result + photo_reference!!.hashCode()
                    result = 31 * result + html_attributions!!.hashCode()
                    return result
                }
            }
        }

    }
}