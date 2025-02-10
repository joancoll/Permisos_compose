package cat.dam.andy.permisos_compose.permissions

data class PermissionData(
    val permission: String,
    val permissionInfo: String,
    val grantedMessage: String,
    val deniedMessage: String,
    val permanentDeniedMessage: String
)