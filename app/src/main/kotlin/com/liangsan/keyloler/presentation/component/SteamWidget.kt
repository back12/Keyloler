package com.liangsan.keyloler.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.SteamPlatform
import com.liangsan.keyloler.domain.model.SteamWidgetData

private val steamWidgetShape = RoundedCornerShape(4.dp)
private val steamWidgetBackgroundGradient = Brush.linearGradient(
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 500)
@Composable
private fun SteamWidgetPreview() {
    val widget = SteamWidgetData(
        title = "购买 Terraria - Commercial License",
        titleExt = "就在 Steam",
        image = "https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/105600/capsule_184x69.jpg?t=1731252354",
        desc = "挖掘，战斗，探索，建造！在这个动感十足的冒险游戏里没有什么是不可能的。四人包依然可用！",
        platformImages = listOf(
            SteamPlatform.Win,
            SteamPlatform.Mac,
            SteamPlatform.Linux,
            SteamPlatform.ValveIndex
        ),
        gamePurchasePrice = "HK\$ 52",
        addToCartButton = "在 Steam 上购买"
    )
    SteamWidget(
        modifier = Modifier.padding(20.dp),
        data = widget
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SteamWidget(modifier: Modifier = Modifier, data: SteamWidgetData) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 14.dp)
                .shadow(elevation = 8.dp, shape = steamWidgetShape)
                .fillMaxWidth()
                .clip(steamWidgetShape)
                .background(steamWidgetBackgroundGradient)
                .clickable { }
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
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    overflow = FlowRowOverflow.Visible
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
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    data.platformImages.forEach {
                        Image(
                            painterResource(getPlatformImageRes(it)),
                            contentScale = ContentScale.Fit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Image(
                painterResource(R.drawable.steam_widget_logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = data.gamePurchasePrice,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFC6D4DF)),
                modifier = Modifier
                    .background(Color.Black)
                    .shadow(elevation = 4.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
            Text(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(2.dp))
                    .clip(RoundedCornerShape(2.dp))
                    .background(brush = purchaseButtonBackgroundGradient)
                    .clickable {

                    }
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

private fun getPlatformImageRes(platform: SteamPlatform): Int {
    return when (platform) {
        SteamPlatform.HTCVive -> R.drawable.icon_platform_htcvive
        SteamPlatform.Linux -> R.drawable.icon_platform_linux
        SteamPlatform.Mac -> R.drawable.icon_platform_mac
        SteamPlatform.Music -> R.drawable.icon_platform_music
        SteamPlatform.OculusRift -> R.drawable.icon_platform_oculusrift
        SteamPlatform.SteamPlay -> R.drawable.icon_steamplay
        SteamPlatform.Streaming360Video -> R.drawable.icon_streaming360video_v6
        SteamPlatform.StreamingVideo -> R.drawable.icon_streamingvideo_v6
        SteamPlatform.StreamingVideoSeries -> R.drawable.icon_streamingvideoseries_v6
        SteamPlatform.ValveIndex -> R.drawable.icon_platform_valveindex
        SteamPlatform.Win -> R.drawable.icon_platform_win
        SteamPlatform.WindowsMR -> R.drawable.icon_platform_windowsmr
    }
}