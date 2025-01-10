package com.example.laba8.ui.theme                  //Определяет пакет, к которому принадлежит этот файл.

import androidx.compose.material3.Typography        //Этот класс используется для настройки типографических стилей в приложении.
import androidx.compose.ui.text.TextStyle           //используется для определения стиля текста, включая шрифт, размер, цвет и другие параметры.
import androidx.compose.ui.text.font.FontFamily     //Семейство шрифтов включает различные варианты одного шрифта (например, обычный, полужирный).
import androidx.compose.ui.text.font.FontWeight     //используется для задания толщины шрифта
import androidx.compose.ui.unit.sp                  //Импортирует единицу измерения sp (scale-independent pixels), используемую для задания размеров текста.

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    //Файл содержит определение объекта Typography, который настраивает стили текста для различных элементов интерфейса приложения.


/* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)