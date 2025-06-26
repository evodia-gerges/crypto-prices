package com.example.cryptoprices.data.repo

import com.example.cryptoprices.data.network.api.ApiCryptoService
import com.example.cryptoprices.data.network.dto.CryptoCurrencyDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CryptoPricesRepoTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var exchangeRateRepo: ExchangeRateRepo
    private lateinit var cryptoApi: ApiCryptoService
    private lateinit var repo: CryptoPricesRepoImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        exchangeRateRepo = mockk()
        cryptoApi = mockk()
        repo = CryptoPricesRepoImpl(exchangeRateRepo, cryptoApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCryptoCurrencies returns mapped crypto list when rates and API succeed`() = runTest {
        val rates = mapOf(
            "usd_to_sek" to BigDecimal("10"),
            "inr_to_usd" to BigDecimal("0.012"),
            "inr_to_sek" to BigDecimal("0.13")
        )

        val cryptoDTOs = listOf(
            CryptoCurrencyDTO(
                symbol = "BTCUSDT",
                baseAsset = "BTC",
                quoteAsset = "USDT",
                openPrice = "26000",
                lowPrice = "25500",
                highPrice = "27000",
                lastPrice = "26500",
                volume = "1000",
                bidPrice = "26400",
                askPrice = "26600",
                at = 123456789
            )
        )

        coEvery { exchangeRateRepo.getExchangeRates() } returns rates
        coEvery { cryptoApi.getCryptoCurrencies() } returns cryptoDTOs

        val result = repo.getCryptoCurrencies()

        // Assuming USDT behaves like USD → passthrough
        assertEquals(1, result.size)
        assertEquals("BTCUSDT", result[0].symbol)
        assertEquals(BigDecimal("26500"), result[0].usdPrice)
        assertEquals(BigDecimal("265000"), result[0].sekPrice)
    }

    @Test
    fun `getCryptoCurrencies returns empty list on exception`() = runTest {
        coEvery { exchangeRateRepo.getExchangeRates() } throws RuntimeException("Failed")
        val result = repo.getCryptoCurrencies()
        assertEquals(emptyList(), result)
    }

    @Test
    fun `getCryptoCurrencies caches result after first call`() = runTest {
        val rates = mapOf("usd_to_sek" to BigDecimal("10.0"))
        val dto = CryptoCurrencyDTO(
            symbol = "ETHUSDT",
            baseAsset = "ETH",
            quoteAsset = "USDT",
            openPrice = "1800",
            lowPrice = "1750",
            highPrice = "1900",
            lastPrice = "1850",
            volume = "500",
            bidPrice = "1845",
            askPrice = "1855",
            at = 123456789
        )
        coEvery { exchangeRateRepo.getExchangeRates() } returns rates
        coEvery { cryptoApi.getCryptoCurrencies() } returns listOf(dto)

        val result1 = repo.getCryptoCurrencies()
        val result2 = repo.getCryptoCurrencies()

        assertEquals(1, result1.size)
        assertEquals(result1, result2) // Cached value reused
    }
}
