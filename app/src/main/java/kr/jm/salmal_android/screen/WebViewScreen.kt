package kr.jm.salmal_android.screen

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.orhanobut.logger.Logger
import kr.jm.salmal_android.ui.theme.primaryBlack
import kr.jm.salmal_android.ui.theme.primaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(url: String, backStack: () -> Unit) {
    val webView = rememberWebView(url)

    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "이용약관", color = primaryWhite) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryBlack),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = primaryWhite,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable { backStack() })
                }
            )
        },
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            factory = { webView }
        )
    }
}

@Composable
fun rememberWebView(url: String): WebView {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    Logger.i(request?.url.toString())
                    return true
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Logger.e(
                        "url: %s\nerror?.errorCode: %s\nerror?.description: %s",
                        view?.url ?: "",
                        error?.errorCode,
                        error?.description
                    )
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    super.onReceivedSslError(view, handler, error)
                    handler?.proceed()
                    Logger.e("handler: %s\nerror: %s", handler, error)
                    Logger.e(
                        "error?.url: %s\nerror?.certificate: %s",
                        error?.url,
                        error?.certificate.toString()
                    )
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Logger.e(
                        "url: %s\nerrorResponse?.statusCode: %d\nerrorResponse?.reasonPhrase: %s",
                        view?.url ?: "",
                        errorResponse?.statusCode,
                        errorResponse?.reasonPhrase
                    )
                }
            }
            loadUrl(url)
        }
    }
    return webView
}