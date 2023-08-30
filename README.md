# BTree 🚧 WORK IN PROGRESS 🚧

BTree is bookmark management application for iOS and Android, Windows, macOS.  
BTree is created by Compose Multiplatform.

![Group 2](https://github.com/kaleidot725/BTree/assets/23740796/4bddcaf8-8083-432c-8f7a-e7781493ee26)

## Features

- [x] Manage bookmark directories and links on the tree structure.
- [x] Create and delete the bookmark directory and link.
- [x] Edit bookmark directory and link. 
- [X] Open bookmark link website.
- [ ] Rearrange directory and link structures

## Library

| Name | Details |
| ----- | ------ |
| [Compose Multiplatform](https://www.jetbrains.com/ja-jp/lp/compose-multiplatform/) | UI Framework |
| [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html) | Serialize and desrialize data |
| [Kotlin Coroutines](https://kotlinlang.org/docs/serialization.html) | Launch ansyncronous execution |
| [Koin](https://insert-koin.io/) | Dependency injection container |
| [Voyager](https://voyager.adriel.cafe/) | Screen and navigation management |
| [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) | Save and load application data |
| [InsetsX](https://github.com/mori-atsushi/insetsx) | Control status icon color | 

## Architecture

| Class | Detailas |
| ----- | -------- |
| Screen | tttt |
| Components | tttt |
| ScreenModel | tttt |
| Repository | ttttt |

![Group 3 - 01](https://github.com/kaleidot725/BTree/assets/23740796/93499c09-2d8d-43f5-be6f-2618aa061a68)

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
