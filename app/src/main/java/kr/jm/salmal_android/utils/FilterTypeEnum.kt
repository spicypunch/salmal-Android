package kr.jm.salmal_android.utils

import androidx.compose.ui.graphics.ColorMatrix

enum class FilterTypeEnum {
    DEFAULT,
    SEPIA,
    BRIGHTNESS,
    CONTRAST,
    NEGATIVE,
    BLACK_WHITE;

    companion object {
        fun getFilter(type: FilterTypeEnum): ColorMatrix {
            return when (type) {
                DEFAULT -> {
                    ColorMatrix(
                        floatArrayOf(
                            1f, 0f, 0f, 0f, 0f,
                            0f, 1f, 0f, 0f, 0f,
                            0f, 0f, 1f, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                }
                SEPIA -> {
                    ColorMatrix(
                        floatArrayOf(
                            0.393f, 0.769f, 0.189f, 0f, 0f,
                            0.349f, 0.686f, 0.168f, 0f, 0f,
                            0.272f, 0.534f, 0.131f, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                }

                BRIGHTNESS -> {
                    val brightness = 1.5f
                    ColorMatrix(
                        floatArrayOf(
                            brightness, 0f, 0f, 0f, 0f,
                            0f, brightness, 0f, 0f, 0f,
                            0f, 0f, brightness, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                }

                CONTRAST -> {
                    val contrast = 1.5f
                    val translate = (-0.5f * contrast + 0.5f) * 255f
                    ColorMatrix(
                        floatArrayOf(
                            contrast, 0f, 0f, 0f, translate,
                            0f, contrast, 0f, 0f, translate,
                            0f, 0f, contrast, 0f, translate,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                }

                NEGATIVE -> {
                    ColorMatrix(
                        floatArrayOf(
                            -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
                            0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
                            0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
                            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                        )
                    )
                }

                BLACK_WHITE -> {
                    ColorMatrix().apply { setToSaturation(0f) }
                }
            }
        }
    }
}