package kr.jm.salmal_android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kr.lifesemantics.salmal_android.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_gov_regular, FontWeight.Normal),
    Font(R.font.pretendard_gov_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_gov_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.pretendard_gov_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_gov_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pretendard_gov_thin, FontWeight.Thin, FontStyle.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)