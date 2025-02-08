package com.example.cryptoradar.core.presentation.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.cryptoradar.crypto.presentation.coinList.CoinListEvent

// Composable bir fonksiyon tanımlanıyor ve jenerik (generic) bir tür olan <T> kullanılıyor.
// Bu, fonksiyonun herhangi bir türde olay akışını (Flow<T>) dinleyebileceğini gösterir.
@Composable
fun <T> ObserveAsEvents(
    // 'events': Olayları temsil eden bir Flow. Bu akıştaki her yeni olay 'onEvent' fonksiyonuna iletilir.
    events: Flow<T>,

    // 'key1' ve 'key2': LaunchedEffect’in tetiklenmesini kontrol eden opsiyonel anahtarlar.
    key1: Any? = null,
    key2: Any? = null,

    // 'onEvent': Yeni bir olay geldiğinde çağrılacak lambda fonksiyonudur.
    onEvent: (T) -> Unit
) {
    // Mevcut LifecycleOwner’ı alır. Bu, Composable’ın bağlı olduğu yaşam döngüsünü kontrol eder.
    val lifecycleOwner = LocalLifecycleOwner.current

    // LaunchedEffect, Compose'un recomposition sürecinde yan etkileri yönetmek için kullanılır.
    // Burada, 'lifecycleOwner.lifecycle' veya 'key1' veya 'key2' değiştiğinde bu efekt yeniden çalıştırılır.
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {

        // Composable yaşam döngüsünü takip ederek, yalnızca 'STARTED' durumunda olayları dinler.
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Olaylar UI iş parçacığında (Main Thread) işlenir.
            withContext(Dispatchers.Main.immediate) {
                // 'events' akışı (Flow<T>) dinlenir ve her yeni olay geldiğinde 'onEvent' fonksiyonu çağrılır.
                events.collect(onEvent)
            }
        }
    }
}