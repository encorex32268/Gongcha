package com.lihan.member.presentation.member.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.designsystem.ui.theme.MemberPointsColor
import com.lihan.core.presentation.designsystem.ui.theme.MemberTextColor
import com.lihan.core.presentation.ui.member.toGradient
import com.lihan.core.presentation.ui.member.toLevelImageResId
import com.lihan.core.presentation.ui.member.toNextLevelCondition
import com.lihan.member.presentation.R

@Composable
fun MemberCard(
    userName: String,
    pointsInt: Int,
    level: MemberLevel,
    modifier: Modifier = Modifier,
    profileImageUrl: String?=null,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = level.toGradient(),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(36.dp),
                                contentAlignment = Alignment.Center
                            ){
                                if (profileImageUrl != null){
                                    AsyncImage(
                                        model = profileImageUrl,
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(36.dp),
                                        contentDescription = null
                                    )
                                }else{
                                    Icon(
                                        imageVector = GongchaIcons.Member,
                                        contentDescription = null,
                                        tint = MemberTextColor
                                    )
                                }
                            }
                            Text(
                                text = userName,
                                style = MaterialTheme.typography.titleLarge,
                                color = MemberTextColor
                            )
                        }
                        LevelTag(level)
                    }
                    Spacer(Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.Bottom),
                        painter = painterResource(level.toLevelImageResId()),
                        contentDescription = null,
                    )
                }
                LevelDescription(level)
                Row(
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        Text(
                            text = stringResource(R.string.member_points),
                            style = MaterialTheme.typography.titleMedium,
                            color = MemberTextColor
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ){
                            Text(
                                text = pointsInt.toString(),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = MemberPointsColor
                                )
                            )
                            Text(
                                text = stringResource(R.string.points),
                                style = MaterialTheme.typography.titleMedium,
                                color = MemberPointsColor
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Bottom),
                        text = level.toNextLevelCondition(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                }
            }
        }

    }
}

@PreviewLightDark
@Composable
private fun MemberCardPreview() {
    GongchaTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            MemberLevel.entries.forEach { memberLevel ->
                if (memberLevel != MemberLevel.NONE){
                    MemberCard(
                        profileImageUrl = null,
                        userName = "Koala",
                        pointsInt = 100,
                        level = memberLevel
                    )

                }
            }
        }
    }
}