package hajong.qrcode.presentation.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
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

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        Log.d("[지용]", "shouldOverrideUrlLoading")
                        return handleUrl(context, request!!.url.toString())
                    }
                }
            }
        },
        update = { webView ->
            webView.loadUrl(code)
        }
    )
}

private fun handleUrl(context: Context, url: String): Boolean {
    Log.d("[지용]", "shouldOverrideURLLoading : $url")
    if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
        val uri = try {
            Uri.parse(url)
        } catch (e: Exception) {
            return false
        }
        return when (uri.scheme) {
            "intent" -> {
                Log.d("[지용]", "uri.scheme : intent")
                startSchemeIntent(url, context)
            }
            else -> {
                Log.d("[지용]", "uri.scheme : else")
                return try {
                    // 다른 딥링크 스킴이면 실행
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                } catch (e: java.lang.Exception) {
                    false
                }
            }
        }
    } else {
        return false
    }
}

/*Intent 스킴을 처리하는 함수*/
private fun startSchemeIntent(url: String, context: Context): Boolean {
    val schemeIntent: Intent = try {
        Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
    } catch (e: URISyntaxException) {
        return false
    }
    try {
        context.startActivity(schemeIntent)
        return true
    } catch (e: ActivityNotFoundException) {
        val packageName = schemeIntent.getPackage()

        if (!packageName.isNullOrBlank()) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
            return true
        }
    }
    return false
}