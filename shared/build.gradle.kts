import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    ksp {
        arg("circuit.codegen.mode", "kotlin_inject_anvil")
        arg("kotlin-inject-anvil-contributing-annotations", "com.slack.circuit.codegen.annotations.CircuitInject")
    }
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.inject.anvil.runtime)
            implementation(libs.kotlin.inject.anvil.runtime.optional)
            implementation(libs.kotlin.inject.runtime)

            implementation(libs.circuit.foundation)
            implementation(libs.circuit.overlay)
            implementation(libs.circuit.codegen.annotation)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.assertk)
        }
    }
}

android {
    namespace = "com.example.kotlininjecttest"
    compileSdk = 34
    defaultConfig {
        minSdk = 30
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    add("kspAndroid", libs.kotlin.inject.compiler)
    add("kspIosArm64", libs.kotlin.inject.compiler)
    add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)

    add("kspAndroid", libs.kotlin.inject.anvil.compiler)
    add("kspIosArm64", libs.kotlin.inject.anvil.compiler)
    add("kspIosSimulatorArm64", libs.kotlin.inject.anvil.compiler)

    add("kspAndroid", libs.circuit.codegen.ksp)
    add("kspIosArm64", libs.circuit.codegen.ksp)
    add("kspIosSimulatorArm64", libs.circuit.codegen.ksp)
}
