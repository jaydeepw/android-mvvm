# Android MVVM
A simple Android app demonstrating the use of MVVM architecture pattern and automated tests for it

# Problem Statement
[Filtering matches](https://github.com/sparknetworks/coding_exercises_options/blob/master/filtering_matches/README.md)

# Getting Started
- Clone the repository
- Import the directory `MatchFilter` from the repo using Android Studio stable version
- Update the `SERVER_URL` to your local IP address in `app/build.gradle` in the build variant that you are using `release` or `debug`
- Update `network_security_config.xml` in the app with your IP address to allow connecting to [this](https://github.com/jaydeepw/backend-snetworks) local server
- Run the app on emulator or device
- When running the app on device, ensure that both your server and mobile device, both are
connected to the same Wifi network

# Running UI tests
- Clone the repository
- Open terminal and type `cd android-mvvm-snetworks/MatchFilter`
- Run `./gradlew connectedAndroidTest`
