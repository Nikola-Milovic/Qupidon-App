@file:Suppress("detekt.StringLiteralDuplication")

private object LibraryVersion {
    const val TIMBER = "4.7.1"
    const val PLAY_CORE = "1.7.3" //164
    const val RECYCLER_VIEW = "1.2.0-alpha05"
    const val MATERIAL = "1.1.0-alpha09"
    const val CONSTRAINT_LAYOUT = "1.1.3"
    const val CORE_KTX = "1.1.0"
    const val FRAGMENT_KTX = "1.2.5" //1.1.0
    const val LIFECYCLE = "2.2.0"
    const val LIFECYCLE_VIEW_MODEL_KTX = "2.2.0"
    const val ANDROID_LEGACY_SUPPORT = "1.0.0"

    //Koin
    const val KOIN = "2.2.0"
    const val RETROFIT = "2.9.0"
    const val SWIPE_REFRESH_LAYOUT = "1.1.0"
    const val LOTTIE_ANIMATION = "3.5.0"
    const val FACEBOOK_SDK = "8.1.0"
    const val GOOGLE_LOCATION = "17.1.0"
    const val DEXTER = "6.2.2"
    const val IMAGE_PICKER = "2.4.1"
    const val GLIDE = "4.9.0"
    const val GSON = "2.8.6"

    const val ROOM = "2.2.6"
}


object LibraryDependency {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${CoreVersion.KOTLIN}"

    const val TIMBER = "com.jakewharton.timber:timber:${LibraryVersion.TIMBER}"
    const val PLAY_CORE = "com.google.android.play:core:${LibraryVersion.PLAY_CORE}"
    const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersion.COROUTINES_ANDROID}"
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${CoreVersion.COROUTINES_ANDROID}"

    //UI
    const val SUPPORT_CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${LibraryVersion.CONSTRAINT_LAYOUT}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${LibraryVersion.RECYCLER_VIEW}"
    const val MATERIAL = "com.google.android.material:material:${LibraryVersion.MATERIAL}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${LibraryVersion.FRAGMENT_KTX}"

    //LifeCycle
    const val LIFECYCLE_EXTENSIONS =
            "androidx.lifecycle:lifecycle-extensions:${LibraryVersion.LIFECYCLE}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.LIFECYCLE_VIEW_MODEL_KTX}"
    const val ANDROID_LEGACY_SUPPORT =
            "androidx.legacy:legacy-support-v4:${LibraryVersion.ANDROID_LEGACY_SUPPORT}"

    const val NAVIGATION_FRAGMENT_KTX =
            "androidx.navigation:navigation-fragment-ktx:${CoreVersion.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${CoreVersion.NAVIGATION}"

    //Koin
    const val KOIN_ANDROID = "org.koin:koin-android:${LibraryVersion.KOIN}"
    const val KOIN_ANDROID_SCOPE = "org.koin:koin-android-scope:${LibraryVersion.KOIN}"
    const val KOIN_ANDROID_VIEWMODEL = "org.koin:koin-android-viewmodel:${LibraryVersion.KOIN}"
    const val KOIN_ANDROID_EXTENSION = "org.koin:koin-android-ext:${LibraryVersion.KOIN}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
    const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${LibraryVersion.RETROFIT}"

    const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:${LibraryVersion.SWIPE_REFRESH_LAYOUT}"

    const val LOTTIE_ANIMATION = "com.airbnb.android:lottie:${LibraryVersion.LOTTIE_ANIMATION}"

    const val FACEBOOK_SDK = "com.facebook.android:facebook-login:${LibraryVersion.FACEBOOK_SDK}"

    const val GOOGLE_LOCATION = "com.google.android.gms:play-services-location:${LibraryVersion.GOOGLE_LOCATION}"

    const val DEXTER = "com.karumi:dexter:${LibraryVersion.DEXTER}"

    const val IMAGE_PICKER = "com.github.esafirm.android-image-picker:imagepicker:${LibraryVersion.IMAGE_PICKER}"
    const val GLIDE = "com.github.bumptech.glide:glide:${LibraryVersion.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${LibraryVersion.GLIDE}"

    const val GSON = "com.google.code.gson:gson:${LibraryVersion.GSON}"

    const val ROOM = "androidx.room:room-runtime:${LibraryVersion.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${LibraryVersion.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${LibraryVersion.ROOM}"
}
