import java.util.Properties

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.serialization)
	id("com.google.devtools.ksp")

}

android {
	namespace = "com.example.cookat"
	compileSdk = 36


	defaultConfig {
		applicationId = "com.example.cookat"
		minSdk = 29
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		// 👇 NEW: Load from local.properties
		val localProps = Properties()
		localProps.load(rootProject.file("local.properties").inputStream())

		buildConfigField("String", "SUPABASE_URL", "\"${localProps["SUPABASE_URL"]}\"")
		buildConfigField("String", "SUPABASE_ANON_KEY", "\"${localProps["SUPABASE_ANON_KEY"]}\"")
		buildConfigField("String", "COOKAT_URL", "\"${localProps["COOKAT_URL"]}\"")
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
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlin {
		compilerOptions {
			jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
		}
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
}

dependencies {
	val composeBom = platform("androidx.compose:compose-bom:2025.05.00")
	implementation(composeBom)
	androidTestImplementation(composeBom)
	// Material Design 3
	implementation(libs.composeMaterial3)
	// UI
	implementation(libs.composeUi)
	// Tooling support (Previews, etc.)
	implementation(libs.composeUiToolingPreview)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.navigation)
	implementation(libs.lifecycle)
	implementation(libs.fragment)
	implementation(libs.androidx.navigation.fragment)
	implementation(platform(libs.supabaseBom))
	implementation(libs.supabasePostgrest)
	implementation(libs.supabaseAuth)
	implementation(libs.ktorClientAndroid)
	implementation(libs.retrofit)
	implementation(libs.retrofit.gson)
	implementation(libs.okhttp)
	implementation(libs.okhttp.logging)
	implementation(libs.datastore.preferences)
	implementation(libs.room.runtime)
	implementation(libs.room.ktx)
	ksp(libs.room.compiler)
	implementation(libs.compose.material.icons.extended)


	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)

}