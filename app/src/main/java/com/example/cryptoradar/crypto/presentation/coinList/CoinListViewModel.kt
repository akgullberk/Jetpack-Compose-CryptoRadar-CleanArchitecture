package com.example.cryptoradar.crypto.presentation.coinList

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoradar.core.domain.util.onError
import com.example.cryptoradar.core.domain.util.onSuccess
import com.example.cryptoradar.crypto.domain.CoinDataSource
import com.example.cryptoradar.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

// ViewModel sınıfı, Jetpack Compose veya diğer UI bileşenlerinde
// kullanılacak olan CoinList ekranının mantığını yönetir.
class CoinListViewModel(
    private val coinDataSource: CoinDataSource // Coin verilerini sağlayan veri kaynağı
): ViewModel() {

    // Ekranın durumunu saklamak için kullanılan MutableStateFlow.
    // Varsayılan olarak CoinListState() nesnesiyle başlar.
    private val _state = MutableStateFlow(CoinListState())

    // UI bileşenleri tarafından gözlemlenebilir bir state akışı.
    // `onStart { loadCoins() }` ile ViewModel başlatıldığında `loadCoins()` çağrılıyor.
    val state = _state
        .onStart { loadCoins() } // Akış başladığında coin verilerini yükler.
        .stateIn(
            viewModelScope, // ViewModel’in yaşam döngüsüne bağlı bir Coroutine scope
            SharingStarted.WhileSubscribed(5000L), // 5 saniye boyunca abonelik aktif olmazsa durdurulur.
            CoinListState() // Varsayılan başlangıç değeri
        )

    // '_events' adında bir özel (private) Channel oluşturuluyor.
    // Channel, bir tür asenkron veri kuyruğudur. Burada, 'CoinListEvent' tipinde veriler iletiliyor.
    // Bu kanal, olayların sırasıyla taşınmasına olanak tanır.
    private val _events = Channel<CoinListEvent>()

    // 'events' adında bir public Flow oluşturuluyor.
    // 'receiveAsFlow()' fonksiyonu, kanal üzerinden gelen verileri bir Flow'a dönüştürür.
    // Bu, 'events' akışını (Flow) dinleyen bileşenlerin, veri geldiğinde tepki vermesini sağlar.
    val events = _events.receiveAsFlow()

    // Kullanıcıdan gelen eylemleri işlemek için kullanılan fonksiyon
    fun onAction(action: CoinListAction) {
        when(action) {
            is CoinListAction.OnCoinClick -> { // Kullanıcı bir coine tıkladığında yapılacak işlemler buraya eklenebilir.
                _state.update { it.copy(
                    selectedCoin = action.coinUi
                ) }
            }
        }
    }

    // API veya veri kaynağından coin listesini yükleyen fonksiyon
    private fun loadCoins() {
        viewModelScope.launch { // ViewModel'in yaşam döngüsüne bağlı olarak coroutine başlatır.

            // Yükleme başladığında `isLoading` değerini true yaparak UI'yı bilgilendirir.
            _state.update { it.copy(
                isLoading = true
            ) }

            // Coin verilerini asenkron olarak getir.
            coinDataSource
                .getCoins()
                .onSuccess { coins -> // Eğer veri başarıyla gelirse
                    _state.update { it.copy(
                        isLoading = false, // Yükleme tamamlandı, isLoading = false
                        coins = coins.map { it.toCoinUi() } // Gelen coinleri UI modeline çevir
                    ) }
                }
                .onError { error -> // Eğer hata olursa
                    _state.update { it.copy(isLoading = false) } // Yüklemeyi durdur

                    // '_events' kanalına 'CoinListEvent.Error' türünde bir olay gönderiliyor.
                    // 'CoinListEvent.Error' sınıfı, bir hata mesajını (NetworkError) taşır.
                    // 'error' değişkeni, bu hatayı temsil eder ve bu veri kanal aracılığıyla iletilir.
                    // Kanal, olayları alacak ve bunları 'Flow' olarak iletecektir.
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}