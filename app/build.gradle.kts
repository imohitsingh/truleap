plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.eventadminapp1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eventadminapp1"
        minSdk = 25
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.navigation:navigation-fragment:2.7.4")
    implementation ("androidx.navigation:navigation-ui:2.7.4")
    // Firebase dependencies

    // Firebase Authentication
    implementation ("com.google.firebase:firebase-auth:22.1.1")

    // Firebase Firestore
    implementation ("com.google.firebase:firebase-firestore:24.8.1")

    // Firebase Storage
    implementation ("com.google.firebase:firebase-storage:20.3.0")

    // For loading images.../EventAdminApp1>

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

