package kr.jm.salmal_android.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.data.response.BookMarkResponse
import kr.jm.salmal_android.data.response.MyEvaluations
import kr.jm.salmal_android.data.response.MyVotesResponse
import kr.jm.salmal_android.ui.theme.transparent

@Composable
fun <T> LazyGridView(
    items: List<T>,
    onClick: (String) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(top = 12.dp)) {
        itemsIndexed(items) { index, item ->
            when (item) {
                is MyVotesResponse.Vote -> {
                    VoteImageListView(item.imageUrl) {
                        onClick(item.id.toString())
                    }
                }

                is MyEvaluations.Vote -> {
                    VoteImageListView(item.imageUrl) {
                        onClick(item.id.toString())
                    }
                }

                is BookMarkResponse.Vote -> {
                    VoteImageListView(item.imageUrl) {
                        onClick(item.id.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun VoteImageListView(
    imageUrl: String,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .width(screenWidth / 2)
            .height(screenWidth / 2)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(transparent)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = "image_url"
        )
    }
}