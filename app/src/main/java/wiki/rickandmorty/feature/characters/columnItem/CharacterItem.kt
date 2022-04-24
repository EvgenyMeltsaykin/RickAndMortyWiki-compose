package wiki.rickandmorty.feature.characters.columnItem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.data.LifeStatus
import wiki.rickandmorty.ui_components.TextSchwiftyNormal

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: CharacterDto
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (iPreview, bName) = createRefs()
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Preview character",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(AbsoluteRoundedCornerShape(4.dp))
                .constrainAs(iPreview) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        NameText(
            modifier = Modifier
                .constrainAs(bName) {
                    width = Dimension.fillToConstraints
                    top.linkTo(iPreview.top)
                    bottom.linkTo(iPreview.bottom)
                    start.linkTo(iPreview.end, margin = 8.dp)
                    end.linkTo(parent.end)
                },
            text = character.name
        )
    }

}

@Composable
fun NameText(modifier: Modifier, text: String) {
    TextSchwiftyNormal(
        textAlign = TextAlign.Center,
        modifier = modifier,
        text = text,
        fontSize = 24.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}