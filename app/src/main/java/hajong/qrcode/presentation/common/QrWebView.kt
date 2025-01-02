package hajong.qrcode.presentation.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.net.URISyntaxException

@Composable
fun QrWebView(
    code: String,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
//                        isLoading = false
                    }

//                    override fun shouldOverrideUrlLoading(
//                        view: WebView?,
//                        request: WebResourceRequest?
//                    ): Boolean {
//                        return shouldOverrideUrlLoading(view, request!!.url.toString())
//                    }
                }
            }
        },
        update = { webView ->
            webView.loadUrl(code)
        }
    )
}

fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
    url?.let {
        if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
            val uri = try {
                Uri.parse(url)
            } catch (e: Exception) {
                return false
            }
            return when (uri.scheme) {
                "intent" -> {
                    startSchemeIntent(it, view!!.context)
                }
                else -> {
                    return try {
                        // 다른 딥링크 스킴이면 실행

                        true
                    } catch (e: java.lang.Exception) {
                        false
                    }
                }
            }
        } else {
            return false
        }
    } ?: return false
}

/*Intent 스킴을 처리하는 함수*/
fun startSchemeIntent(url: String, context: Context): Boolean {
    val schemeIntent: Intent = try {
        Intent.parseUri(url, Intent.URI_INTENT_SCHEME) // Intent 스킴을 파싱
    } catch (e: URISyntaxException) {
        return false
    }
    try {
//        startActivity(schemeIntent) // 앱으로 이동
        return true
    } catch (e: ActivityNotFoundException) { // 앱이 설치 안 되어 있는 경우
        val packageName = schemeIntent.getPackage()

        if (!packageName.isNullOrBlank()) {
//            startActivity(
//                Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("market://details?id=$packageName") // 스토어로 이동
//                )
//            )
            return true
        }
    }
    return false
}