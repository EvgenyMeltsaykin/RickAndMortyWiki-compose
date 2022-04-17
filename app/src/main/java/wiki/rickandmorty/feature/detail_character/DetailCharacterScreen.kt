package wiki.rickandmorty.feature.detail_character

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import androidx.lifecycle.ViewModelStore
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import wiki.rickandmorty.R
import wiki.rickandmorty.core.base.BaseScreen
import wiki.rickandmorty.core.base.EventScreen
import wiki.rickandmorty.core.base.ViewStateScreen
import wiki.rickandmorty.ui_components.TextMontserratNormal
import java.util.*

@Parcelize
class DetailCharacterScreen(
    private val idCharacter: Int
) : BaseScreen<DetailCharacterScreen.ScreenEvent,
    DetailCharacterScreen.DetailCharacterViewState,
    DetailCharacterViewModel>() {

    object ScreenEvent : EventScreen
    data class DetailCharacterViewState(
        val name: String = "",
        val imageUrl: String = "",
        val gender: String = ""
    ) : ViewStateScreen

    @Composable
    override fun Content() {
        val viewModel: DetailCharacterViewModel =
            getViewModel(owner = { ViewModelStore() }) { parametersOf(idCharacter) }
        BindScreen(viewModel = viewModel)
        DetailCharacterContent(viewModel)
    }

    override fun bindEvents(event: ScreenEvent) {

    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMotionApi::class)
@Composable
fun DetailCharacterContent(viewModel: DetailCharacterViewModel) {
    val viewState by viewModel.state.collectAsState()
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene_detail_character)
            .readBytes()
            .decodeToString()
    }
    val componentHeight by remember { mutableStateOf(context.resources.displayMetrics.heightPixels.toFloat()) }
    val swipeableState = rememberSwipeableState("Bottom")
    val anchors = mapOf(0f to "Bottom", componentHeight to "Top")
    val mprogress = (swipeableState.offset.value / componentHeight)
    MotionLayout(

        motionScene = MotionScene(content = motionScene),
        progress = mprogress,
        debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),
        modifier = Modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                reverseDirection = true,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
    ) {
        HeaderContent(
            viewState = viewState,
            modifier = Modifier.layoutId("header")
        )
        SubcomposeAsyncImage(
            modifier = Modifier
                .clip(AbsoluteRoundedCornerShape(8.dp))
                .layoutId("image"),
            model = ImageRequest.Builder(LocalContext.current)
                .data(viewState.imageUrl)
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator()
            },
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        TextMontserratNormal(
            modifier = Modifier.fillMaxWidth(),
            text = "name",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        //CharacterInfoContent(modifier = Modifier.layoutId("character_name"))
    }

}

@Preview
@Composable
fun CharacterInfoContent(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextMontserratNormal(
            modifier = Modifier.fillMaxWidth(),
            text = "name",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )

        TextMontserratNormal(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.detail_character_gender, "Female"),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )
    }


}

@Composable
fun HeaderContent(
    viewState: DetailCharacterScreen.DetailCharacterViewState,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.padding(horizontal = 16.dp).height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(AbsoluteRoundedCornerShape(8.dp))
                .layoutId("image"),
            model = ImageRequest.Builder(LocalContext.current)
                .data(viewState.imageUrl)
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator()
            },
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        TextMontserratNormal(
            modifier = Modifier.wrapContentHeight(Alignment.CenterVertically).weight(1f),
            fontSize = 24.sp,
            text = viewState.name
        )
    }
    
}



/*

 */