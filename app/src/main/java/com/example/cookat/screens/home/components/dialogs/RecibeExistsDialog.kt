import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeExistsDialog(
	name: String,
	onNameChange: (String) -> Unit,
	onReplace: () -> Unit,
	onModify: () -> Unit,
	onTryAgain: () -> Unit,
	onDismiss: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		modifier = Modifier.widthIn(min = 320.dp, max = 420.dp),
		title = { Text("Ya creaste esa receta.") },
		text = {
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(16.dp)
			) {
				// Top section
				Text("Probar otro name:")
				OutlinedTextField(
					value = name,
					onValueChange = onNameChange,
					label = { Text("Nombre") },
					singleLine = true,
					modifier = Modifier.fillMaxWidth()
				)
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(12.dp)
				) {
					Button(
						modifier = Modifier.weight(1f),
						onClick = onTryAgain,
						enabled = name.isNotBlank()
					) { Text("Reintentar") }
					TextButton(
						modifier = Modifier.weight(1f),
						onClick = onDismiss
					) { Text("Cancelar") }
				}

				// Divider and alternative actions
				Spacer(Modifier.height(8.dp))
				HorizontalDivider()
				Spacer(Modifier.height(8.dp))
				Text(
					"O sino ...",
					style = MaterialTheme.typography.labelMedium,
					modifier = Modifier.padding(start = 4.dp)
				)
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(10.dp)
				) {
					Button(
						modifier = Modifier.weight(1f),
						onClick = onReplace,
						colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
					) { Text("Reemplaza") }
					Button(
						modifier = Modifier.weight(1f),
						onClick = onModify
					) { Text("Modifica") }
				}
			}
		},
		// No confirmButton or dismissButton, all actions are in the text section for layout control
		confirmButton = {},
		dismissButton = {}
	)
}