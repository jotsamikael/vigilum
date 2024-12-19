package com.example.vigilum.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.vigilum.R
//Google fonts
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Montserrat")

val fontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider
    )
)

// Set of Material typography styles to start with
val baiJumreeFontFamily = FontFamily(
    androidx.compose.ui.text.font.Font(R.font.baijamjuree_regular, FontWeight.Normal),
    androidx.compose.ui.text.font.Font(R.font.baijamjuree_bold, FontWeight.Bold),
    androidx.compose.ui.text.font.Font(R.font.baijamjuree_semibold, FontWeight.SemiBold),
    androidx.compose.ui.text.font.Font(R.font.baijamjuree_light, FontWeight.Light),
    androidx.compose.ui.text.font.Font(R.font.baijamjuree_extralight, FontWeight.ExtraLight)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = baiJumreeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */

)