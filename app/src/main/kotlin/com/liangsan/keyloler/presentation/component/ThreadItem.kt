package com.liangsan.keyloler.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.presentation.utils.toHTMLAnnotatedString

@Composable
fun ThreadItem(modifier: Modifier = Modifier, thread: Thread) {
    Column(modifier = modifier) {
        Text(thread.subject, style = MaterialTheme.typography.titleSmall)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                thread.dateline.toHTMLAnnotatedString(),
                style = MaterialTheme.typography.bodySmall
            )
            Text("tid: ${thread.tid}", style = MaterialTheme.typography.bodySmall)
        }
    }
}