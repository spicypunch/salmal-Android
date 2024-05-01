package kr.jm.salmal_android.data.request

data class SignUpRequest(
    val marketingInformationConsent: Boolean,
    val nickName: String,
    val providerId: String
)