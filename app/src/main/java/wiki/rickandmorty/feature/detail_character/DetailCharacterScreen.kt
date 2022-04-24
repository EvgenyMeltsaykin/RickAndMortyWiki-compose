package wiki.rickandmorty.feature.detail_character

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import androidx.lifecycle.ViewModelStore
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.github.terrakok.modo.back
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import wiki.rickandmorty.R
import wiki.rickandmorty.core.base.BaseScreen
import wiki.rickandmorty.core.base.EventScreen
import wiki.rickandmorty.core.base.ViewStateScreen
import wiki.rickandmorty.data.CharacterDto
import wiki.rickandmorty.data.EpisodeDto
import wiki.rickandmorty.data.LifeStatus
import wiki.rickandmorty.feature.detail_character.columnItem.EpisodeItem
import wiki.rickandmorty.ui_components.GifImage
import wiki.rickandmorty.ui_components.IconTwoRowText
import wiki.rickandmorty.ui_components.TextMontserrat
import java.util.*

@Parcelize
class DetailCharacterScreen(
    private val character: CharacterDto
) : BaseScreen<DetailCharacterScreen.ScreenEvent,
    DetailCharacterScreen.DetailCharacterViewState,
    DetailCharacterViewModel>() {

    sealed class ScreenEvent : EventScreen {
        object OnNavigateBack : ScreenEvent()
    }

    data class DetailCharacterViewState(
        val name: String,
        val imageUrl: String,
        val gender: String,
        val lifeStatus: LifeStatus,
        val species: String,
        val originLocation: String,
        val lastKnownLocation: String,
        val firstSeenInEpisodeName: String = "",
        val episodes: List<EpisodeDto> = emptyList(),
        val isAutoPlay: Boolean = false,
        val isShowEpisodes: Boolean = false
    ) : ViewStateScreen

    @Composable
    override fun Content() {
        val viewModel: DetailCharacterViewModel =
            getViewModel(owner = { ViewModelStore() }) { parametersOf(character) }
        BindScreen(viewModel = viewModel)
        DetailCharacterContent(viewModel)
    }

    override fun bindEvents(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnNavigateBack -> router.back()
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMotionApi::class)
    @Composable
    fun DetailCharacterContent(viewModel: DetailCharacterViewModel) {
        val viewState by viewModel.state.collectAsState()
        val context = LocalContext.current
        val mainMotionScene = remember {
            context.resources
                .openRawResource(R.raw.motion_scene_detail_character)
                .readBytes()
                .decodeToString()
        }
        val componentHeight by remember { mutableStateOf(context.resources.displayMetrics.heightPixels.toFloat()) }
        val swipeableState = rememberSwipeableState("Bottom")
        val anchors = mapOf(0f to "Bottom", componentHeight to "Top")
        val mProgress = swipeableState.offset.value / componentHeight
        viewModel.setProgressMotion(mProgress)

        if (viewState.isAutoPlay) {
            swipeableState.performDrag(((mProgress + 0.15) * 100).toFloat())
        } else {
            swipeableState.performDrag(0f)
        }

        MotionLayout(
            motionScene = MotionScene(content = mainMotionScene),
            progress = mProgress,
            debug = EnumSet.of(MotionLayoutDebugFlags.NONE),
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    reverseDirection = true,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Vertical
                )
                .layoutId("main_scene")
        ) {
            SetupStatusBar(motionProperties("background").value.color("color"))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(motionProperties("background").value.color("color"))
                    .layoutId("background")
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

            TextMontserrat(
                modifier = Modifier
                    .defaultMinSize(minHeight = 80.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .layoutId("character_name"),
                color = motionProperties("character_name").value.color("color"),
                text = viewState.name,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
            Divider(
                modifier = Modifier
                    .clip(AbsoluteRoundedCornerShape(50))
                    .height(0.5.dp)
                    .layoutId("header_divider"),
                color = motionProperties("header_divider").value.color("color"),
            )
            CharacterShortInfoContent(
                viewState = viewState,
                modifier = Modifier
                    .wrapContentHeight()
                    .layoutId("character_info"),
                changedTextColor = motionProperties("character_info").value.color("color")
            )
            GifImage(
                gifResource = R.drawable.portal_animation,
                modifier = Modifier
                    .layoutId("portal")
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        viewModel.onPortalClick()
                    }
            )
            GifImage(
                gifResource = R.drawable.portal_animation,
                modifier = Modifier
                    .layoutId("portal_close")
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        viewModel.onPortalCloseClick()
                    }

            )
            CharacterFullInfoContent(
                viewState = viewState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .layoutId("character_full_info"),
                changedTextColor = motionProperties("character_info").value.color("color"),
                blurValue = motionProperties("character_full_info").value.float("blurValue").dp,
                episodeTextColor = motionProperties("character_full_info").value.color("episodeTextColor"),
                episodeBackgroundItemColor = motionProperties("character_full_info").value.color("episodeBackgroundColorBack")
            )
        }

    }

    @Composable
    fun CharacterShortInfoContent(
        viewState: DetailCharacterViewState,
        modifier: Modifier = Modifier,
        changedTextColor: Color
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                @DrawableRes val statusResourceId: Int = getLifeStatus(viewState.lifeStatus)
                IconTwoRowText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.detail_character_status),
                    subTitle = viewState.lifeStatus.status,
                    textAlign = TextAlign.Start,
                    iconResourceId = statusResourceId,
                    color = changedTextColor
                )
                IconTwoRowText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.detail_character_species),
                    subTitle = viewState.species,
                    textAlign = TextAlign.Center,
                    color = changedTextColor,
                )
                IconTwoRowText(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.detail_character_gender),
                    subTitle = viewState.gender,
                    textAlign = TextAlign.End,
                    color = changedTextColor
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun CharacterFullInfoContent(
    viewState: DetailCharacterScreen.DetailCharacterViewState,
    modifier: Modifier = Modifier,
    blurValue: Dp,
    changedTextColor: Color,
    episodeTextColor: Color,
    episodeBackgroundItemColor:Color
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconTwoRowText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .blur(blurValue, edgeTreatment = BlurredEdgeTreatment.Unbounded),
            title = stringResource(id = R.string.detail_character_first_seen_in_episode),
            subTitle = viewState.firstSeenInEpisodeName,
            color = changedTextColor
        )

        IconTwoRowText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .blur(blurValue, edgeTreatment = BlurredEdgeTreatment.Unbounded),
            title = stringResource(id = R.string.detail_character_origin_location),
            subTitle = viewState.originLocation,
            color = changedTextColor
        )

        IconTwoRowText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .blur(blurValue, edgeTreatment = BlurredEdgeTreatment.Unbounded),
            title = stringResource(id = R.string.detail_character_last_known_location),
            subTitle = viewState.lastKnownLocation,
            color = changedTextColor
        )
        val episodes by remember(viewState.episodes) {
            derivedStateOf { viewState.episodes }
        }

        TextMontserrat(
            modifier = Modifier
                .fillMaxWidth()
                .blur(blurValue, edgeTreatment = BlurredEdgeTreatment.Unbounded),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            text = stringResource(id = R.string.detail_character_episodes_title).uppercase(),
            color = changedTextColor
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            itemsIndexed(items = episodes) { index, episode ->
                AnimatedVisibility(
                    visible = viewState.isShowEpisodes,
                    enter = fadeIn(
                        animationSpec = tween(500)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(250)
                    )
                ) {
                    EpisodeItem(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(align = Alignment.CenterVertically)
                            .clip(AbsoluteRoundedCornerShape(8.dp))
                            .animateItemPlacement(
                                animationSpec = tween(1000)
                            ),
                        textColor = episodeTextColor,
                        backgroundColor = episodeBackgroundItemColor,
                        episode = episode
                    )
                }
            }
        }
    }
}

fun getLifeStatus(lifeStatus: LifeStatus): Int {
    return when (lifeStatus) {
        LifeStatus.DEAD -> R.drawable.ic_circle_red
        LifeStatus.ALIVE -> R.drawable.ic_circle_green
        LifeStatus.UNKNOWN -> R.drawable.ic_circle_gray
        LifeStatus.OTHER -> R.drawable.ic_circle_gray
    }
}

@Composable
fun HeaderContent(
    viewState: DetailCharacterScreen.DetailCharacterViewState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min),
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

        TextMontserrat(
            modifier = Modifier
                .wrapContentHeight(Alignment.CenterVertically)
                .weight(1f),
            fontSize = 24.sp,
            text = viewState.name
        )
    }

}