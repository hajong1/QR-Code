package hajong.qrcode.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import hajong.qrcode.presentation.common.Aim
import hajong.qrcode.util.QrCodeAnalyzer
import hajong.qrcode.util.QrCodeResult
import hajong.qrcode.util.parseQrResult
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MainScreen(
    onHistoryClick: () -> Unit,
    onScanResult: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var code by remember { mutableStateOf("") }
    val qrCodeAnalyzer = remember { QrCodeAnalyzer() }
    val interactionSource = remember { MutableInteractionSource() }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    LaunchedEffect(key1 = qrCodeAnalyzer) {
        qrCodeAnalyzer.scannedResult
            .distinctUntilChanged() // 동일한 값 방지
            .collect { result ->
                when (parseQrResult(result)) {
                    is QrCodeResult.DeepLink -> {
                        code = result
                    }
                    is QrCodeResult.PlainText -> {
                        code = result
                    }
                    is QrCodeResult.Url -> {
                        code = result
                    }
                }
            }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraProvider?.unbindAll()
            cameraProvider = null
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.weight(1f),
            ) {
                // QR Scanner camera preview
                if (hasCameraPermission) {
                    AndroidView(
                        factory = { context ->
                            val previewView = PreviewView(context)
                            val preview = Preview.Builder().build()
                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setTargetResolution(
                                    Size(
                                        previewView.width,
                                        previewView.height
                                    )
                                )
                                .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                qrCodeAnalyzer
                            )
                            try {
                                cameraProvider = cameraProviderFuture.get().also { provider ->
                                    provider.unbindAll()
                                    provider.bindToLifecycle(
                                        lifecycleOwner,
                                        selector,
                                        preview,
                                        imageAnalysis
                                    )
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                // Animating Box
                Aim(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(top = 4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (code.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 40.dp, end = 40.dp, bottom = 16.dp)
                                .border(1.dp, Color.White, RoundedCornerShape(40.dp))
                                .shadow(16.dp, RoundedCornerShape(40.dp))
                                .background(Color.White)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = {
                                        onScanResult(code)
                                        viewModel.addQrHistory(code)
                                    },
                                )
                        ) {
                            Text(
                                text = code,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(Color(0x80111111))
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterStart),
                            ) {
                                IconButton(
                                    modifier = Modifier
                                        .padding(20.dp),
                                    onClick = onHistoryClick
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.List,
                                        contentDescription = null,
                                        tint = Color.White,
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center)
                                    .shadow(16.dp, CircleShape)
                                    .background(Color.White, CircleShape)
                                    .clickable(
                                        enabled = code.isNotEmpty(),
                                        interactionSource = interactionSource,
                                        indication = null,
                                        onClick = {
                                            onScanResult(code)
                                            viewModel.addQrHistory(code)
                                        },
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = if(code.isNotEmpty()) Color.Black else Color.Transparent,
                                    modifier = Modifier
                                        .padding(20.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterEnd)
                            ) {
                                // 여백 or 추가 기능 들어갈 row
                            }
                        }
                    }
                }
            }
        }
    }
}
