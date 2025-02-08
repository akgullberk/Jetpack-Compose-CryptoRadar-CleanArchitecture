package com.example.cryptoradar.crypto.presentation.coinList

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.cryptoradar.core.presentation.util.toString
import com.example.cryptoradar.crypto.presentation.coinList.components.CoinListItem
import com.example.cryptoradar.crypto.presentation.coinList.components.previewCoin
import com.example.cryptoradar.ui.theme.CryptoRadarTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext

//Ana bileşen, state parametresiyle birlikte gelir. state, listeyi ve yükleme durumunu (isLoading) içeren bir veri sınıfıdır.
@Composable
fun CoinListScreen(
    state: CoinListState, // Ekrandaki mevcut durumu temsil eden veri sınıfı
    modifier: Modifier = Modifier // Varsayılan olarak boş, dışarıdan özelleştirilebilen stil ayarı
) {

    //Eğer veri hala yükleniyorsa, ortada bir yükleme göstergesi görüntülenir.
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(), // Ekranı tamamen kaplayacak şekilde ayarlanır
            contentAlignment = Alignment.Center // İçeriği ortalar
        ) {
            CircularProgressIndicator() // Dairesel yükleme animasyonu
        }

        //Veri yüklendiğinde,LazyColumn bileşeni kullanılarak bir liste oluşturulur.
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(), // Tüm ekranı kaplar
            verticalArrangement = Arrangement.spacedBy(8.dp) // Liste elemanları arasında 8dp boşluk bırakır
        ) {
            items(state.coins) { coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = { /*TODO*/ }, // Öğeye tıklanınca yapılacak işlem
                    modifier = Modifier.fillMaxWidth() // Tam genişlik kaplamasını sağlar
                )
                HorizontalDivider() // Her öğe arasında bir çizgi ekler
            }
        }
    }
}

//Bu bileşen, CoinListScreen'in nasıl görüneceğini test etmek için kullanılır.
@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoRadarTheme {
        CoinListScreen(
            state = CoinListState(
                coins = (1..100).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}