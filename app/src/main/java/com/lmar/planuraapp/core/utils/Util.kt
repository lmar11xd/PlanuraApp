package com.lmar.planuraapp.core.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.random.Random

fun generateUniqueCode(): String {
    val timestamp =
        System.currentTimeMillis().toString().takeLast(3) // Últimos 3 dígitos del tiempo
    val random = Random.nextInt(100, 999).toString() // Número aleatorio de 3 dígitos
    return timestamp + random
}

fun generateUniqueId(): String {
    return UUID.randomUUID().toString()
}

fun Long.toFormattedDate(): String {
    val locale = Locale.forLanguageTag("es-ES")

    val now = Calendar.getInstance()
    val date = Calendar.getInstance().apply { timeInMillis = this@toFormattedDate }

    val sdfTime = SimpleDateFormat("h:mm a", locale)
    val sdfDayTime = SimpleDateFormat("EEE h:mm a", locale)
    val sdfDayMonth = SimpleDateFormat("d 'de' MMMM", locale)
    val sdfFullDate = SimpleDateFormat("d 'de' MMMM 'de' yyyy", locale)

    return when {
        // Hoy
        now.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR) -> {
            sdfTime.format(Date(this))
        }
        // Ayer
        now.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) - date.get(Calendar.DAY_OF_YEAR) == 1 -> {
            "Ayer ${sdfTime.format(Date(this))}"
        }
        // Misma semana
        now.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                now.get(Calendar.WEEK_OF_YEAR) == date.get(Calendar.WEEK_OF_YEAR) -> {
            sdfDayTime.format(Date(this)).replaceFirstChar { it.uppercase() }
        }
        // Mismo año
        now.get(Calendar.YEAR) == date.get(Calendar.YEAR) -> {
            sdfDayMonth.format(Date(this))
        }
        // Otro año
        else -> {
            sdfFullDate.format(Date(this))
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val localeEs = Locale.forLanguageTag("es-ES")
    val zone = ZoneId.systemDefault()

    val ahora = ZonedDateTime.now(zone)
    val fecha = Instant.ofEpochMilli(timestamp).atZone(zone)

    val hoy = ahora.toLocalDate()
    val dia = fecha.toLocalDate()

    val weekFields = WeekFields.of(localeEs)
    val inicioSemana = hoy.with(weekFields.dayOfWeek(), 1) // lunes
    val mismaSemana = !dia.isBefore(inicioSemana) && !dia.isAfter(hoy)

    val horaAmPm = fecha.format(DateTimeFormatter.ofPattern("h:mm a", localeEs))
        .toUpperAmPm()

    return when {
        // Hoy
        dia == hoy -> horaAmPm

        // Ayer
        dia == hoy.minusDays(1) -> "Ayer $horaAmPm"

        // Misma semana (excluyendo hoy/ayer por casos anteriores)
        mismaSemana -> {
            val dow = dia.dayOfWeek.getDisplayName(TextStyle.SHORT, localeEs)
                .removeSuffix(".")
                .capitalizeFirst(localeEs)
            "$dow $horaAmPm"
        }

        // Mismo año
        dia.year == hoy.year -> {
            val mes = dia.month.getDisplayName(TextStyle.FULL, localeEs).capitalizeFirst(localeEs)
            "${dia.dayOfMonth} de $mes"
        }

        // Otro año
        else -> {
            val mes = dia.month.getDisplayName(TextStyle.FULL, localeEs).capitalizeFirst(localeEs)
            "${dia.dayOfMonth} de $mes de ${dia.year}"
        }
    }
}

// --- Helpers ---

private fun String.capitalizeFirst(locale: Locale): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }

// Normaliza "a. m." / "p. m." a "AM"/"PM"
private fun String.toUpperAmPm(): String =
    this.replace(Regex("\\b[aA]\\.?\\s?m\\.?\\b"), "AM")
        .replace(Regex("\\b[pP]\\.?\\s?m\\.?\\b"), "PM")
