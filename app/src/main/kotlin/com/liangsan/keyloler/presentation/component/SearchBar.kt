package com.liangsan.keyloler.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.liangsan.keyloler.R
import com.liangsan.keyloler.presentation.utils.clearFocusOnKeyboardDismiss

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchInput: String,
    textStyle: TextStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
    onSearchInputChange: (String) -> Unit,
    onClearSearchInput: () -> Unit,
    onSearch: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        modifier = modifier
            .height(56.dp)
            .focusRequester(focusRequester)
            .clearFocusOnKeyboardDismiss(),
        value = searchInput,
        onValueChange = onSearchInputChange,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp)
            ) {
                if (searchInput.isEmpty()) {
                    Text(
                        stringResource(R.string.search_placeholder_text),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = textStyle
                    )
                } else {
                    IconButton(
                        onClick = onClearSearchInput,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.round_close_24),
                            contentDescription = stringResource(R.string.clear_search_bar_input)
                        )
                    }
                }

                innerTextField()
            }
        },
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    )
}