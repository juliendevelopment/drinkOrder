package com.drinkorder.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/**
 * Hand-made drink icons (24x24 viewport). They are drawn in black and are meant
 * to be tinted by the caller (e.g. Icon(tint = ...)), cut-outs use the even-odd fill rule.
 */
object DrinkIcons {

    val Beer: ImageVector by lazy {
        drinkIcon("Beer") {
            fill("M4,6 a2.5,2.5 0 1,0 5,0 a2.5,2.5 0 1,0 -5,0 Z M7,4.8 a3,3 0 1,0 6,0 a3,3 0 1,0 -6,0 Z M11,6 a2.5,2.5 0 1,0 5,0 a2.5,2.5 0 1,0 -5,0 Z M4,6 V8.2 H16 V6 Z")
            fill("M4.5,9 H15.5 V19.5 A1.5,1.5 0 0 1 14,21 H6 A1.5,1.5 0 0 1 4.5,19.5 Z M7.5,11 H8.7 V19 H7.5 Z M11.3,11 H12.5 V19 H11.3 Z", evenOdd = true)
            fill("M15.5,10.5 H18 A2,2 0 0 1 20,12.5 V16 A2,2 0 0 1 18,18 H15.5 V16.5 H18 A0.5,0.5 0 0 0 18.5,16 V12.5 A0.5,0.5 0 0 0 18,12 H15.5 Z")
        }
    }

    val BeerNonAlcoholic: ImageVector by lazy {
        drinkIcon("BeerNonAlcoholic") {
            fill("M4,6 a2.5,2.5 0 1,0 5,0 a2.5,2.5 0 1,0 -5,0 Z M7,4.8 a3,3 0 1,0 6,0 a3,3 0 1,0 -6,0 Z M11,6 a2.5,2.5 0 1,0 5,0 a2.5,2.5 0 1,0 -5,0 Z M4,6 V8.2 H16 V6 Z")
            fill("M4.5,9 H15.5 V19.5 A1.5,1.5 0 0 1 14,21 H6 A1.5,1.5 0 0 1 4.5,19.5 Z M7,15 a3,4 0 1,0 6,0 a3,4 0 1,0 -6,0 Z M8.5,15 a1.5,2.5 0 1,0 3,0 a1.5,2.5 0 1,0 -3,0 Z", evenOdd = true)
            fill("M15.5,10.5 H18 A2,2 0 0 1 20,12.5 V16 A2,2 0 0 1 18,18 H15.5 V16.5 H18 A0.5,0.5 0 0 0 18.5,16 V12.5 A0.5,0.5 0 0 0 18,12 H15.5 Z")
        }
    }

    val SpecialBeer: ImageVector by lazy {
        drinkIcon("SpecialBeer") {
            fill("M5,2.5 H19 V7.5 C19,12 16.2,14.8 13,15.2 V19 H16.5 A1,1 0 0 1 17.5,20 V21.5 H6.5 V20 A1,1 0 0 1 7.5,19 H11 V15.2 C7.8,14.8 5,12 5,7.5 Z M12.00,5.30 L12.82,7.47 L15.14,7.58 L13.33,9.03 L13.94,11.27 L12.00,10.00 L10.06,11.27 L10.67,9.03 L8.86,7.58 L11.18,7.47 Z", evenOdd = true)
        }
    }

    val Water: ImageVector by lazy {
        drinkIcon("Water") {
            fill("M12,2 C12,2 5,10 5,14.8 A7,7 0 0 0 19,14.8 C19,10 12,2 12,2 Z M8,14.5 A4,4 0 0 0 12,18.5 V17 A2.5,2.5 0 0 1 9.5,14.5 Z", evenOdd = true)
        }
    }

    val SparklingWater: ImageVector by lazy {
        drinkIcon("SparklingWater") {
            fill("M5,6 H19 L17.4,21 H6.6 Z M8.6,17.5 a1.4,1.4 0 1,0 2.8,0 a1.4,1.4 0 1,0 -2.8,0 Z M12.9,14.5 a1.1,1.1 0 1,0 2.2,0 a1.1,1.1 0 1,0 -2.2,0 Z M9.3,11.5 a1.2,1.2 0 1,0 2.4,0 a1.2,1.2 0 1,0 -2.4,0 Z M13.8,9.3 a0.8,0.8 0 1,0 1.6,0 a0.8,0.8 0 1,0 -1.6,0 Z M11.6,18.5 a0.6,0.6 0 1,0 1.2,0 a0.6,0.6 0 1,0 -1.2,0 Z", evenOdd = true)
            fill("M7.4,3.3 a1.1,1.1 0 1,0 2.2,0 a1.1,1.1 0 1,0 -2.2,0 Z M11.7,2.2 a0.8,0.8 0 1,0 1.6,0 a0.8,0.8 0 1,0 -1.6,0 Z M14.6,3.8 a0.9,0.9 0 1,0 1.8,0 a0.9,0.9 0 1,0 -1.8,0 Z")
        }
    }

    val Cola: ImageVector by lazy {
        drinkIcon("Cola") {
            fill("M10.3,1.5 H13.7 V3 H13.4 V5.5 C13.4,7.5 16.2,8.5 16.2,11 C16.2,12.8 15,13.4 15,15 C15,16.6 16.2,17.2 16.2,19 V21 A1.5,1.5 0 0 1 14.7,22.5 H9.3 A1.5,1.5 0 0 1 7.8,21 V19 C7.8,17.2 9,16.6 9,15 C9,13.4 7.8,12.8 7.8,11 C7.8,8.5 10.6,7.5 10.6,5.5 V3 H10.3 Z M8.4,11.2 H15.6 V13 H8.4 Z M9.8,4 H14.2 V4.6 H9.8 Z", evenOdd = true)
        }
    }

    val Coffee: ImageVector by lazy {
        drinkIcon("Coffee") {
            fill("M4.5,9 H16 V13.5 A5,5 0 0 1 11,18.5 H9.5 A5,5 0 0 1 4.5,13.5 Z")
            fill("M13.7,12 a2.6,2.6 0 1,0 5.2,0 a2.6,2.6 0 1,0 -5.2,0 Z M15.1,12 a1.2,1.2 0 1,0 2.4,0 a1.2,1.2 0 1,0 -2.4,0 Z", evenOdd = true)
            fill("M2.5,19.5 H18.5 C18.5,20.6 17.6,21.5 16.5,21.5 H4.5 C3.4,21.5 2.5,20.6 2.5,19.5 Z")
            stroke("M8.3,7.3 C7.3,6 9.3,5.2 8.3,3.5 M12.3,7.3 C11.3,6 13.3,5.2 12.3,3.5", width = 1.3f)
        }
    }

    val RedBull: ImageVector by lazy {
        drinkIcon("RedBull") {
            fill("M7,4 Q7,2.5 8.5,2.5 H15.5 Q17,2.5 17,4 V20 Q17,21.5 15.5,21.5 H8.5 Q7,21.5 7,20 Z M7,4.6 H17 V5.3 H7 Z M7,18.7 H17 V19.4 H7 Z M13.4,6.6 L9.2,12.9 H11.8 L10.4,17.6 L14.9,11 H12.2 L13.9,6.6 Z", evenOdd = true)
        }
    }

    val Juice: ImageVector by lazy {
        drinkIcon("Juice") {
            fill("M5.5,8 H17.5 L16.1,21.5 H6.9 Z M6.1,10.3 H16.9 L16.8,11 H6.2 Z", evenOdd = true)
            stroke("M12.5,8.5 L14.8,2.8 H18.5", width = 1.4f)
            fill("M3.2,7.5 a3.3,3.3 0 1,0 6.6,0 a3.3,3.3 0 1,0 -6.6,0 Z M4,7.5 a2.5,2.5 0 1,0 5,0 a2.5,2.5 0 1,0 -5,0 Z M6.5,7.5 L6.5,5.3 L8.4,6.4 Z M6.5,7.5 L8.4,8.6 L6.5,9.7 Z M6.5,7.5 L4.6,8.6 L4.6,6.4 Z", evenOdd = true)
        }
    }

    val Glass: ImageVector by lazy {
        drinkIcon("Glass") {
            fill("M5,3 H19 L17.2,21 H6.8 Z M5.5,5 H18.5 L18.1,9 H5.9 Z", evenOdd = true)
        }
    }

    private fun drinkIcon(name: String, block: ImageVector.Builder.() -> Unit): ImageVector =
        ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply(block).build()

    private fun ImageVector.Builder.fill(pathData: String, evenOdd: Boolean = false) {
        addPath(
            pathData = addPathNodes(pathData),
            pathFillType = if (evenOdd) PathFillType.EvenOdd else PathFillType.NonZero,
            fill = SolidColor(Color.Black)
        )
    }

    private fun ImageVector.Builder.stroke(pathData: String, width: Float) {
        addPath(
            pathData = addPathNodes(pathData),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = width,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        )
    }
}
