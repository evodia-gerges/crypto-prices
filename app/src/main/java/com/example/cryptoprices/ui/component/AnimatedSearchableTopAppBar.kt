package com.example.cryptoprices.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.cryptoprices.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedSearchableTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    initialQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    hasSettings: Boolean = false,
    onSettingsClicked: () -> Unit
) {
    var isSearching by remember { mutableStateOf(initialQuery.isNotEmpty()) }
    var searchQuery by remember { mutableStateOf(TextFieldValue(initialQuery)) }

    TopAppBar(
        title = {
            AnimatedContent(
                targetState = isSearching,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { searching ->
                if (searching) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp, top = 8.dp, bottom = 8.dp)
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                onSearchQueryChanged(it.text)
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.search),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyMedium,
                        )

                        if (searchQuery.text.isNotEmpty()) {
                            IconButton(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = {
                                    searchQuery = TextFieldValue("")
                                    onSearchQueryChanged("")
                                }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    }
                } else {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        navigationIcon = {
            if (isSearching) {
                IconButton(onClick = {
                    isSearching = false
                    searchQuery = TextFieldValue("")
                    onSearchQueryChanged("")
                }) {
                    Icon(
                        Icons.Default.ChevronLeft,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        actions = {
            AnimatedContent(
                targetState = isSearching,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { searching ->
                if (!searching) {
                    Row {
                        if (hasSettings) {
                            IconButton(onClick = { onSettingsClicked() }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = "Settings"
                                )
                            }
                        }
                        IconButton(onClick = { isSearching = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier
    )
}
