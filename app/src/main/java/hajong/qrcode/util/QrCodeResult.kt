package hajong.qrcode.util

sealed interface QrCodeResult {
    data class Url(val url: String) : QrCodeResult
    data class DeepLink(val deepLink: String) : QrCodeResult
    data class PlainText(val text: String) : QrCodeResult
}

fun parseQrResult(rawResult: String): QrCodeResult {
    return when {
        rawResult.startsWith("http://") || rawResult.startsWith("https://") ->
            QrCodeResult.Url(rawResult)
        rawResult.contains("://") ->
            QrCodeResult.DeepLink(rawResult)
        else ->
            QrCodeResult.PlainText(rawResult)
    }
}