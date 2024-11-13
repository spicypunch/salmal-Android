package kr.jm.salmal_android.ui.screen.register

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kr.jm.salmal_android.repository.RepositoryImpl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val repository: RepositoryImpl,
) : ViewModel() {

    fun registerVote(imageInfo: ByteArray) {
        viewModelScope.launch  {
            try {
                val accessToken = "Bearer ${repository.readAccessToken().firstOrNull()}"
                val requestFile = imageInfo.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageFile =
                    MultipartBody.Part.createFormData("imageFile", "image.jpeg", requestFile)

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