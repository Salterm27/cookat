package com.example.cookat.models.uiStates

enum class EditorStep {
	Description, Ingredients, Servings, Steps, Preview
}

enum class UnitOfMeasure(val display: String) {
	L("L"), ML("ml"), KG("Kg"), G("g"), UN("Un"), TSP("tsp"), TBSP("tbsp")
}

data class Ingredient(
	val name: String,
	val quantity: Double,
	val unit: UnitOfMeasure? = null
)

data class RecipeEditorUiState(
	val currentStep: EditorStep = EditorStep.Description,
	val name: String = "",
	val description: String = "",
	val ingredients: List<Ingredient> = emptyList(),
	val servings: Int = 0,
	val steps: List<String> = emptyList()
)