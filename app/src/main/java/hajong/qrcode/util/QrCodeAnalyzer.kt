package hajong.qrcode.util

import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import kotlin.system.measureTimeMillis

class QrCodeAnalyzer: ImageAnalysis.Analyzer {
    private val _scannedResult = MutableSharedFlow<String>()
    val scannedResult = _scannedResult.asSharedFlow()

    // 이미지 영역을 설정
    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )

    override fun analyze(image: ImageProxy) {
        try {
            if (image.format in supportedImageFormats) {
                val bytes = image.planes.first().buffer.toByteArray()

                val source = PlanarYUVLuminanceSource(
                    bytes,
                    image.width,
                    image.height,
                    0,
                    0,
                    image.width,
                    image.height,
                    false
                )
                val binaryBmp = BinaryBitmap(HybridBinarizer(source))
                try {
                    val result = MultiFormatReader().apply {
                        setHints(
                            mapOf(
                                DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                    BarcodeFormat.QR_CODE
                                )
                            )
                        )
                    }.decode(binaryBmp)
                    // QrCodeResult 를 여기에 붙일지 고민
                    CoroutineScope(Dispatchers.IO).launch {
                        _scannedResult.emit(result.text)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } finally {
            image.close()
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}