package com.example.cryptoprices.data.repo

import android.content.SharedPreferences
import com.example.cryptoprices.domain.model.CurrenciesOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SettingsRepoTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var repo: SettingsRepoImpl

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        sharedPreferences = mockk()
        editor = mockk(relaxed = true)
        every { sharedPreferences.edit() } returns editor

        repo = SettingsRepoImpl(sharedPreferences)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSettings should return current settings from SharedPreferences`() = runTest {
        every { sharedPreferences.getBoolean("isUsd", true) } returns false
        every {
            sharedPreferences.getString("order", CurrenciesOrder.DEFAULT.name)
        } returns CurrenciesOrder.DESCENDING.name

        val settings = repo.getSettings().first()

        assertEquals(false, settings.isUsd)
        assertEquals(CurrenciesOrder.DESCENDING, settings.order)
    }

    @Test
    fun `saveIsUsd should persist value and update flow`() = runTest {
        val newValue = false

        val slot = slot<Boolean>()
        every { editor.putBoolean("isUsd", capture(slot)) } returns editor
        every { sharedPreferences.getBoolean("isUsd", true) } returns newValue
        every {
            sharedPreferences.getString("order", CurrenciesOrder.DEFAULT.name)
        } returns CurrenciesOrder.DESCENDING.name

        repo.saveIsUsd(newValue)

        assertEquals(newValue, slot.captured)

        val settings = repo.getSettings().first()
        assertEquals(newValue, settings.isUsd)
    }

    @Test
    fun `saveOrder should persist value and update flow`() = runTest {
        val newOrder = CurrenciesOrder.ASCENDING

        val slot = slot<String>()
        every { editor.putString("order", capture(slot)) } returns editor
        every { sharedPreferences.getBoolean("isUsd", true) } returns false
        every {
            sharedPreferences.getString("order", CurrenciesOrder.DEFAULT.name)
        } returns newOrder.name

        repo.saveOrder(newOrder)

        assertEquals(newOrder.name, slot.captured)

        val settings = repo.getSettings().first()
        assertEquals(newOrder, settings.order)
    }
}

