package com.homework.weather.features.main.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.homework.weather.R
import com.homework.weather.ui.theme.COMPONENT_BACKGROUND

@Composable
fun SearchArea(
    onSearchAreaClick: () -> Unit,
) {
    Card(
        onClick = onSearchAreaClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("main_search_area"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = COMPONENT_BACKGROUND
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                tint = Color.White,
            )

            Text(
                text = stringResource(id = R.string.search_keyword_placeholder),
                color = Color.White,
            )
        }
    }
}