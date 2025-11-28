package com.example.probcalc.presentation.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Percent: ImageVector
    get() {
        if (_Percent != null) return _Percent!!

        _Percent = ImageVector.Builder(
            name = "Percent",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(300f, 440f)
                quadToRelative(-58f, 0f, -99f, -41f)
                reflectiveQuadToRelative(-41f, -99f)
                reflectiveQuadToRelative(41f, -99f)
                reflectiveQuadToRelative(99f, -41f)
                reflectiveQuadToRelative(99f, 41f)
                reflectiveQuadToRelative(41f, 99f)
                reflectiveQuadToRelative(-41f, 99f)
                reflectiveQuadToRelative(-99f, 41f)
                moveToRelative(0f, -80f)
                quadToRelative(25f, 0f, 42.5f, -17.5f)
                reflectiveQuadTo(360f, 300f)
                reflectiveQuadToRelative(-17.5f, -42.5f)
                reflectiveQuadTo(300f, 240f)
                reflectiveQuadToRelative(-42.5f, 17.5f)
                reflectiveQuadTo(240f, 300f)
                reflectiveQuadToRelative(17.5f, 42.5f)
                reflectiveQuadTo(300f, 360f)
                moveToRelative(360f, 440f)
                quadToRelative(-58f, 0f, -99f, -41f)
                reflectiveQuadToRelative(-41f, -99f)
                reflectiveQuadToRelative(41f, -99f)
                reflectiveQuadToRelative(99f, -41f)
                reflectiveQuadToRelative(99f, 41f)
                reflectiveQuadToRelative(41f, 99f)
                reflectiveQuadToRelative(-41f, 99f)
                reflectiveQuadToRelative(-99f, 41f)
                moveToRelative(0f, -80f)
                quadToRelative(25f, 0f, 42.5f, -17.5f)
                reflectiveQuadTo(720f, 660f)
                reflectiveQuadToRelative(-17.5f, -42.5f)
                reflectiveQuadTo(660f, 600f)
                reflectiveQuadToRelative(-42.5f, 17.5f)
                reflectiveQuadTo(600f, 660f)
                reflectiveQuadToRelative(17.5f, 42.5f)
                reflectiveQuadTo(660f, 720f)
                moveToRelative(-444f, 80f)
                lineToRelative(-56f, -56f)
                lineToRelative(584f, -584f)
                lineToRelative(56f, 56f)
                close()
            }
        }.build()

        return _Percent!!
    }

private var _Percent: ImageVector? = null

val Contrast_circle: ImageVector
    get() {
        if (_Contrast_circle != null) return _Contrast_circle!!

        _Contrast_circle = ImageVector.Builder(
            name = "Contrast_circle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(480f, 880f)
                quadToRelative(-83f, 0f, -156f, -31.5f)
                reflectiveQuadTo(197f, 763f)
                reflectiveQuadToRelative(-85.5f, -127f)
                reflectiveQuadTo(80f, 480f)
                reflectiveQuadToRelative(31.5f, -156f)
                reflectiveQuadTo(197f, 197f)
                reflectiveQuadToRelative(127f, -85.5f)
                reflectiveQuadTo(480f, 80f)
                reflectiveQuadToRelative(156f, 31.5f)
                reflectiveQuadTo(763f, 197f)
                reflectiveQuadToRelative(85.5f, 127f)
                reflectiveQuadTo(880f, 480f)
                reflectiveQuadToRelative(-31.5f, 156f)
                reflectiveQuadTo(763f, 763f)
                reflectiveQuadToRelative(-127f, 85.5f)
                reflectiveQuadTo(480f, 880f)
                moveToRelative(0f, -80f)
                quadToRelative(134f, 0f, 227f, -93f)
                reflectiveQuadToRelative(93f, -227f)
                quadToRelative(0f, -64f, -24.5f, -122.5f)
                reflectiveQuadTo(706f, 254f)
                lineTo(254f, 706f)
                quadToRelative(45f, 45f, 103.5f, 69.5f)
                reflectiveQuadTo(480f, 800f)
                moveToRelative(0f, -160f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(60f)
                close()
                moveTo(320f, 460f)
                horizontalLineToRelative(60f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(-60f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(60f)
                horizontalLineToRelative(80f)
                close()
            }
        }.build()

        return _Contrast_circle!!
    }

private var _Contrast_circle: ImageVector? = null



