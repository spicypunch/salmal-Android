package kr.jm.salmal_android.ui.screen.home

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.data.response.VotesListResponse
import kr.jm.salmal_android.repository.RepositoryImpl
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    private val _votesList = MutableStateFlow<VotesListResponse?>(null)
    val votesList: StateFlow<VotesListResponse?> = _votesList.asStateFlow()

    fun getVotesList(
        cursorId: String = "",
        cursorLikes: String = "",
        size: Int,
        searchType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
            _votesList.emit(repository.votesList(accessToken, cursorId, cursorLikes, size, searchType))
        }
    }
}