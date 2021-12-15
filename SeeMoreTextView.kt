import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
fun SeeMoreTextView(
    modifier: Modifier = Modifier,
    text: String,
    maxLengthVisible: Int = 200,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    actionSeeMore: (() -> Unit)? = null,
    isSeeMoreEnabled: Boolean = false,
) {
    val annotatedString =
        if (text.length < maxLengthVisible || isSeeMoreEnabled)
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = textColor,
                    )
                ) { append(text = text) }
            }
        else buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = textColor,
                )
            ) { append(text = text.substring(0, maxLengthVisible)) }
            pushStringAnnotation(tag = "SEE_MORE", annotation = "")
            withStyle(
                style = SpanStyle(
                    color = textColor.copy(alpha = 0.5f),
                    // textDecoration = TextDecoration.Underline
                )
            ) { append(stringResource(R.string.see_more)) }
            pop()
        }
    ClickableText(
        style = textStyle,
        text = annotatedString,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "SEE_MORE",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                actionSeeMore?.invoke()
            }
        }
    )
}
