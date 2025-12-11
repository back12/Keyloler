package com.liangsan.keyloler.presentation.component

import android.text.Html
import android.text.Spanned
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.util.fastForEach
import androidx.core.text.HtmlCompat
import com.liangsan.keyloler.presentation.theme.LightBlue
import com.liangsan.keyloler.presentation.utils.ContentHandlerReplacementTag
import com.liangsan.keyloler.presentation.utils.NewLineSpan
import com.liangsan.keyloler.presentation.utils.NoDrawableImageSpan
import com.liangsan.keyloler.presentation.utils.SteamIframeSpan
import com.liangsan.keyloler.presentation.utils.TagHandler
import com.liangsan.keyloler.presentation.utils.onTap
import com.liangsan.keyloler.presentation.utils.toAnnotatedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val defaultLinkStyle = TextLinkStyles(
    SpanStyle(
        color = LightBlue,
        textDecoration = TextDecoration.Underline
    )
)

@Stable
data class RichTextState(
    val elements: List<ElementComposable>
)

@Composable
fun rememberRichTextState(
    content: String,
    linkStyles: TextLinkStyles? = defaultLinkStyle,
    linkInteractionListener: LinkInteractionListener? = null
) = produceState(
    RichTextState(elements = emptyList())
) {
    withContext(Dispatchers.Default) {
        value = value.copy(
            elements = Html.fromHtml(
                "<$ContentHandlerReplacementTag />$content",
                HtmlCompat.FROM_HTML_MODE_COMPACT,
                null,
                TagHandler
            ).toComposable(
                linkStyles,
                linkInteractionListener
            )
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HtmlRichText(
    content: String,
    modifier: Modifier = Modifier,
    linkStyles: TextLinkStyles? = defaultLinkStyle,
    linkInteractionListener: LinkInteractionListener? = null,
    onZoomImage: (String?) -> Unit
) {
    val state by rememberRichTextState(content, linkStyles, linkInteractionListener)
    HtmlRichText(state = state, modifier = modifier, onZoomImage = onZoomImage)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HtmlRichText(
    state: RichTextState,
    modifier: Modifier = Modifier,
    onZoomImage: (String?) -> Unit
) {
    SelectionContainer {
        FlowRow(modifier = modifier) {
            val bottomAlignModifier = Modifier.align(Alignment.Bottom)
            state.elements.fastForEach { element ->
                when (element) {
                    is ImageElement -> {
                        element(
                            bottomAlignModifier.onTap {
                                onZoomImage(element.src)
                            }
                        )
                    }

                    else -> {
                        element(bottomAlignModifier)
                    }
                }
            }
        }
    }
}

private fun Spanned.toComposable(
    linkStyles: TextLinkStyles? = defaultLinkStyle,
    linkInteractionListener: LinkInteractionListener? = null
): List<ElementComposable> {
    val spanned = this@toComposable
    var start = 0
    val result = mutableListOf<ElementComposable>()
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val spanEnd = getSpanEnd(span)
        if (start < spanEnd) {
            result.add(
                TextElement(
                    (subSequence(start, spanEnd) as Spanned).toAnnotatedString(
                        linkStyles,
                        linkInteractionListener
                    )
                ).also {
                    start = spanEnd
                }
            )
        }
        when (span) {
            is NoDrawableImageSpan -> {
                result.add(ImageElement(span.src))
            }

            is NewLineSpan -> {
                result.add(NewLineElement)
            }

            is SteamIframeSpan -> {
                result.add(SteamIframeElement(span.src))
            }
        }
    }
    if (start < spanned.lastIndex) {
        result.add(
            TextElement(
                (subSequence(start, lastIndex) as Spanned).toAnnotatedString(
                    linkStyles,
                    linkInteractionListener
                )
            )
        )
    }
    return result
}