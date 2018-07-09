package com.routematch.routematchcodingchallenge.data.network

import android.location.Location
import android.util.Log
import com.routematch.routematchcodingchallenge.BuildConfig
import com.routematch.routematchcodingchallenge.R
import com.routematch.routematchcodingchallenge.data.models.Place
import com.routematch.routematchcodingchallenge.data.network.responses.PlacesResponse
import com.routematch.routematchcodingchallenge.ui.main.MainActivity.Companion.TAG
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

    /** Maps the response from the Google Places API to custom Place Class. **/
    fun mapPlacesListResponseToPlaceModel(placesList: List<PlacesResponse.GetNearbyPlaces.PlaceResult>): List<Place> {
        val _listPlaces = mutableListOf<Place>()
        for (item in placesList) {

            val place = Place(
                    id = item.id,
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

}
