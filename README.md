# Routematch Challenge

Thank you for the opportunity to show you some of my skills! 

For this challenge I used the following libraries:
* Android Architecture Components - For Live Data.
* Android Data Binding - For binding views to models.
* Dagger 2 - For dependency injection.
* RxJava/RxAndroid - For threading and observing data returned from Api calls.
* Google Play Services - For access to Maps, FusedLocationProviderClient, Location Services SettingsClient.
* Rx2FastAndroidNetworking - For calling the Place Api.
* GSON - For JSON parsing.
* Glide - For Image Loading.

The bulk of the location use cases was handled in the LocationHelperLiveData class. It is responsible for connecting to the GoogleApiClient,
checking and handling location settings and permissions, and posting location updates to observing classes. I chose a Live Data object for this 
because it is effectively lifecycle aware, connecting when an activity or fragment begins observing it and disconnecting when they stop observing 
which only happens when an activity or fragment is [in the STARTED or RESUMED state and in the DESTROYED state respectively](https://developer.android.com/topic/libraries/architecture/livedata). 

I decided to call the Places API directly instead of using the Android SDK so that I could have more control over the threading and other things. 

I hope that I correctly fulfilled your requirements and I think I correctly used the feature branching process. Thanks for your time!

## Setup

In order to run this project, you need an API key from a Google Cloud Project that has the Maps Android SDK API and the Places API (not Android SDK) enabled.

Then place your API key into the gradle.properties file like this:

```gradle

GOOGLE_MAPS_API_KEY="YOUR_API_KEY"

```
