package com.lihan.member.presentation.member.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.presentation.designsystem.ui.theme.GongchaDarkInverseSurface
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.member.toLevelColor
import com.lihan.core.presentation.ui.member.toStringResource

@Composable
fun LevelTag(
    level: MemberLevel,
    modifier: Modifier = Modifier
) {
    val levelColor = level.toLevelColor()
    Row(
        modifier = modifier
            .background(
                color = GongchaDarkInverseSurface,
                shape = CircleShape
            )
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector = GongchaIcons.CircleStar,
            contentDescription = null,
            tint = level.toLevelColor()
        )
        Text(
            text = level.toStringResource(),
            color = level.toLevelColor(),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun LevelTagPreview() {
    GongchaTheme {
        LevelTag(
            level = MemberLevel.GOLD
        )
    }
}