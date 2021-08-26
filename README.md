# Movie Rating Application
An application which provides the overview of movies across the globe with its rating.

## Description
This application allows the user to search a movie by entering the movie title or a keyword pertaining to any language. Upon searching, the user will get the list of movies similar to the keyword entered.The user can click on the desired movie name to view the rating along with an overview of the movie.

## Getting Started

This project is designed using the MVVM Architecture pattern and kodein dependency injection so that the application is losely coupled. This enables easy computation of the unit test cases. There are two Gradle tasks for testing the project:
* `AndroidTest` - for running Espresso on a connected device
* `test` - for running unit tests


### TMDB API key

Movie Rating uses the [TMDB API](https://www.themoviedb.org/settings/api) to load movie list and its images. To use the API, you will need to create an account in [TMDB Portal](https://www.themoviedb.org/) and generate API key.

Libraries Used
--------------
* Foundation - Components for core system capabilities, Kotlin extensions and support for automated testing.
  * AppCompat - Degrade gracefully on older versions of Android.
  * Android KTX- Write more concise, idiomatic Kotlin code.
  * Test - An Android testing framework for unit and runtime UI tests.
* Architecture - A collection of libraries for robust, testable, and
  maintainable apps. 
  * View Binding- Helps to interact with view more easily.
  * Lifecycles- Create a UI that automatically responds to lifecycle events.
  * LiveData - Build data objects that notify views when the underlying database changes.
  * ViewModel - Seprate business logic from UI.
* Third party and miscellaneous libraries
  * Picasso  -for image loading
  * Kodein - for dependency injection
  * Kotlin Coroutines - for managing background threads with simplified code and reducing needs for callbacks
  * Retrofit - used for API calls
  * Gson - used to convert json into pojo class
  * KTlint - build in application formatter

## Version History

* 1.0
    * Initial Release
