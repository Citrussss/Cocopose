package top.arakawa.cocopose.widget.coordinator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Created by JBY on 2023/2/8.
 */
@Composable
fun CoordinatorLayout(
    modifier: Modifier = Modifier,
    isExitUntilCollapsed: Boolean = false,
    calculateMaxOffset: (statusBarHeight: Dp) -> Dp,
    appbar: @Composable CoordinatorLayoutScope.() -> Unit,
    content: @Composable CoordinatorLayoutScope.() -> Unit
) {
    val density = LocalDensity.current

    val statusBarHeight by getStatusBarHeight()

    val maxOffset by remember(statusBarHeight) {
        derivedStateOf {
            with(density) {
                calculateMaxOffset(statusBarHeight).toPx()
            }
        }
    }

    var _offset by remember {
        mutableStateOf(0f)
    }

    val _offsetAnimaiton by animateFloatAsState(targetValue = _offset)

    var isCollapsing by remember {
        mutableStateOf(false)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return scroll(available, isExitUntilCollapsed)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return scroll(available, false)
            }

            private fun scroll(available: Offset, isExitUntilCollapsed: Boolean): Offset {
                if (available.y < 0 || !isExitUntilCollapsed) {
                    val preOffset = _offset
                    val nextOffset =
                        (_offset + available.y).coerceIn(-maxOffset, 0f)

                    if (_offset != nextOffset) {
                        _offset = nextOffset
                    }
                    val nextIsCollapsing = (-_offset) > (maxOffset / 2)
                    if (nextIsCollapsing != isCollapsing) {
                        isCollapsing = nextIsCollapsing
                    }
                    if (preOffset == _offset) {
                        return Offset.Zero
                    } else {
                        return Offset(0f, available.y)
                    }
                } else {
                    return Offset.Zero
                }
            }
        }
    }
    SubcomposeLayout(
        modifier = modifier.nestedScroll(nestedScrollConnection),
        measurePolicy = { constraints ->
            var headerHeight = 0
            val scope =
                CoordinatorLayoutScopeImpl(isCollapsing, with(density) { _offsetAnimaiton.toDp() })
            val header = subcompose("header") { scope.appbar() }.map {
                val placeable = it.measure(constraints)
                headerHeight = Integer.max(headerHeight, placeable.height)
                placeable
            }
            val body = subcompose("body") { scope.content() }.map {
                val bodyConstraints = constraints.copy(
                    maxHeight = constraints.maxHeight - (headerHeight - maxOffset).roundToInt()
                )
                val placeable = it.measure(bodyConstraints)
                placeable
            }
            layout(constraints.maxWidth, constraints.maxHeight) {
                body.forEach {
                    it.place(0, headerHeight)
                }
                header.forEach {
                    it.place(0, 0)
                }
            }
        })
}

@Composable
fun getStatusBarHeight(): State<Dp> {
    val density = LocalDensity.current
    val _statusBarHeight =
        WindowInsets.Companion.statusBars.getTop(density).let { with(density) { it.toDp() } }
    val statusBarHeight = remember {
        mutableStateOf(0.dp)
    }
    LaunchedEffect(key1 = _statusBarHeight, block = {
        statusBarHeight.value = _statusBarHeight
    })
    return statusBarHeight
}

interface CoordinatorLayoutScope {
    val isCollapsing: Boolean
    val offset: Dp
}

private data class CoordinatorLayoutScopeImpl(
    override val isCollapsing: Boolean,
    override val offset: Dp
) : CoordinatorLayoutScope
