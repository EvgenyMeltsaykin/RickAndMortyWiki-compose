package wiki.rickandmorty.cf_ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconTwoRowText(
    modifier: Modifier = Modifier,
    title: String, subTitle: String,
    textAlign: TextAlign = TextAlign.Start,
    @DrawableRes iconResourceId: Int? = null,
    color: Color
) {
    val columnWidthAlign = when (textAlign) {
        TextAlign.End -> Alignment.End
        TextAlign.Center -> Alignment.CenterHorizontally
        else -> Alignment.Start
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (iconResourceId != null) {
            Image(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = iconResourceId),
                contentDescription = null
            )
        }
        Column(
            modifier = modifier.wrapContentWidth(columnWidthAlign),
            horizontalAlignment = columnWidthAlign
        ) {
            TextMontserrat(
                text = title,
                fontSize = 18.sp,
                textAlign = textAlign,
                color = color,
                fontWeight = FontWeight.SemiBold
            )
            TextMontserrat(
                text = subTitle,
                fontSize = 18.sp,
                textAlign = textAlign,
                color = color
            )
        }
    }
}