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
                @Suppress("DEPRECATION")
                srcDir("${buildDir.path}/openapi")
            }
        }
    }
}


openApiGenerate {
    generatorName = "kotlin"
    validateSpec = true
    val path = File(File(projectDir.parent).parent).parent
    inputSpec = "$path\\RandomCoffee.openapi.yaml".replace("\\", "/")
    ignoreFileOverride = "${projectDir.path}/openapi-generator-ignore"
    outputDir = "${layout.buildDirectory.asFile.get().path}/openapi".replace("\\", "/")

    additionalProperties = mapOf(
        "library" to "jvm-retrofit2",
//        "serializationLibrary" to "kotlinx_serialization",
//        "serializationLibrary" to "gson",
        "useCoroutines" to "true"
    )

    apiPackage = "dev.suai.randomcoffee.schema.api"
    modelPackage = "dev.suai.randomcoffee.schema.model"
}

tasks.preBuild {
    dependsOn(tasks.openApiGenerate)
}

//val test by tasks.getting(Test::class) {
//    useJUnitPlatform { }
//}

ksp {
    arg("circuit.codegen.mode", "hilt") // or "kotlin_inject_anvil"
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
    implementation(libs.androidx.material.icons.extended)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // datastore
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)
//    implementation(libs.androidx.datastore.core.android)
//    implementation(libs.androidx.datastore.preferences.core.jvm)


    // jwt
    implementation(libs.jwtdecode)


    // test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlintest.runner.junit5)


    // android test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)


    // api
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.converter.scalars)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.converter.kotlinx.serialization)


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
    api(libs.circuit.codegen.annotations)
    ksp(libs.circuit.codegen)


    // api UNUSED
    implementation(project(":api"))
}