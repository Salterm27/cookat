package com.example.cookat.data.remote

import com.example.cookat.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {

	val client: SupabaseClient by lazy {
		createSupabaseClient(
			supabaseUrl = BuildConfig.SUPABASE_URL,
			supabaseKey = BuildConfig.SUPABASE_ANON_KEY
		) {
			install(Postgrest)
			install(Auth)
		}
	}
}