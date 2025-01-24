package cat.dam.andy.permisos_compose

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val permissionManager by lazy { PermissionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura els permisos necessaris
        initPermissions()

        setContent {
            MaterialTheme {
                PermissionDemoUI(permissionManager)
            }
        }
    }

    private fun initPermissions() {
        permissionManager.addPermission(
            Manifest.permission.CAMERA,
            getString(R.string.cameraPermissionInfo),
            getString(R.string.cameraPermissionGranted),
            getString(R.string.cameraPermissionDenied),
            getString(R.string.cameraPermissionPermanentDenied)
        )

        permissionManager.addPermission(
            Manifest.permission.WRITE_CONTACTS,
            getString(R.string.contactsPermissionInfo),
            getString(R.string.contactsPermissionGranted),
            getString(R.string.contactsPermissionDenied),
            getString(R.string.contactsPermissionPermanentDenied)
        )
    }

    @Composable
    fun PermissionDemoUI(permissionManager: PermissionManager) {
        val permissions = permissionManager.permissionsState.collectAsState().value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            permissions.forEach { permissionData ->
                PermissionButton(
                    permissionData = permissionData,
                    onPermissionRequest = {
                        permissionManager.askForPermission(permissionData.permission)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Mostrar el diàleg de permisos quan sigui necessari
            permissionManager.ShowPermissionDialog(title = getString(R.string.permissionRequired),confirm=getString(R.string.ok),cancel=getString(R.string.cancel))
        }
    }

    @Composable
    fun PermissionButton(
        permissionData: PermissionManager.PermissionData,
        onPermissionRequest: () -> Unit
    ) {
        Button(onClick = onPermissionRequest) {
            Text(text = permissionData.permissionInfo)
        }
    }
}
