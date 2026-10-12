package com.lihan.member.presentation.member.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.presentation.designsystem.PrimaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.core.presentation.ui.member.toGradient
import com.lihan.core.presentation.ui.member.toLevelImageResId
import com.lihan.member.presentation.R

@Composable
fun MemberCard(
    userName: String,
    pointsInt: Int,
    level: MemberLevel,
    modifier: Modifier = Modifier,
    profileImageUrl: String?=null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .surface(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                border = null,
                shadowElevation = 8.dp.value
            )
            .background(
                brush = level.toGradient(),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
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
                            contentDescription = null
                        )
                    }
                }
                Column {
                    Text(
                        text = userName
                    )
                }
            }

            Image(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 36.dp)
                    .size(64.dp),
                painter = painterResource(R.drawable.kocha_20261007),
                contentDescription = null
            )
            Spacer(Modifier.height(12.dp))
            Row{
                Text(
                    text = "累積點數"
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline()
                        .padding(horizontal = 12.dp)
                        .offset(y = (-4).dp),
                    text = pointsInt.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "pts"
                )
                Spacer(Modifier.weight(1f))
            }

        }



        Image(
            modifier = Modifier.align(Alignment.CenterEnd).size(120.dp),
            painter = painterResource(level.toLevelImageResId()),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun MemberCardPreview() {
    GongchaTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            MemberLevel.entries.forEach { memberLevel ->
                MemberCard(
                    profileImageUrl = "",
                    userName = "Koala",
                    pointsInt = 100,
                    level = memberLevel
                )
            }
        }
    }
}

fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Float,
) =
    this.then(
        if (shadowElevation > 0f) {
            Modifier.graphicsLayer(
                shadowElevation = shadowElevation,
                shape = shape,
                clip = false,
            )
        } else {
            Modifier
        }
    )
        .then(if (border != null) Modifier.border(border, shape) else Modifier)
        .background(color = backgroundColor, shape = shape)
        .clip(shape)