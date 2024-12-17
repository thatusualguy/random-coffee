import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

plugins {
    id("java-library")
    id( "org.openapi.generator")
    kotlin("jvm")
}


openApiValidate{
    val path = File(File(projectDir.parent).parent).parent
    inputSpec = "$path\\RandomCoffee.openapi.yaml".replace("\\","/")
}

openApiGenerate {
    generatorName = "kotlin"
    validateSpec = false
    val path = File(File(projectDir.parent).parent).parent
    inputSpec = "$path\\RandomCoffee.openapi.yaml".replace("\\","/")

    outputDir = "${layout.buildDirectory.asFile.get().toPath()}/openapi".replace("\\","/")

    additionalProperties = mapOf(
        "library" to "jvm-retrofit2",
        "serializationLibrary" to "kotlinx_serialization",
        "useCoroutines" to "true"
    )

    apiPackage = "dev.suai.randomcoffee.schema.api"
    modelPackage = "dev.suai.randomcoffee.schema.model"
}

tasks.build {
    dependsOn(tasks.openApiGenerate)
}


//test {
//    useJUnitPlatform()
//}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation( libs.kotlin.reflect)
    implementation( libs.moshi.kotlin)
    implementation( libs.moshi.adapters)
    implementation( libs.logging.interceptor)
    implementation( libs.retrofit)
    implementation( libs.converter.moshi)
    implementation( libs.converter.scalars)
    testImplementation( libs.kotlintest.runner.junit5)
    implementation(kotlin("stdlib-jdk8"))
}
//repositories {
//    mavenCentral()
//}
//
//kotlin {
//    jvmToolchain(21)
//}