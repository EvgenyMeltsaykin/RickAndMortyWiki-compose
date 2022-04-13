package wiki.rickandmorty.feature.characters.columnItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.ui.theme.montserratFamily

@Preview
@Composable
fun CharacterItem(
    character: CharacterDto = CharacterDto(
        id = 2,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        gender = "Male",
        imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (iPreview, bName) = createRefs()
        Image(
            painter = rememberAsyncImagePainter(character.imageUrl),
            contentDescription = "Preview character",
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
    Text(
        modifier = modifier,
        text = text,
        color = Color.Black,
        fontSize = 24.sp,
        fontFamily = montserratFamily,
        fontWeight = FontWeight.Normal,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}