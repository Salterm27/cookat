import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NewRecipeNameDialog(
	name: String,
	error: String?,
	isChecking: Boolean,
	onNameChange: (String) -> Unit,
	onDismiss: () -> Unit,
	onSubmit: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("New Recipe Name", style = MaterialTheme.typography.titleLarge) },
		text = {
			Column {
				OutlinedTextField(
					value = name,
					onValueChange = onNameChange,
					label = { Text("Name") },
					singleLine = true
				)
				if (error != null) {
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						text = error,
						color = MaterialTheme.colorScheme.error, // Use your theme's error color
						style = MaterialTheme.typography.bodySmall // Or whatever fits your design
					)
				}
			}
		},
		confirmButton = {
			TextButton(
				onClick = onSubmit,
				enabled = !isChecking
			) { Text("Send") }
		},
		dismissButton = {
			TextButton(onClick = onDismiss) { Text("Cancel") }
		}
	)
}