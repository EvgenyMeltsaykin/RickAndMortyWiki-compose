package wiki.rickandmorty.f_detail_character.columnItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wiki.rickandmorty.cf_ui.TextMontserrat
import wiki.rickandmorty.data.EpisodeDto
import wiki.rickandmorty.f_detail_character.R

@Composable
fun EpisodeItem(
    modifier: Modifier = Modifier,
    textColor: Color,
    backgroundColor: Color,
    episode: EpisodeDto
) {

    Column(
        modifier = modifier.fillMaxWidth().background(color = backgroundColor).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(2f),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                TextMontserrat(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = textColor,
                    text = stringResource(id = R.string.detail_character_release_date),
                )
                TextMontserrat(
                    textAlign = TextAlign.Start,
                    color = textColor,
                    text = episode.airDate,
                )
            }

            TextMontserrat(
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                text = episode.shortEpisode
            )
        }
        TextMontserrat(
            color = textColor,
            fontSize = 16.sp,
            text = episode.name,
        )
    }

}