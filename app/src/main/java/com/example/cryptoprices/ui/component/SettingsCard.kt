package com.example.cryptoprices.ui.component

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.dp

object SettingsCard {

    sealed interface Type {
        val label: String
    }

    data class Check(
        override val label: String,
        val checked: Boolean,
        val onChecked: (Boolean) -> Unit
    ) : Type

    data class Select(
        override val label: String,
        val checked: Boolean,
        val onClick: () -> Unit
    ) : Type


    @Composable
    operator fun invoke(
        settings: List<Type>,
        modifier: Modifier = Modifier,
    ) {
        if (settings.isEmpty()) return
        Column(
            modifier = modifier
                .clip(shape = MaterialTheme.shapes.large)
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            settings.forEachIndexed { index, setting ->
                when (setting) {
                    is Select -> RowLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = setting.onClick,
                                enabled = !setting.checked
                            ),
                        label = setting.label
                    ) {
                        if (setting.checked) Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .onPlaced { Log.d("Settings Card", "Icon Size: ${it.size}") },
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Checked",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    is Check -> RowLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { setting.onChecked(!setting.checked) }),
                        label = setting.label
                    ) {
                        TertiarySwitch(
                            checked = setting.checked,
                            onCheckedChange = setting.onChecked
                        )
                    }
                }
                if (index < settings.lastIndex)
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
            }
        }
    }

    @Composable
    fun RowLayout(
        label: String,
        modifier: Modifier = Modifier,
        endContent: @Composable () -> Unit,
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 16.dp)
                .padding(start = 24.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredHeightIn(min = 32.dp)
                    .weight(1F),
                contentAlignment = Alignment.CenterStart
            ) {
                AnimatedContent(label) { text ->
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = text,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            endContent()
        }
    }
}