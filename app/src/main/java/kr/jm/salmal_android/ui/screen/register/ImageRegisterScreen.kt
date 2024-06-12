package kr.jm.salmal_android.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray4
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.white80
import kr.jm.salmal_android.utils.FilterType

@Composable
fun ImageRegisterScreen(
    uri: String,
    onClickCancel: () -> Unit,
) {
    val filterTypeList = arrayOf(
        FilterType.getFilter(FilterType.DEFAULT),
        FilterType.getFilter(FilterType.SEPIA),
        FilterType.getFilter(FilterType.BRIGHTNESS),
        FilterType.getFilter(FilterType.CONTRAST),
        FilterType.getFilter(FilterType.NEGATIVE),
        FilterType.getFilter(FilterType.BLACK_WHITE)
    )
    var currentFilterType by remember {
        mutableStateOf(filterTypeList[0])
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray4),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(primaryBlack),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "취소",
                fontFamily = Pretendard,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = white80,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable {
                        onClickCancel()
                    }
            )
            Text(
                text = "확인",
                fontFamily = Pretendard,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = primaryGreen,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {

                    }
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .weight(1f),
            shape = RoundedCornerShape(18.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = uri),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(currentFilterType),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "uriInfo"
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(items = filterTypeList) { filterType ->
                Spacer(modifier = Modifier.width(16.dp))
                Card(
                    modifier = Modifier.size(90.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = uri),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.colorMatrix(filterType),
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                currentFilterType = filterType
                            },
                        contentDescription = "uriInfo"
                    )
                }
            }
        }
        Row(
            modifier = Modifier.padding(vertical = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                modifier = Modifier.size(32.dp),
                tint = primaryWhite,
                contentDescription = "add_icon"
            )
            Text(
                text = "텍스트 추가",
                fontFamily = Pretendard,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = primaryWhite,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}