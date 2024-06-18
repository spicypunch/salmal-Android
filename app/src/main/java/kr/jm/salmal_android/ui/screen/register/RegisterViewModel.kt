package kr.jm.salmal_android.ui.screen.register

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.BaseViewModel
import kr.jm.salmal_android.repository.RepositoryImpl
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    override var dataStore: DataStore<Preferences>
) : BaseViewModel() {

    fun registerVote(imageFile: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accessToken = "Bearer ${readAccessToken().firstOrNull()}"
                val response = repository.registerVote(accessToken, imageFile)
                if (response.isSuccessful) {
                    if (response.code() == 201) {
                        Log.i("성공", "마루")
                    }
                }
            } catch (e: HttpException) {
                Log.e("RegisterViewModel", "registerVote: ${e.message}")
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "registerVote: ${e.message}")
            }
        }
    }
}