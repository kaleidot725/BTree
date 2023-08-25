# BTree 🚧 WORK IN PROGRESS 🚧

BTree is bookmark management application for iOS and Android, Windows, macOS.  
BTree is created by Compose Multiplatform.

![Group 2 (1)](https://github.com/kaleidot725/BTree/assets/23740796/65931c52-4819-41e1-8e9a-9721d26c848d)

## Features

- [x] Manage bookmark directories and links on the tree structure.
- [x] Create and delete the bookmark directory and link.
- [x] Edit bookmark directory and link. 
- [X] Open bookmark link website.
- [ ] Rearrange directories and links

## Architecture

![Group 3](https://github.com/kaleidot725/BTree/assets/23740796/27877370-a8eb-4f4b-80cc-419953ff827e)

## Library

| Name | Details |
| ----- | ------ |
| Compose Multiplatform | UI Framework |
| Multiplatform Settings | Persist setting data |
| Voyager | Navigation management |
| Koin | DI Container |
| Kotlin Serialization | Serialize setting data |
| Napier | Logger |
| InsetsX | Screen insets utility | 

## Build

### Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 8 on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there
 - run `./gradlew podInstall` in the project root

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`

### iOS
To run the application on iPhone device/simulator:
 - Open `iosApp/iosApp.xcworkspace` in Xcode and run standard configuration
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio
