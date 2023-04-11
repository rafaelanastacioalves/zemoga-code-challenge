# Zemoga Code Challenge

![listing screen](captures/Screenshot_1523145822.png "listing screen") ![details screen](captures/Screenshot_1523145868.png "details screen")

## General Description

**Architecture**: I used the MVVM (Model-View-ViewModel) architecture. I used interactors as an example to include some business logic rules.

**Repository**: In the Model layer, I abstracted a repository that provides the necessary data to the ViewModel without the other parts knowing how this data was obtained. The `NetworkBoundResource` class is responsible for the algorithm to obtain the data (allows obtaining data from remote and/or local storage).

**Tests**: I included several unit tests and some instrumented tests to demonstrate knowledge in the use of main libraries (JUnit, Mockk, Roboelectric). I did not implement UI tests.

**Fetching data mechanisms**: In some cases, I decided to fetch data from the repository and apply some rules in interactors (like ordering the favorites first and eliminating those which have the "deleted" status).
In other cases, however, when there is some user interaction,
like in the Favorites Only feature, I tried to add some async mechanism by keeping the user option in live data and taking that value into consideration after refreshing the post list. In this case, I didn't make use of interactors. Using `switchMap` collection processing in the ViewModel was just enough.

![Architecture Diagram](captures/android_arch.png)

## Technical Description

I used the following libraries and solutions:

- **Retrofit**: Used to fetch data remotely. [Reference: https://square.github.io/retrofit/]

- **Mockk**: Library used to create mocks of classes to facilitate class isolation in unit tests. [Reference: https://mockk.io/]

- **Coroutines**: Used for asynchronous tasks during app execution. [Reference: https://developer.android.com/kotlin/coroutines?hl=en]

- **Android Architecture Components**: Used for implementing the MVVM architecture. Used LiveData, ViewModel, among others. [Reference: https://developer.android.com/topic/libraries/architecture]

- **Room**: Used for data model storage. [Reference: https://developer.android.com/training/data-storage/room]

## What was not implemented

**Mechanism to load all posts from API**: This mechanism occurs, but we do not have control over when to force that. The implemented mechanism fetches from the API if there is no local data, for the sake of simplicity.

**UI Tests**: UI tests are harder to keep in projects as they take too long to be executed and are prone to flaky errors. So for time-saving purposes, I didn't add any UI tests.

**Dependency Injection**: I did not use it in the project, but I have knowledge in Koin and Dagger 2.

## How to run

The app can be built like any other app. No additional instructions about that.
The same applies to tests.
