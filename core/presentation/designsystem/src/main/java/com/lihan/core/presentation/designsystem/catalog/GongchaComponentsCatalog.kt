package com.lihan.core.presentation.designsystem.catalog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.core.presentation.designsystem.ui.theme.GongchaTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GongchaComponentCatalog() {
    var textValue by remember { mutableStateOf("經典奶茶") }
    var switchChecked by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
//            TopAppBar(
//                title = { Text("貢茶元件型錄") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.surface,
//                    titleContentColor = MaterialTheme.colorScheme.onSurface
//                )
//            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "新增")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Buttons Section
//            Text("按鈕類型", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Button(onClick = {}) { Text("Filled") }
//                FilledTonalButton(onClick = {}) { Text("Tonal") }
//                OutlinedButton(onClick = {}) { Text("Outlined") }
//                TextButton(onClick = {}) { Text("Text") }
//            }

            // Cards Section
            Text("卡片類型", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Filled Card", style = MaterialTheme.typography.titleSmall)
                    Text("使用 SurfaceVariant 底色呈現", style = MaterialTheme.typography.bodyMedium)
                }
            }

            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Elevated Card", style = MaterialTheme.typography.titleSmall)
                    Text("帶有 Material 3 高度陰影效果", style = MaterialTheme.typography.bodyMedium)
                }
            }

            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Outlined Card", style = MaterialTheme.typography.titleSmall)
                    Text("使用 Outline 邊框線條", style = MaterialTheme.typography.bodyMedium)
                }
            }

            // Inputs Section
//            Text("輸入與選取", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
//            OutlinedTextField(
//                value = textValue,
//                onValueChange = { textValue = it },
//                label = { Text("飲品名稱") },
//                modifier = Modifier.fillMaxWidth(),
//                leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) }
//            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("啟用加料通知", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = switchChecked,
                    onCheckedChange = { switchChecked = it }
                )
            }

            // Typography Section
            Text("文字層級展示", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Display Small: 貢茶 Gong cha", style = MaterialTheme.typography.displaySmall)
                    Text("Headline Small: 經典原葉茶系列", style = MaterialTheme.typography.headlineSmall)
                    Text("Title Medium: 阿里山烏龍珍珠奶茶", style = MaterialTheme.typography.titleMedium)
                    Text("Body Medium: 傳承台灣在地製茶工藝，茶香甘醇、圓潤順口。", style = MaterialTheme.typography.bodyMedium)
                    Text("Label Large: 加入購物車 NT$ 65", style = MaterialTheme.typography.labelLarge)
                    Text("Label Small: 甜度固定 / 微冰 / 推薦加料", style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun GongchaCatalogLightPreview() {
    GongchaTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            GongchaComponentCatalog()
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun GongchaCatalogDarkPreview() {
    GongchaTheme(darkTheme = true, dynamicColor = false) {
        Surface {
            GongchaComponentCatalog()
        }
    }
}