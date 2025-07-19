package com.example.cookat.data.local.converters

import androidx.room.TypeConverter
import com.example.cookat.models.dbModels.recipes.IngredientModel
import com.example.cookat.models.dbModels.recipes.StepModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
	private val gson = Gson()

	@TypeConverter
	fun fromIngredientList(value: List<IngredientModel>?): String = gson.toJson(value)

	@TypeConverter
	fun toIngredientList(value: String): List<IngredientModel>? {
		val type = object : TypeToken<List<IngredientModel>>() {}.type
		return gson.fromJson(value, type)
	}

	@TypeConverter
	fun fromStepList(value: List<StepModel>?): String = gson.toJson(value)

	@TypeConverter
	fun toStepList(value: String): List<StepModel>? {
		val type = object : TypeToken<List<StepModel>>() {}.type
		return gson.fromJson(value, type)
	}
}