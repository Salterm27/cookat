import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookat.models.uiStates.EditorStep
import com.example.cookat.screens.recipes.editor.WizardScreens.DescriptionStep
import com.example.cookat.screens.recipes.editor.WizardScreens.IngredientsStep
import com.example.cookat.viewmodels.recipes.RecipeEditorViewModel

@Composable
fun RecipeEditor(
	recipeName: String,
	onFinish: () -> Unit,
	onCancel: () -> Unit,
	viewModel: RecipeEditorViewModel = viewModel()
) {
	val state by viewModel.uiState.collectAsState()

	when (state.currentStep) {
		EditorStep.Description -> DescriptionStep(
			recipeName = recipeName,
			description = state.description,
			onDescriptionChange = { viewModel.setDescription(it) },
			onNext = { viewModel.nextStep() },
			onCancel = onCancel
		)

		EditorStep.Ingredients -> IngredientsStep(
			ingredients = state.ingredients,
			onAddIngredient = { name, qty, unit -> viewModel.addIngredient(name, qty, unit) },
			onRemoveIngredient = { idx -> viewModel.removeIngredient(idx) },
			onNext = { viewModel.nextStep() },
			onBack = { viewModel.prevStep() }
		)
		// Add Servings, Steps, Preview similarly
		else -> Text("Step not implemented yet.")
	}
}