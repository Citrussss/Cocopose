package top.arakawa.cocopose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.arakawa.cocopose.widget.coordinator.CoordinatorLayout

/**
 * Created by JBY on 2023/2/8.
 */
@Composable
fun Coordinator1Screen(modifier: Modifier) {
    CoordinatorLayout(modifier = modifier, calculateMaxOffset = { 128.dp },
        appbar = {
            Box(
                Modifier
                    .offset(y = this.offset)
                    .fillMaxWidth()
                    .height(128.dp)
                    .background(Color.Blue)
            ) {

            }
        },
        content = {
            LazyColumn(modifier = Modifier
                .offset(y = this.offset)
                .fillMaxSize()) {
                items(100) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    ) {
                        Text(text = "$it")
                    }
                }
            }
        })
}