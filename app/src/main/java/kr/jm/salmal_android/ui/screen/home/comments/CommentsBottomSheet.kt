package kr.jm.salmal_android.ui.screen.home.comments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.firstOrNull
import kr.jm.salmal_android.ui.theme.Pretendard
import kr.jm.salmal_android.ui.theme.gray2
import kr.jm.salmal_android.ui.theme.gray4
import kr.jm.salmal_android.ui.theme.primaryGreen
import kr.jm.salmal_android.ui.theme.primaryWhite
import kr.jm.salmal_android.ui.theme.transparent
import kr.jm.salmal_android.ui.theme.white36
import kr.lifesemantics.salmal_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    voteId: String,
    commentsCount: Int,
    viewModel: CommentsViewModel = hiltViewModel(),
    showCommentsBottomSheet: () -> Unit
) {
    var myMemberId by remember { mutableIntStateOf(0) }
    val sheetState = rememberModalBottomSheetState(true)
    val commentsList by viewModel.commentsList.collectAsState()
    var commentId by remember {
        mutableIntStateOf(0)
    }
    var sunCommentIndex by remember {
        mutableIntStateOf(0)
    }
    var myImage by remember {
        mutableStateOf("")
    }
    val focusRequester = remember {
        FocusRequester()
    }
    val (comment, setComment) = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(voteId) {
        viewModel.getCommentsList(voteId)
    }

    LaunchedEffect(Unit) {
        viewModel.readMyImageUrl().firstOrNull()?.let {
            myImage = it
        }
    }
    LaunchedEffect(Unit) {
        viewModel.readMyMemberId().firstOrNull()?.let {
            myMemberId = it
        }
    }

    ModalBottomSheet(
        onDismissRequest = { showCommentsBottomSheet() },
        sheetState = sheetState,
        containerColor = gray4,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .navigationBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues())
        ) {
            Row(
            ) {
                Text(
                    text = "댓글",
                    fontFamily = Pretendard,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryWhite,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.Bottom)
                )
                Text(
                    text = "$commentsCount",
                    fontFamily = Pretendard,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = gray2,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.Bottom)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.cancel_icon),
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(end = 8.dp)
                        .clickable { showCommentsBottomSheet() },
                    contentDescription = "cancel_icon"
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                color = white36
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(items = commentsList, key = { _, item -> item.id }) { index, item ->
                    CommentsList(
                        item,
                        index,
                        myMemberId,
                        onShowReplyClick = {
                            viewModel.getSubCommentsList(item.id, index)
                        },
                        onAddSubComment = {
                            commentId = item.id
                            sunCommentIndex = index
                            focusRequester.requestFocus()
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = myImage),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = "member_image_url"
                )
                TextField(
                    value = comment,
                    onValueChange = setComment,
                    placeholder = { Text("눌러서 댓글 입력") },
                    textStyle = TextStyle(
                        color = primaryWhite,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .height(54.dp)
                        .focusRequester(focusRequester)
                        .border(
                            BorderStroke(1.dp, primaryGreen),
                            shape = CircleShape
                        ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = gray4,
                        unfocusedContainerColor = gray4,
                        disabledContainerColor = gray4,
                        focusedIndicatorColor = transparent,
                        unfocusedIndicatorColor = transparent,
                        cursorColor = primaryGreen,
                    )
                )
                Text(
                    text = "전송",
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium,
                    color = if (comment.isNotBlank()) primaryGreen else white36,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            if (comment.isNotBlank()) {
                                if (commentId != 0 || sunCommentIndex != 0) {
                                    viewModel.addSubComment(
                                        commentId,
                                        comment,
                                        sunCommentIndex,
                                        voteId
                                    )
                                } else {
                                    viewModel.addComment(voteId, comment)
                                }
                                keyboardController?.hide()
                                setComment("")
                            }
                        }
                )
            }
        }
    }
}