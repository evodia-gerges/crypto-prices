package com.example.cryptoprices.data.repo

import com.example.cryptoprices.data.network.api.ApiExchangeRateService
import com.example.cryptoprices.data.network.dto.ExchangeRateDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeRateRepoTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var api: ApiExchangeRateService
    private lateinit var repo: ExchangeRateRepoImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        repo = ExchangeRateRepoImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getExchangeRates returns expected exchange rate map`() = runTest {
        val usdToSek = BigDecimal("9.43")
        val inrToUsd = BigDecimal("0.012")
        val inrToSek = BigDecimal("0.11")

        coEvery {
            api.getRates("usd", "sek")
        } returns ExchangeRateDTO(BigDecimal.ONE, "usd", "2024-01-01", mapOf("SEK" to usdToSek))

        coEvery {
            api.getRates("inr", "usd")
        } returns ExchangeRateDTO(BigDecimal.ONE, "inr", "2024-01-01", mapOf("USD" to inrToUsd))

        coEvery {
            api.getRates("inr", "sek")
        } returns ExchangeRateDTO(BigDecimal.ONE, "inr", "2024-01-01", mapOf("SEK" to inrToSek))

        val result = repo.getExchangeRates()

        assertEquals(usdToSek, result["usd_to_sek"])
        assertEquals(inrToUsd, result["inr_to_usd"])
        assertEquals(inrToSek, result["inr_to_sek"])
    }

    @Test
    fun `getExchangeRates returns zero for missing rate keys`() = runTest {
        coEvery {
            api.getRates("usd", "sek")
        } returns ExchangeRateDTO(BigDecimal.ONE, "usd", "2024-01-01", emptyMap())

        coEvery {
            api.getRates("inr", "usd")
        } returns ExchangeRateDTO(
            BigDecimal.ONE,
            "inr",
            "2024-01-01",
            mapOf("XYZ" to BigDecimal("1.1"))
        )

        coEvery {
            api.getRates("inr", "sek")
        } returns ExchangeRateDTO(
            BigDecimal.ONE,
            "inr",
            "2024-01-01",
            mapOf("SEK" to BigDecimal("0.15"))
        )

        val result = repo.getExchangeRates()

        assertEquals(BigDecimal.ZERO, result["usd_to_sek"])
        assertEquals(BigDecimal.ZERO, result["inr_to_usd"])
        assertEquals(BigDecimal("0.15"), result["inr_to_sek"])
    }

    @Test
    fun `getExchangeRates returns empty map when exception is thrown`() = runTest {
        coEvery { api.getRates(any(), any()) } throws RuntimeException("API failure")

        val result = repo.getExchangeRates()

        assertEquals(emptyMap(), result)
    }
}
