package com.example.cryptoradar.crypto.presentation.coinList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoradar.crypto.presentation.models.DisplayableNumber
import com.example.cryptoradar.ui.theme.CryptoRadarTheme
import com.example.cryptoradar.ui.theme.greenBackground

//Bu fonksiyon, kripto paranın son 24 saatteki yüzdelik değişimini göstermek için bir bileşendir. Değer pozitifse yeşil, negatifse kırmızı renkte gösterilir.
@Composable
fun PriceChange(
    change: DisplayableNumber, // Değişim yüzdesini temsil eden veri (sayı ve formatlı hali)
    modifier: Modifier = Modifier // Varsayılan olarak boş olan, dışarıdan özelleştirilebilen stil ayarı
) {

    //Değişim yüzdesinin negatif veya pozitif olup olmadığına göre uygun renkler belirlenir.
    val contentColor = if (change.value < 0.0) {
        MaterialTheme.colorScheme.onErrorContainer // Negatif değişimlerde hata rengi (örneğin kırmızı)
    } else {
        Color.Green // Pozitif değişimlerde yeşil
    }
    val backgroundColor = if (change.value < 0.0) {
        MaterialTheme.colorScheme.errorContainer // Negatif değişimde koyu kırmızı arka plan
    } else {
        greenBackground // Pozitif değişimde yeşil arka plan (özel bir renk tanımlanmış)
    }

    //Değişim yüzdesini göstermek için yuvarlak köşeli ve renkli bir arka plana sahip bir satır oluşturulur.
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f)) // Tamamen yuvarlak köşeler (100f çok büyük bir değer olduğu için oval şekil oluşur)
            .background(backgroundColor) // Arka plan rengini yukarıda belirlenen değere göre ayarlar
            .padding(horizontal = 4.dp), // Yatayda 4 dp boşluk ekler
        verticalAlignment = Alignment.CenterVertically // Öğeleri dikeyde ortalar
    ) {

        //Fiyat değişimi pozitifse yukarı, negatifse aşağı ok simgesi gösterilir.
        Icon(
            imageVector = if (change.value < 0.0) {
                Icons.Default.KeyboardArrowDown // Negatif değişimse aşağı ok simgesi
            } else {
                Icons.Default.KeyboardArrowUp // Pozitif değişimse yukarı ok simgesi
            },
            contentDescription = null, // Erişilebilirlik için açıklama yok
            modifier = Modifier.size(20.dp), // Simgenin boyutu 20 dp olarak ayarlanır
            tint = contentColor // Simge rengi içeriğin renginde olur (kırmızı veya yeşil)
        )

        //Fiyat değişim yüzdesi, % işaretiyle birlikte gösterilir.
        Text(
            text = "${change.formatted} %", // Yüzdelik değişim formatlı şekilde yazılır (örneğin: "2.43 %")
            color = contentColor, // Yazı rengi, pozitifse yeşil, negatifse kırmızı olur
            fontSize = 14.sp, // Yazı boyutu 14 sp
            fontWeight = FontWeight.Medium // Orta kalınlıkta yazı fontu
        )
    }
}


//Bu fonksiyon, PriceChange bileşeninin önizlemesini oluşturur.
@PreviewLightDark
@Composable
private fun PriceChangePreview() {
    CryptoRadarTheme {
        PriceChange(
            change = DisplayableNumber(
                value = 2.43,
                formatted = "2.43"
            )
        )
    }
}