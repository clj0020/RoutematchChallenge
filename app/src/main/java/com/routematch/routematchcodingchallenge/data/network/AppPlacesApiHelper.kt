package com.routematch.routematchcodingchallenge.data.network

import android.location.Location
import com.routematch.routematchcodingchallenge.BuildConfig
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.data.network.responses.PlacesResponse
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/** This is the instantiation of the PlacesApiHelper that the DataManager can call. **/
@Singleton
class AppPlacesApiHelper @Inject constructor() : PlacesApiHelper {

    /** Calls the Google Places API function for finding nearby places. **/
    override fun getNearbyPlaces(location: Location, radius: Int, type: String): Single<List<Place>> {
        return Rx2AndroidNetworking.get(PlacesApiEndPoint.ENDPOINT_PLACES_AROUND_LOCATION)
                .addQueryParameter("key", BuildConfig.GOOGLE_MAPS_API_KEY)
                .addQueryParameter("location", location.latitude.toString() + ", " + location.longitude.toString())
                .addQueryParameter("radius", radius.toString())
                .addQueryParameter("type", type)
                .build()
                .getObjectSingle<PlacesResponse.GetNearbyPlaces>(PlacesResponse.GetNearbyPlaces::class.java)
                .map{placeApiNearbyPlacesResult: PlacesResponse.GetNearbyPlaces? ->
                    mapPlacesListResponseToPlaceModel(placeApiNearbyPlacesResult?.results ?: emptyList())
                }
    }

    /** Calls the Google Places API function for getting a place's details. **/
    override fun getPlaceDetails(place_id: String): Single<Place?> {
        return Rx2AndroidNetworking.get(PlacesApiEndPoint.ENDPOINT_PLACE_DETAILS)
                .addQueryParameter("key", BuildConfig.GOOGLE_MAPS_API_KEY)
                .addQueryParameter("placeid", place_id)
                .build()
                .getObjectSingle<PlacesResponse.GetPlaceDetails>(PlacesResponse.GetPlaceDetails::class.java)
                .map{placeApiPlaceDetailsResult: PlacesResponse.GetPlaceDetails? ->
                    mapPlaceDetailsResponseToPlaceModel(placeApiPlaceDetailsResult?.placeResult)
                }
    }

    /** Maps the response from the Google Places API to custom Place Class. **/
    fun mapPlacesListResponseToPlaceModel(placesList: List<PlacesResponse.GetNearbyPlaces.PlaceResult>): List<Place> {
        val _listPlaces = mutableListOf<Place>()
        for (item in placesList) {

            val place = Place(
                    id = item.id,
                    place_id = item.place_id,
                    name = item.name,
                    rating = item.rating,
                    price_level = item.price_level,
                    lat = item.geometry?.location?.lat,
                    lng = item.geometry?.location?.lng,
                    image_url = item.photos?.get(0)?.photo_reference
            )

            _listPlaces.add(place)
        }

        return _listPlaces.toList()
    }

    /** Maps the response from the Google Places API to custom Place Class. **/
    fun mapPlaceDetailsResponseToPlaceModel(place: PlacesResponse.GetPlaceDetails.PlaceResult?): Place? {
        if (place == null) {
            return null
        }

        return Place(
                id = place.id,
                place_id = place.place_id,
                name = place.name,
                rating = place.rating,
                price_level = place.price_level,
                lat = place.geometry?.location?.lat,
                lng = place.geometry?.location?.lng,
                image_url = place.photos?.get(0)?.photo_reference
        )
    }

}
