# Hillfort Explorer

### Description
Kotlin mobile application made in Android Studio; Hillfort Explorer. This is an app that allows archaeology students in Ireland to manage and explore Hillforts to their desires.

### Technologies Used
* **Primary Language**: Kotlin
* **IDE**: Android Studio

### Importing
Read [this](https://maxrohde.com/2014/08/18/import-github-project-to-android-studio/) article for importing .git projects into Android Studio.

#### Google Maps API Key
This mobile app uses the Google Maps API, therefore a Google Maps API key is required to be configured in the files named `app/src/release/res/values/google_maps_api.xml` and `app/src/debug/res/values/google_maps_api.xml`. Read [this](https://developers.google.com/maps/documentation/android-sdk/start#get-key) article for retrieving one.

```xml
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">AIzaSyAQh2SaKrIJAnwL5Y3cCeK39pdcOIFtrco</string>
</resources>
```