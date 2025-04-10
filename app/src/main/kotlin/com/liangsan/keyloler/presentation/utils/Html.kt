package com.liangsan.keyloler.presentation.utils

import android.graphics.Typeface
import android.text.Editable
import android.text.Html.TagHandler
import android.text.Layout
import android.text.Spanned
import android.text.Spanned.SPAN_MARK_MARK
import android.text.style.AbsoluteSizeSpan
import android.text.style.AlignmentSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.em
import org.xml.sax.Attributes
import org.xml.sax.ContentHandler
import org.xml.sax.XMLReader

/**
 * @see androidx.compose.ui.text.toAnnotatedString
 */
fun Spanned.toAnnotatedString(
    linkStyles: TextLinkStyles? = null,
    linkInteractionListener: LinkInteractionListener? = null
): AnnotatedString {
    return AnnotatedString.Builder(capacity = length)
        .append(this)
        .also {
            it.addSpans(
                this,
                linkStyles,
                linkInteractionListener
            )
        }
        .toAnnotatedString()
}

private fun AnnotatedString.Builder.addSpans(
    spanned: Spanned,
    linkStyles: TextLinkStyles?,
    linkInteractionListener: LinkInteractionListener?
) {
    spanned.getSpans(0, length, Any::class.java).forEach { span ->
        val range = TextRange(spanned.getSpanStart(span), spanned.getSpanEnd(span))
        addSpan(
            span,
            range.start,
            range.end,
            linkStyles,
            linkInteractionListener
        )
    }
}

private fun AnnotatedString.Builder.addSpan(
    span: Any,
    start: Int,
    end: Int,
    linkStyles: TextLinkStyles?,
    linkInteractionListener: LinkInteractionListener?
) {

    when (span) {
        is AbsoluteSizeSpan -> {
            // TODO(soboleva) need density object or make dip/px new units in TextUnit
        }

        is AlignmentSpan -> {
            addStyle(span.toParagraphStyle(), start, end)
        }

        is BackgroundColorSpan -> {
            addStyle(SpanStyle(background = Color(span.backgroundColor)), start, end)
        }

        is ForegroundColorSpan -> {
            addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
        }

        is RelativeSizeSpan -> {
            addStyle(SpanStyle(fontSize = span.sizeChange.em), start, end)
        }

        is StrikethroughSpan -> {
            addStyle(SpanStyle(textDecoration = TextDecoration.LineThrough), start, end)
        }

        is StyleSpan -> {
            span.toSpanStyle()?.let { addStyle(it, start, end) }
        }

        is SubscriptSpan -> {
            addStyle(SpanStyle(baselineShift = BaselineShift.Subscript), start, end)
        }

        is SuperscriptSpan -> {
            addStyle(SpanStyle(baselineShift = BaselineShift.Superscript), start, end)
        }

        is TypefaceSpan -> {
            addStyle(span.toSpanStyle(), start, end)
        }

        is UnderlineSpan -> {
            addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
        }

        is URLSpan -> {
            span.url?.let { url ->
                val link = LinkAnnotation.Url(
                    url,
                    linkStyles,
                    linkInteractionListener
                )
                addLink(link, start, end)
            }
        }
    }
}

private fun AlignmentSpan.toParagraphStyle(): ParagraphStyle {
    val alignment = when (this.alignment) {
        Layout.Alignment.ALIGN_NORMAL -> TextAlign.Start
        Layout.Alignment.ALIGN_CENTER -> TextAlign.Center
        Layout.Alignment.ALIGN_OPPOSITE -> TextAlign.End
        else -> TextAlign.Unspecified
    }
    return ParagraphStyle(textAlign = alignment)
}

private fun StyleSpan.toSpanStyle(): SpanStyle? {
    /** StyleSpan doc: styles are cumulative -- if both bold and italic are set in
     * separate spans, or if the base style is bold and a span calls for italic,
     * you get bold italic.  You can't turn off a style from the base style.
     */
    return when (style) {
        Typeface.BOLD -> {
            SpanStyle(fontWeight = FontWeight.Bold)
        }

        Typeface.ITALIC -> {
            SpanStyle(fontStyle = FontStyle.Italic)
        }

        Typeface.BOLD_ITALIC -> {
            SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        }

        else -> null
    }
}

private fun TypefaceSpan.toSpanStyle(): SpanStyle {
    val fontFamily = when (family) {
        FontFamily.Cursive.name -> FontFamily.Cursive
        FontFamily.Monospace.name -> FontFamily.Monospace
        FontFamily.SansSerif.name -> FontFamily.SansSerif
        FontFamily.Serif.name -> FontFamily.Serif
        else -> {
            optionalFontFamilyFromName(family)
        }
    }
    return SpanStyle(fontFamily = fontFamily)
}

private fun optionalFontFamilyFromName(familyName: String?): FontFamily? {
    if (familyName.isNullOrEmpty()) return null
    val typeface = Typeface.create(familyName, Typeface.NORMAL)
    return typeface.takeIf {
        typeface != Typeface.DEFAULT &&
                typeface != Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    }?.let { FontFamily(it) }
}

val TagHandler = object : TagHandler {
    override fun handleTag(
        opening: Boolean,
        tag: String?,
        output: Editable?,
        xmlReader: XMLReader?
    ) {
        if (xmlReader == null || output == null) return
        if (opening && tag == ContentHandlerReplacementTag) {
            val currentContentHandler = xmlReader.contentHandler
            xmlReader.contentHandler = AnnotationContentHandler(currentContentHandler, output)
        }
    }
}

private class AnnotationContentHandler(
    private val contentHandler: ContentHandler,
    private val output: Editable
) : ContentHandler by contentHandler {

    override fun startElement(uri: String?, localName: String?, qName: String?, atts: Attributes) {
        when (localName) {
            "div" -> {
                // By default, div tag will add a new line.
                // We are ignoring div tag here.
            }

            "script" -> {
                val start = output.length
                // Set the start of script tag.
                output.setSpan(ScriptSpan(), start, start, SPAN_MARK_MARK)
            }

            "img" -> {
                val src: String = atts.getValue("", "src")

                // Add a image span with no drawable and don't append a placeholder char.
                output.setSpan(
                    NoDrawableImageSpan(src), output.length, output.length,
                    SPAN_MARK_MARK
                )
            }

            "iframe" -> {
                val src: String = atts.getValue("", "src")

                output.setSpan(
                    SteamIframeSpan(src), output.length, output.length,
                    SPAN_MARK_MARK
                )
            }

            else -> contentHandler.startElement(uri, localName, qName, atts)
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        when (localName) {
            "div" -> {
                // Ignore ending div tag.
            }

            "script" -> {
                output.getSpans(0, output.length, ScriptSpan::class.java)
                    .filter { output.getSpanFlags(it) == SPAN_MARK_MARK }
                    .forEach { annotation ->
                        val start = output.getSpanStart(annotation)
                        val end = output.length
                        output.removeSpan(annotation)
                        if (start != end) {
                            // Delete the content inside of script tag
                            output.delete(start, end)
                        }
                    }
            }

            "br" -> {
                // Add a NewLineSpan mark instead of append '\n' directly
                // to custom new line behavior in composable
                output.setSpan(NewLineSpan(), output.length, output.length, SPAN_MARK_MARK)
            }

            else -> contentHandler.endElement(uri, localName, qName)
        }
    }
}

private class ScriptSpan

class NoDrawableImageSpan(val src: String)

class SteamIframeSpan(val src: String)

class NewLineSpan

/**
 * @see androidx.compose.ui.text.ContentHandlerReplacementTag
 */
const val ContentHandlerReplacementTag = "ContentHandlerReplacementTag"