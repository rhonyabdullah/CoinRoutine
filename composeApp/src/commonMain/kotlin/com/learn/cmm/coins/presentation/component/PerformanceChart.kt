package com.learn.cmm.coins.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.learn.cmm.theme.CoinRoutineTheme
import com.learn.cmm.theme.LocalCoinRoutineColorsPalette
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PerformanceChart(
    modifier: Modifier = Modifier,
    nodes: List<Double>,
    profitColor: Color,
    lossColor: Color,
) {
    if (nodes.isEmpty()) return

    val max = nodes.maxOrNull() ?: return
    val min = nodes.minOrNull() ?: return
    val lineColor = if (nodes.last() > nodes.first()) profitColor else lossColor

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val path = Path()
        nodes.forEachIndexed { index, value ->
            val x = index * (size.width / (nodes.size - 1))
            val y = size.height * (1 - ((value - min) / (max - min)).toFloat())

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}

@Preview
@Composable
fun PerformanceChartGreenPreview() {
    CoinRoutineTheme {
        PerformanceChart(
            nodes = listOf(10.0, 15.0, 12.0, 18.0, 20.0, 16.0, 22.0),
            profitColor = LocalCoinRoutineColorsPalette.current.profitGreen,
            lossColor = LocalCoinRoutineColorsPalette.current.lossRed,
        )
    }
}

@Preview
@Composable
fun PerformanceChartLossPreview() {
    CoinRoutineTheme {
        PerformanceChart(
            nodes = listOf(100.0, 15.0, 12.0, 18.0, 20.0, 16.0, 22.0),
            profitColor = LocalCoinRoutineColorsPalette.current.profitGreen,
            lossColor = LocalCoinRoutineColorsPalette.current.lossRed,
        )
    }
}
