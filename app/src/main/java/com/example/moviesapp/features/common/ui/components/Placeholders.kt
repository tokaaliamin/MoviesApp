package com.example.moviesapp.features.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R
import com.example.moviesapp.features.common.ui.models.StringWrapper


@Composable
fun ErrorPlaceholder(
    errorMessageWrapper: StringWrapper,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(width = 300.dp, height = 200.dp),
            painter = painterResource(R.drawable.placeholder_error),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        val error = if (errorMessageWrapper.stringInt != null) {
            context.getString(errorMessageWrapper.stringInt)
        } else
            errorMessageWrapper.message

        Text(
            text = error ?: "",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ElevatedButton(onClick = onAction) {
            Text(text = stringResource(R.string.reload))
        }
    }
}


@Composable
fun NoDataPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.placeholder_empty_list),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.no_movies_has_been_found),
            style = MaterialTheme.typography.titleMedium
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ErrorPlaceholderPreview() {
    ErrorPlaceholder(StringWrapper(R.string.failed_to_parse_error_response), onAction = {})
}

@Preview(showBackground = true)
@Composable
fun NoDataPlaceholderPreview() {
    NoDataPlaceholder()
}