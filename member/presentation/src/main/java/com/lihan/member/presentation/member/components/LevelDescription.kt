package com.lihan.member.presentation.member.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.presentation.designsystem.ui.theme.ArtisanalIvoryWhite
import com.lihan.core.presentation.designsystem.ui.theme.ArtisanalMutedEarthGrey
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.member.toLevelColor
import com.lihan.core.presentation.ui.member.toLevelDescriptionList

@Composable
fun LevelDescription(
    level: MemberLevel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = ArtisanalIvoryWhite,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        level.toLevelDescriptionList().forEach { description ->
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = level.toLevelColor(),
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun LevelDescriptionPreview() {
    GongchaTheme {
        LevelDescription(
            level = MemberLevel.GOLD
        )
    }
}