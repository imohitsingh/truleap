plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Add the plugin here, no need for 'apply plugin' later
}

android {
    namespace = "com.tru.leap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tru.leap"
        minSdk = 25
        targetSdk = 34
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
    implementation ("androidx.appcompat:appcompat:1.6.1")// This ensures you can use AppCompatActivity
    implementation ("androidx.recyclerview:recyclerview:1.3.1") // Ensure this is included


    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // Firebase BoM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))

    // Firebase Auth (version managed by BoM)
    implementation("com.google.firebase:firebase-auth")
}