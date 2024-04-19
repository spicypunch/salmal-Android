package kr.jm.salmal_android.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

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

    fun createImageFile(context: Context): Uri? {
        val timeStamp = SimpleDateFormat("yyMMdd_HHmm ss", Locale.KOREA).format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$timeStamp.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    fun requestLogin(context: Context) {
        /**
         * 카카오계정으로 로그인 공통 callback 구성
         * 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
         */
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.i("Kakao", "로그인 실패 $error")
            } else if (token != null) {
                Log.i(
                    "Kakao",
                    token.toString()
                )
            }
        }

        /**
         * 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
         */
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.i("Kakao", "카카오톡으로 로그인 실패", error)

                    /**
                     * 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                     * 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                     */
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    /**
                     * 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                     */
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i("Kakao", token.toString())
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}
