plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.compose)
    id("org.openapi.generator")
}

android {
    namespace = "dev.suai.randomcoffee"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.suai.randomcoffee"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets {
        getByName("main") {
            kotlin {
                srcDir("${buildDir.path}/openapi")
            }
        }
    }

}

openApiValidate {
    val path = File(File(projectDir.parent).parent).parent
    inputSpec = "$path\\RandomCoffee.openapi.yaml".replace("\\", "/")
}

openApiGenerate {
    generatorName = "kotlin"
    validateSpec = false
    val path = File(File(projectDir.parent).parent).parent
    inputSpec = "$path\\RandomCoffee.openapi.yaml".replace("\\", "/")
//    inputSpec = "${projectDir.path}/RandomCoffee.openapi.yaml".replace("\\","/")

    outputDir = "${buildDir.path}/openapi".replace("\\", "/")

    additionalProperties = mapOf(
        "library" to "jvm-retrofit2",
        "serializationLibrary" to "kotlinx_serialization",
        "useCoroutines" to "true"
    )

    apiPackage = "dev.suai.randomcoffee.schema.api"
    modelPackage = "dev.suai.randomcoffee.schema.model"
}

tasks.preBuild {
    dependsOn(tasks.openApiGenerate)
}



dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // api
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.converter.scalars)
    implementation(libs.kotlinx.serialization.json)

    //ViewModel
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // images
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // compose destinations
    implementation(libs.compose.navigation.core)
    ksp(libs.compose.navigation.ksp)

    // animated bottom bar
    implementation(libs.animated.navigation.bar)

    // Yet Another Kotlin COmpose Validation
    implementation(libs.yakov)

    // circuit
    implementation(libs.circuit.foundation)
    implementation(libs.slack.circuitx.android)

    implementation(project(":api"))
}