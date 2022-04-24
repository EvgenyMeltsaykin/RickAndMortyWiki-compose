package wiki.rickandmorty.ui_components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import wiki.rickandmorty.ui.theme.getSchwiftyFamily
import wiki.rickandmorty.ui.theme.montserratFamily

@Composable
fun TextMontserrat(
    modifier: Modifier = Modifier,
    text: String,
    isVisible: Boolean = true,
    color:Color = Color.Black,
    fontSize: TextUnit = 14.sp,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    fontWeight: FontWeight = FontWeight.Normal
) {
    if (isVisible){
        Text(
            text = text,
            modifier = modifier,
            fontFamily = montserratFamily,
            fontWeight = fontWeight,
            color = color,
            fontSize = fontSize,
            overflow = overflow,
            maxLines = maxLines,
            textAlign = textAlign,
            onTextLayout = onTextLayout,
        )
    }
}

@Composable
fun TextSchwiftyNormal(
    text: String,
    modifier: Modifier = Modifier,
    color:Color = Color.Black,
    fontSize: TextUnit = 14.sp,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = getSchwiftyFamily,
        fontWeight = FontWeight.Normal,
        color = color,
        fontSize = fontSize,
        overflow = overflow,
        maxLines = maxLines,
        textAlign = textAlign
    )
}