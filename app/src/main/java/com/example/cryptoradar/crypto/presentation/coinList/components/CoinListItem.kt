package com.example.cryptoradar.crypto.presentation.coinList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoradar.crypto.domain.Coin
import com.example.cryptoradar.crypto.presentation.models.CoinUi
import com.example.cryptoradar.crypto.presentation.models.toCoinUi
import com.example.cryptoradar.ui.theme.CryptoRadarTheme

//Bu fonksiyon, tek bir coin’in (kripto para) listede nasıl görüntüleneceğini belirleyen bir Jetpack Compose bileşenidir.
@Composable
fun CoinListItem(
    coinUi: CoinUi, // Kullanıcı arayüzünde gösterilecek coin bilgileri (UI modeli)
    onClick: () -> Unit, // Tıklanınca çalışacak fonksiyon
    modifier: Modifier = Modifier // Varsayılan olarak boş olan, dışarıdan özelleştirilebilen stil ayarı
) {

    //Burada, cihazın karanlık modda olup olmadığı kontrol ediliyor ve içerik rengi buna göre belirleniyor.
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White // Karanlık moddaysa yazılar beyaz olur
    } else {
        Color.Black // Açık moddaysa yazılar siyah olur
    }

    //Coin’in görseli, adı, sembolü, fiyatı ve değişim yüzdesini yatay bir satır halinde düzenler.
    Row(
        modifier = modifier
            .clickable(onClick = onClick) // Coin’e tıklanınca onClick fonksiyonu çalışır
            .padding(16.dp), // Kenarlardan 16 dp boşluk bırakılır
        verticalAlignment = Alignment.CenterVertically, // Öğeler dikeyde ortalanır
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Öğeler arasında 16 dp boşluk bırakılır
    ) {

        //Coin’in logosu veya ikonu burada gösterilir.
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes), // Coin’in simgesi
            contentDescription = coinUi.name, // Erişilebilirlik için coin adı
            tint = MaterialTheme.colorScheme.primary, // Temaya uygun birincil renk kullanılır
            modifier = Modifier.size(85.dp) // Simgenin boyutu 85 dp olarak ayarlanır
        )

        //Coin’in kısa adı (sembolü) ve tam adı gösterilir.
        Column(
            modifier = Modifier.weight(1f) // Diğer bileşenlere göre genişliği esnek şekilde ayarlanır
        ) {
            Text(
                text = coinUi.symbol, // Coin’in kısa sembolü (örn: BTC, ETH)
                fontSize = 20.sp, // Yazı boyutu 20 sp
                fontWeight = FontWeight.Bold, // Kalın yazı tipi
                color = contentColor // Temaya göre renk seçimi (beyaz/siyah)
            )
            Text(
                text = coinUi.name,  // Coin’in tam adı (örn: Bitcoin, Ethereum)
                fontSize = 14.sp,  // Yazı boyutu 14 sp
                fontWeight = FontWeight.Light,  // Daha ince bir yazı tipi
                color = contentColor  // Temaya göre renk seçimi
            )
        }

        //Coin’in fiyatı ve son 24 saatlik değişim yüzdesi burada gösterilir.
        Column(
            horizontalAlignment = Alignment.End  // Öğeleri sağa hizalar
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",  // Coin’in USD fiyatı (örn: $62828.15)
                fontSize = 16.sp,  // Yazı boyutu 16 sp
                fontWeight = FontWeight.SemiBold,  // Yarı kalın yazı tipi
                color = contentColor  // Temaya göre renk seçimi
            )
            Spacer(modifier = Modifier.height(8.dp))  // Fiyat ile değişim yüzdesi arasına 8 dp boşluk bırakır
            PriceChange(
                change = coinUi.changePercent24Hr  // Coin’in son 24 saatlik fiyat değişimi
            )
        }
    }
}

//Bu fonksiyon, CoinListItem bileşenini önizlemede farklı tema modlarında gösterir.
@PreviewLightDark
@Composable
private fun CoinListItemPreview() {
    CryptoRadarTheme { // Uygulamanın temasını kullanarak önizleme yapar
        CoinListItem(
            coinUi = previewCoin, // Önizleme için örnek bir coin kullanılır
            onClick = { /*TODO*/ }, // Tıklama işlemi şu an için boş bırakılmış
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background // Arka plan rengi temaya uygun ayarlanır
            )
        )
    }
}

//Önizleme için kullanılan örnek Bitcoin verisi:
internal val previewCoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 1241273958896.75,
    priceUsd = 62828.15,
    changePercent24Hr = -0.1
).toCoinUi()