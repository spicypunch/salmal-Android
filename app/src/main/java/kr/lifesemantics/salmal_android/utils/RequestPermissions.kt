package kr.lifesemantics.salmal_android.utils

import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

fun requestPermissions(list: ArrayList<String>, useDeniedMessage: Boolean, onGranted: () -> Unit, onDenied: (List<String>) -> Unit) {
    val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            onGranted()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            onDenied(deniedPermissions)
        }
    }

    val builder = TedPermission.create()
        .setPermissionListener(permissionListener)

    builder.setPermissions(*list.toTypedArray())

    if (useDeniedMessage) {
        builder.setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
    }

    builder.check()
}
