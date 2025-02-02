package com.example.cryptoradar.core.domain.util

typealias DomainError = Error // Yeni bir tip ismi tanımlar, burada DomainError adı, Kotlin’in Error sınıfı için bir takma addır.

sealed interface Result<out D, out E : Error> { //Data,Error
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : DomainError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error) // Hata varsa, aynen geri döndür
        is Result.Success -> Result.Success(map(data)) // Başarı varsa, veriyi dönüştür ve yeni bir Result oluştur
    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this // Hata durumu değişmeden döndürülür
        is Result.Success -> {
            action(data) // Başarı durumunda verilen eylem (action) çağrılır
            this // Orijinal başarı durumu geri döndürülür
        }
    }
}

inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error) // Hata durumunda verilen eylem (action) çağrılır
            this // Orijinal hata durumu geri döndürülür
        }

        is Result.Success -> this // Başarı durumu değişmeden döndürülür
    }
}

typealias EmptyResult<E> = Result<Unit, E>