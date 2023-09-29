# Weather
Show current weather and 5 day forecast based on user location with open weather api

# Tech Stack<br/>
-[Kotlin](https://developer.android.com/kotlin?gclid=CjwKCAjw9r-DBhBxEiwA9qYUpWK_ANJvWx6zBkFk-4XeP5a0dCxwyFZv_EeeqAcUx1K_Mj3gGkpdxRoCW9IQAvD_BwE&gclsrc=aw.ds)- a cross-platform, statically typed, general-purpose programming language with type inference.<br/>
-Coroutines - perform background operations.<br/>
-[KOIN](https://insert-koin.io/) - a pragmatic lightweight dependency injection framework.<br/>
-[ROOM](https://developer.android.com/training/data-storage/room) - persistence library providing an abstraction over SQL .<br/>
[Jetpack](https://developer.android.com/jetpack) -<br/>
-[Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.<br/><br/>


# Pre-requisite 📝

In your `local.properties` you will need to add your Open Weather API key and copy the urls in.

```properties
OPEN_WEATHER_API_KEY = YOUR KEY
OPEN_WEATHER_BASE_URL=https://api.openweathermap.org
OPEN_WEATHER_ICONS_URL= http://openweathermap.org/img/wn/
```

Check for one under  [`Api Keys`](https://home.openweathermap.org/api_keys)


*Environment*
- Built on A.S Hedgehog
- JDK 17
-minSDK 26

# Architecture<br/>
The app follows MVVM Architecture and contains these packages: 

#### Data

- ```data-remote```

Handles data interacting with the network and is later serverd up to the presentation layer through 
domain object

- ```data-local```

Handles persistence of object with Room ORM from.This module is responsible for handling all local related
logic and serves up data to and from the presentation layer through domain objects.

With this separation we can easily swap in new or replace the database being used without causeing
major ripples across the codebase.

#### Repository
Gets data from api and room and distributes it to the rest of the app
#### DI
Koin handles dependency injection on components making it easier to reuse
#### util
This package contains utility functions like networkresult which are used throughtout the application 
#### ui
contains views that are shown to the user

# Installation
Clone the repo and run on emulator or phone  or download the apk file 

# ScreenShot
![location List](https://github.com/valentineRutto/Weather/blob/main/favlocation.png)
![Weather](https://github.com/valentineRutto/Weather/blob/main/weather.png)

