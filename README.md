# BTree

BTree is bookmark management application for iOS and Android, Windows, macOS.  
BTree is created by Compose Multiplatform.

![](https://raw.githubusercontent.com/kaleidot725/BTree/main/demo.drawio.svg)

## Features

- [x] Manage bookmark directories and links on the tree structure.
- [x] Create and delete the bookmark directory and link.
- [x] Edit bookmark directory and link. 
- [X] Open bookmark link website.
- [ ] Rearrange directory and link structures

https://github.com/kaleidot725/BTree/assets/23740796/3b286069-679d-48ac-b80f-22e15247c196

## Architecture

| Class | Detailas |
| ----- | -------- |
| Screen | TBD |
| Component | TBD |
| ScreenModel | TBD |
| State | TBD |
| Repository | TBD |

![](https://raw.githubusercontent.com/kaleidot725/BTree/main/architecture.drawio.svg)

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

## License

```
Copyright (c) 2023 Yusuke Katsuragawa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
