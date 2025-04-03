package com.liangsan.keyloler.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import com.liangsan.keyloler.presentation.utils.toHTMLAnnotatedString

@Composable
fun ThreadItem(
    modifier: Modifier = Modifier,
    thread: Thread,
    onClick: () -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .then(modifier)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Avatar(getAvatarUrl(thread.authorId))
                Spacer(Modifier.width(12.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        thread.author,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row {
                        Text(
                            thread.dateline.toHTMLAnnotatedString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            stringResource(R.string.views_prefix, thread.views),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            stringResource(
                                R.string.last_post_prefix,
                                thread.lastPost.toHTMLAnnotatedString()
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.alignByBaseline()
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(16.dp))
            Text(
                thread.subject,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Divider()
    }
}