package kr.jm.salmal_android.ui.screen.register

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.createBitmap
import androidx.core.view.drawToBitmap
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray4
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.transparent
import kr.jm.salmal_android.ui.theme.white80
import kr.jm.salmal_android.utils.FilterType
import kr.jm.salmal_android.utils.TextProperties
import kotlin.math.roundToInt

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
    var currentFilterIndex by remember {
        mutableIntStateOf(0)
    }
    val textProperties by remember {
        mutableStateOf(
            TextProperties(
                "",
                Color.White,
                Color.Transparent,
                Offset(0f, 0f)
            )
        )
    }
    var showTextInput by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray4),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            onClickCancel = onClickCancel,
            onClickOk = {

            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .weight(1f)
        ) {
            MainImage(uri = uri, currentFilterType = filterTypeList[currentFilterIndex])
            DraggableText(textProperties)
        }

        FilterOptions(
            uri = uri,
            filterTypeList = filterTypeList,
            currentFilterIndex = currentFilterIndex
        ) { index ->
            currentFilterIndex = index
        }

        AddTextButton {
            showTextInput = true
        }
        if (showTextInput) {
            TextInputDialog(textProperties, onDismiss = { showTextInput = false })
        }


    }
}

@Composable
fun TopBar(
    onClickCancel: () -> Unit,
    onClickOk: () -> Unit,
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
                    onClickOk()
                }
        )
    }
}

@Composable
fun MainImage(uri: String, currentFilterType: ColorMatrix) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
}

@Composable
fun FilterOptions(
    uri: String,
    filterTypeList: Array<ColorMatrix>,
    currentFilterIndex: Int,
    onFilterChange: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        itemsIndexed(filterTypeList) { index, filterType ->
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                modifier = Modifier
                    .size(90.dp)
                    .border(
                        width = if (currentFilterIndex == index) 3.dp else 0.dp,
                        color = if (currentFilterIndex == index) primaryGreen else transparent,
                        shape = RoundedCornerShape(18.dp)
                    ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.colorMatrix(filterType),
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onFilterChange(index)
                        },
                    contentDescription = "uriInfo"
                )
            }
        }
    }
}

@Composable
fun AddTextButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 30.dp)
            .clickable {
                onClick()
            },
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

@Composable
fun TextInputDialog(textProperties: TextProperties, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf(textProperties.text) }
    var textColor by remember { mutableStateOf(textProperties.color) }
    var backgroundColor by remember { mutableStateOf(textProperties.backgroundColor) }

    Dialog(onDismissRequest = onDismiss) {
        Row(
            modifier = Modifier
                .background(transparent)
                .fillMaxWidth()
                .height(70.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("텍스트 입력", fontFamily = Pretendard, color = primaryWhite) },
                textStyle = TextStyle(color = primaryWhite),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryGreen,
                    unfocusedBorderColor = primaryGreen,
                    disabledBorderColor = primaryGreen,
                    focusedLabelColor = primaryWhite,
                    unfocusedLabelColor = primaryWhite,
                    disabledLabelColor = primaryWhite,
                    cursorColor = primaryWhite
                ),
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxHeight()
            )
//            ColorPicker("텍스트 색상", textColor) { color ->
//                textColor = color
//            }
//            ColorPicker("배경 색상", backgroundColor) { color ->
//                backgroundColor = color
//            }
            Button(
                onClick = {
                    textProperties.text = text
                    textProperties.color = textColor
                    textProperties.backgroundColor = backgroundColor
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = transparent,
                    disabledContainerColor = transparent,
                ),
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "확인", fontSize = 14.sp, fontFamily = Pretendard, color = primaryGreen
                )
            }
        }
    }
}

@Composable
fun ColorPicker(label: String, initialColor: Color, onColorSelected: (Color) -> Unit) {
    // 간단한 색상 선택 구현 (
}

@Composable
fun DraggableText(textProperties: TextProperties) {
    var offset by remember { mutableStateOf(textProperties.position) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .draggable(
                state = rememberDraggableState { delta ->
                    offset = Offset(offset.x + delta, offset.y)
                    textProperties.position = offset
                },
                orientation = Orientation.Horizontal
            )
            .draggable(
                state = rememberDraggableState { delta ->
                    offset = Offset(offset.x, offset.y + delta)
                    textProperties.position = offset
                },
                orientation = Orientation.Vertical
            )
    ) {
        Text(
            text = textProperties.text,
            color = textProperties.color,
            modifier = Modifier.background(textProperties.backgroundColor)
        )
    }
}

fun captureComposableAsBitmap(context: Context, content: @Composable () -> Unit): Bitmap {
    val composeView = ComposeView(context).apply {
        setContent {
            content()
        }
    }
    composeView.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)
    return composeView.drawToBitmap()
}