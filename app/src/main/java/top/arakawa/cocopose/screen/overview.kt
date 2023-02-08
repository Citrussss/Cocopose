package top.arakawa.cocopose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.arakawa.cocopose.MainActivity
import top.arakawa.cocopose.router.Overview
import top.arakawa.cocopose.ui.theme.CocoposeTheme

/**
 * Created by JBY on 2023/2/8.
 */
@Composable
fun OverviewScreen(
    modifier: Modifier,
    titles: List<String>,
    onNav: (position:Int) -> Unit) {
    CocoposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SampleList(
                modifier = Modifier,
                titles = titles,
                onSelect = onNav
            )
        }
    }
}

@Composable
fun SampleList(
    modifier: Modifier = Modifier,
    titles: List<String>,
    onSelect: (position: Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(titles) {
            Box(
                modifier = Modifier
                    .padding(15.dp, 15.dp)
                    .background(Color(0xFF4CAF50), RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            onSelect(titles.indexOf(it))
                        },
                    text = it
                )
            }
        }
    }
}