import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun RecipeExistsDialog(
	name: String,
	onReplace: () -> Unit,
	onModify: () -> Unit,
	onCreate: () -> Unit,
	onDismiss: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("Recipe Already Exists") },
		text = {
			Column {
				Text("Another Name")
				// Add input field if you want user to enter a new name
				// OutlinedTextField(...)
			}
		},
		confirmButton = {
			Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
				Button(
					onClick = onReplace,
					colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
				) {
					Text("Replace")
				}
				Button(onClick = onModify) {
					Text("Modify")
				}
				Button(onClick = onCreate) {
					Text("Create")
				}
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) { Text("Cancel") }
		}
	)
}