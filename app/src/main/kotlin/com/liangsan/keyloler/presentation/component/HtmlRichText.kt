package com.liangsan.keyloler.presentation.component

import android.text.Html
import android.text.Spanned
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.liangsan.keyloler.presentation.utils.TagHandler
import com.liangsan.keyloler.presentation.utils.onTap
import com.liangsan.keyloler.presentation.utils.toAnnotatedString

private val defaultLinkStyle = TextLinkStyles(
    SpanStyle(
        color = LightBlue,
        textDecoration = TextDecoration.Underline
    )
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HtmlRichText(
    content: String,
    modifier: Modifier = Modifier,
    linkStyles: TextLinkStyles? = defaultLinkStyle,
    linkInteractionListener: LinkInteractionListener? = null,
    onZoomImage: (String?) -> Unit
) {
    val htmlComposable = remember { mutableStateListOf<ElementComposable>() }

    LaunchedEffect(content, linkStyles) {
        if (htmlComposable.isNotEmpty()) {
            htmlComposable.clear()
        }
        htmlComposable.addAll(
            Html.fromHtml(
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
    SelectionContainer {
        FlowRow(modifier = modifier) {
            val bottomAlignModifier = Modifier.align(Alignment.Bottom)
            htmlComposable.fastForEach {
                when (it) {
                    is ImageElement -> {
                        it(
                            bottomAlignModifier.onTap {
                                onZoomImage(it.src)
                            }
                        )
                    }

                    else -> {
                        it(bottomAlignModifier)
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