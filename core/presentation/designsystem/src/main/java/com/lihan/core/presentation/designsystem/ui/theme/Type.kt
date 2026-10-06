package com.lihan.core.presentation.designsystem.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.core.presentation.designsystem.R


val AppDefaultFontFamily = FontFamily.SansSerif


val Typography = Typography(
    // Display: 用於超大醒目標題、大促銷 Banner、滿額折抵大數字
    displayLarge = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    // Headline: 用於一級頁面大標、各分類主視覺區塊
    headlineLarge = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // Title: 用於 TopAppBar、飲品卡片名稱、分組標題
    titleLarge = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Body: 用於商品介紹內文、規格說明、長篇內容
    bodyLarge = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // Label: 用於按鈕 CTA、商品標籤（Tag/Badge）、底部導航、輔助小字
    labelLarge = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppDefaultFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

@Preview(name = "貢茶字體層級預覽", showBackground = true, backgroundColor = 0xFFFFF8F7)
@Composable
private fun GongchaTypographyPreview() {
    GongchaTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Kocha Typography Scale",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider()

                TypographyPreviewItem("Display Large (57sp / Black)", "經典好茶", MaterialTheme.typography.displayLarge)
                TypographyPreviewItem("Display Medium (45sp / Bold)", "醇香奶蓋", MaterialTheme.typography.displayMedium)
                TypographyPreviewItem("Display Small (36sp / Bold)", "珍珠奶茶 $65", MaterialTheme.typography.displaySmall)

                HorizontalDivider()

                TypographyPreviewItem("Headline Large (32sp / Bold)", "熱門推薦排行", MaterialTheme.typography.headlineLarge)
                TypographyPreviewItem("Headline Medium (28sp / Bold)", "季節限定特調", MaterialTheme.typography.headlineMedium)
                TypographyPreviewItem("Headline Small (24sp / SemiBold)", "嚴選高山原茶", MaterialTheme.typography.headlineSmall)

                HorizontalDivider()

                TypographyPreviewItem("Title Large (22sp / Bold)", "黑糖珍珠鮮奶", MaterialTheme.typography.titleLarge)
                TypographyPreviewItem("Title Medium (16sp / SemiBold)", "阿里山烏龍茶 (大杯)", MaterialTheme.typography.titleMedium)
                TypographyPreviewItem("Title Small (14sp / SemiBold)", "甜度與冰塊客製化", MaterialTheme.typography.titleSmall)

                HorizontalDivider()

                TypographyPreviewItem(
                    label = "Body Large (16sp / Regular)",
                    sample = "精選優質茶葉，傳承古法烘焙工藝，茶湯清香甘醇回甘悠長。",
                    style = MaterialTheme.typography.bodyLarge
                )
                TypographyPreviewItem(
                    label = "Body Medium (14sp / Regular)",
                    sample = "選用特濃紐西蘭純淨乳源，搭配手工熬煮黑糖珍珠，香濃滑順。",
                    style = MaterialTheme.typography.bodyMedium
                )
                TypographyPreviewItem(
                    label = "Body Small (12sp / Regular)",
                    sample = "※ 每杯飲品總熱量約 320 大卡，糖量 35 克。若有特殊過敏體質請洽服務人員。",
                    style = MaterialTheme.typography.bodySmall
                )

                HorizontalDivider()

                TypographyPreviewItem("Label Large (14sp / SemiBold)", "加入購物車", MaterialTheme.typography.labelLarge)
                TypographyPreviewItem("Label Medium (12sp / Medium)", "人氣熱銷 No.1", MaterialTheme.typography.labelMedium)
                TypographyPreviewItem("Label Small (11sp / Medium)", "微糖 / 去冰 / 加仙草", MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun TypographyPreviewItem(label: String, sample: String, style: TextStyle) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = sample,
            style = style,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
