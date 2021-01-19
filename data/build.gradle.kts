plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas".toString()
            }
        }

    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    api(LibraryDependency.TIMBER)
    api(LibraryDependency.NAVIGATION_FRAGMENT_KTX)
    api(LibraryDependency.NAVIGATION_UI_KTX)
    api(LibraryDependency.KOIN_ANDROID)
    api(LibraryDependency.KOIN_ANDROID_EXTENSION)
    api(LibraryDependency.KOIN_ANDROID_SCOPE)
    api(LibraryDependency.RETROFIT)
    api(LibraryDependency.GSON_CONVERTER)
    api(LibraryDependency.GSON)

    implementation(LibraryDependency.ROOM)
    implementation(LibraryDependency.ROOM_KTX)
    kapt(LibraryDependency.ROOM_COMPILER)

    implementation(project(":common"))

    implementation ("io.socket:socket.io-client:2.0.0") {
        exclude(group = "org.json", module = "json")
    }

    addTestDependencies()
}