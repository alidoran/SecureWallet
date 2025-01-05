package ir.dorantech.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ir.dorantech.ui.model.FirstScreenButtons

@Composable
fun MainScreen(
    onButtonClick: (FirstScreenButtons) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onButtonClick(FirstScreenButtons.Retrofit) }){
            Text("Retrofit")
        }
    }
}