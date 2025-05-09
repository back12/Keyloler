package com.liangsan.keyloler.presentation.component

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.liangsan.keyloler.domain.model.SteamIframeData
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import org.koin.compose.koinInject

private val steamViewShape = RoundedCornerShape(4.dp)
private val steamViewBackgroundGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF3B4351),
        Color(0xFF282E39)
    )
)
private val purchaseButtonBackgroundGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF6fa720),
        Color(0xFF588a1b)
    )
)

@Composable
fun SteamView(modifier: Modifier = Modifier, src: String) {
    val repo = koinInject<ThreadsRepository>()
    var data = remember { mutableStateOf<SteamIframeData?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        data.value = repo.parseSteamIframe(src)
    }

    data.value?.let {
        SteamView(modifier = modifier, data = it) {
            val intent = Intent(Intent.ACTION_VIEW, it.gameUrl.toUri())
            context.startActivity(intent)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SteamView(modifier: Modifier = Modifier, data: SteamIframeData, onClick: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(bottom = 14.dp)
                .shadow(elevation = 8.dp, shape = steamViewShape)
                .fillMaxWidth()
                .clip(steamViewShape)
                .background(steamViewBackgroundGradient)
                .clickable(onClick = onClick)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 15.dp,
                        vertical = 10.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        data.title,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        data.titleExt,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF9E9E9E)),
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AsyncImage(
                        data.image,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .widthIn(max = 180.dp)
                            .height(50.dp)
                    )
                    Text(
                        data.desc,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFFC9C9C9),
                            baselineShift = BaselineShift(1f)
                        )
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            data.gamePurchasePrice?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFC6D4DF)),
                    modifier = Modifier
                        .background(Color.Black)
                        .shadow(elevation = 4.dp)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
            Text(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(2.dp))
                    .clip(RoundedCornerShape(2.dp))
                    .background(brush = purchaseButtonBackgroundGradient)
                    .clickable(onClick = onClick)
                    .padding(horizontal = 12.dp, vertical = 7.dp),
                text = data.addToCartButton,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFFD2EFA9),
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(3f, 3f),
                        blurRadius = 3f
                    )
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 500)
@Composable
private fun SteamViewPreview() {
    val widget = SteamIframeData(
        title = "购买 Terraria - Commercial License",
        titleExt = "就在 Steam",
        image = "https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/105600/capsule_184x69.jpg?t=1731252354",
        desc = "挖掘，战斗，探索，建造！在这个动感十足的冒险游戏里没有什么是不可能的。四人包依然可用！",
        gamePurchasePrice = "HK\$ 52",
        addToCartButton = "在 Steam 上购买",
        gameUrl = ""
    )
    SteamView(
        modifier = Modifier.padding(20.dp),
        data = widget,
        onClick = {}
    )
}