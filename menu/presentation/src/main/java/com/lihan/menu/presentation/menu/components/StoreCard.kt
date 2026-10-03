package com.lihan.menu.presentation.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.domain.Store
import com.lihan.core.presentation.designsystem.SecondaryButton
import com.lihan.core.presentation.designsystem.ui.theme.GongchaIcons
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme
import com.lihan.menu.presentation.R
import com.lihan.menu.presentation.menu.preview.StorePreview

@Composable
fun StoreCard(
    store: Store,
    onChangeStoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = store.name,
                    style = MaterialTheme.typography.titleMedium
                )

                SecondaryButton(
                    text = stringResource(R.string.change_store),
                    onClick = onChangeStoreClick,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = GongchaIcons.Location,
                            contentDescription = store.address
                        )
                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = GongchaIcons.Location,
                    tint = MaterialTheme.colorScheme.outlineVariant,
                    contentDescription = store.name
                )
                Text(
                    text = store.address,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = GongchaIcons.Phone,
                    tint = MaterialTheme.colorScheme.outlineVariant,
                    contentDescription = store.phoneNumber
                )
                Text(
                    text = store.phoneNumber,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = GongchaIcons.Calendar,
                    tint = MaterialTheme.colorScheme.outlineVariant,
                    contentDescription = store.displayHours
                )
                Text(
                    text = store.displayHours,
                    style = MaterialTheme.typography.bodySmall
                )
            }



        }

    }

}


@Preview(showBackground = true)
@Composable
private fun StoreCardPreview() {
    GongchaTheme {
        StoreCard(
            store = StorePreview.store,
            onChangeStoreClick = {}
        )
    }
}