package com.example.cryptoprices.domain.usecase

import com.example.cryptoprices.data.repo.SettingsRepo
import com.example.cryptoprices.domain.model.CurrenciesOrder
import com.example.cryptoprices.domain.model.SettingsModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repo: SettingsRepo
    private lateinit var useCase: SettingsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = mockk(relaxed = true) // relaxed allows us to skip coEvery on void methods
        useCase = SettingsUseCaseImpl(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSettings returns StateFlow from repo`() = runTest {
        val expectedFlow: StateFlow<SettingsModel> = MutableStateFlow(
            SettingsModel(isUsd = false, order = CurrenciesOrder.ASCENDING)
        )

        coEvery { repo.getSettings() } returns expectedFlow

        val result = useCase.getSettings()

        assertEquals(expectedFlow, result)
        assertEquals(false, result.value.isUsd)
        assertEquals(CurrenciesOrder.ASCENDING, result.value.order)
    }

    @Test
    fun `saveIsUsd calls repo with correct value`() = runTest {
        useCase.saveIsUsd(true)
        coVerify { repo.saveIsUsd(true) }

        useCase.saveIsUsd(false)
        coVerify { repo.saveIsUsd(false) }
    }

    @Test
    fun `saveOrder calls repo with correct value`() = runTest {
        useCase.saveOrder(CurrenciesOrder.DESCENDING)
        coVerify { repo.saveOrder(CurrenciesOrder.DESCENDING) }

        useCase.saveOrder(CurrenciesOrder.DEFAULT)
        coVerify { repo.saveOrder(CurrenciesOrder.DEFAULT) }
    }
}
