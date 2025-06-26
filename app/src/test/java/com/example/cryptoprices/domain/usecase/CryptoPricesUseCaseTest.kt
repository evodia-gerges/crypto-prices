package com.example.cryptoprices.domain.usecase

import com.example.cryptoprices.data.repo.CryptoPricesRepo
import com.example.cryptoprices.domain.model.CryptoCurrency
import io.mockk.coEvery
import io.mockk.mockk
import java.math.BigDecimal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CryptoPricesUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repo: CryptoPricesRepo
    private lateinit var useCase: CryptoPricesUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = mockk()
        useCase = CryptoPricesUseCaseImpl(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCryptoCurrencies returns data from repo`() = runTest {
        val mockList = listOf(
            CryptoCurrency(
                symbol = "BTCUSDT",
                baseAsset = "BTC",
                quoteAsset = "USDT",
                openPrice = "26000",
                lowPrice = "25500",
                highPrice = "27000",
                lastPrice = "26500",
                usdPrice = BigDecimal("26500"),
                sekPrice = BigDecimal("265000"),
                volume = "1000",
                bidPrice = "26400",
                askPrice = "26600",
                at = 123456789
            )
        )

        coEvery { repo.getCryptoCurrencies() } returns mockList

        val result = useCase.getCryptoCurrencies()

        assertEquals(mockList, result)
    }

    @Test
    fun `getCryptoCurrencies returns empty list if repo returns empty`() = runTest {
        coEvery { repo.getCryptoCurrencies() } returns emptyList()

        val result = useCase.getCryptoCurrencies()

        assertEquals(emptyList(), result)
    }
}
