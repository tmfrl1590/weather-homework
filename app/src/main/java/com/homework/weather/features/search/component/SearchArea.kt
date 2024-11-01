package com.homework.weather.features.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.homework.weather.R
import com.homework.weather.ui.theme.COMPONENT_BACKGROUND

@Composable
fun SearchBarArea(
    keyword: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focusRequester(focusRequester)
            .testTag("searchBarArea"),
        value = keyword,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_keyword_placeholder),
                color = Color.White
            )
        },
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words,
            autoCorrectEnabled = true
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(keyword) }
        ),
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search",
                tint = Color.White,
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = COMPONENT_BACKGROUND,
            unfocusedContainerColor = COMPONENT_BACKGROUND,
            focusedTextColor = Color.White,
        )
    )
}