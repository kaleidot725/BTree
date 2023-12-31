import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.ksp)
    alias(libs.plugins.licensee)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget()
    jvm("desktop")
    ios()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Compose application framework"
        homepage = "empty"
        ios.deploymentTarget = "11.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    dependencies {
        lintChecks("com.slack.lint:slack-lint-checks:0.4.0")
        lintChecks("com.slack.lint.compose:compose-lint-checks:1.2.0")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.voyager.navigator)
                implementation(libs.napier)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.coroutines)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.lyricist)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(libs.kotlinx.coroutines.android)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }

        val iosMain by getting {
            dependencies {
            }
        }
    }
}

ktlint {
    version.set("0.49.1")
    verbose.set(true)
    ignoreFailures.set(true)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
    }
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("build/generated/ksp/metadata/commonMain/kotlin")
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

android {
    namespace = "jp.albites.btree"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34

        applicationId = "jp.albites.btree.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources/")

    packaging {
        resources.excludes.add("META-INF/**")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "jp.albites.btree.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

// -- Licensee --
licensee {
    allow("Apache-2.0")
    allow("MIT")
}

tasks.register("updateLicences") {
    mustRunAfter(tasks.check)
    data class FromTo(val from: File, val to: File)
    val android = FromTo(
        from = File("composeApp/build/reports/licensee/androidRelease/artifacts.json"),
        to = File("composeApp/src/commonMain/resources/licensee/android/artifacts.json"),
    )
    val desktop = FromTo(
        from = File("composeApp/build/reports/licensee/desktop/artifacts.json"),
        to = File("composeApp/src/commonMain/resources/licensee/desktop/artifacts.json"),
    )
    val ios = FromTo(
        from = File("composeApp/build/reports/licensee/iosArm64/artifacts.json"),
        to = File("composeApp/src/commonMain/resources/licensee/ios/artifacts.json"),
    )
    listOf(android, desktop, ios).forEach { item ->
        item.from.copyTo(item.to, true)
    }
}
// -- Licensee --

// -- Lyricist Workaround --
dependencies {
    add("kspCommonMainMetadata", libs.lyricist.processor)
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

tasks.withType<BaseKtLintCheckTask>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}
// -- Lyricist Workaround --
