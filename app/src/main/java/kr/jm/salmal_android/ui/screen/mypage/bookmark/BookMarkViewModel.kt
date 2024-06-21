package kr.jm.salmal_android.ui.screen.mypage.bookmark

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.response.BookMarkResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    override var repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _myBookMarks = MutableStateFlow<BookMarkResponse?>(null)
    val myBookMarks = _myBookMarks.asStateFlow()

    fun getMyBookMarks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val myMemberId = readMyMemberId().firstOrNull()
                _myBookMarks.emit(repository.getMyBookMarks(accessToken, myMemberId.toString()))
            } catch (e: HttpException) {
                Log.e("BookMarkViewModel", "getMyBookMarks: ${e.message}")
            }
        }
    }
}