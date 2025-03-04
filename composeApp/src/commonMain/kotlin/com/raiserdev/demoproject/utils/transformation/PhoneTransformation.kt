package com.raiserdev.demoproject.utils.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneMexTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return phoneMexFilter(text)
    }
}

const val MAX_NUMBER_LENGTH = 10

fun phoneMexFilter(text: AnnotatedString): TransformedText {
    // Tomamos máximo 10 dígitos, para formar +52 (XXX) XXX-XXXX
    val trimmed = text.text.take(MAX_NUMBER_LENGTH)

    // Construimos la cadena resultante
    val out = StringBuilder("+52 (")
    // Guardaremos los índices en los que termina cada dígito “real” dentro del texto transformado
    val offsets = mutableListOf<Int>()

    // La transformación inicia con "+52 (", que son 5 caracteres
    var currentPos = 5

    for (i in trimmed.indices) {
        // Al llegar al 4to dígito, cerramos paréntesis y agregamos espacio
        if (i == 3) {
            out.append(") ")
            currentPos += 2 // Se agregaron 2 caracteres: ')' y ' '
        }
        // Al llegar al 7mo dígito, agregamos el guion
        if (i == 6) {
            out.append("-")
            currentPos += 1 // Se agregó 1 caracter: '-'
        }

        // Agregamos el dígito actual y registramos su posición final
        out.append(trimmed[i])
        offsets.add(currentPos)
        currentPos++
    }

    val finalString = out.toString()

    // OffsetMapping dinámico para que el cursor “coincida” con los dígitos originales
    val phoneOffsetTranslator = object : OffsetMapping {
        // De la posición original del dígito a la posición transformada
        override fun originalToTransformed(offset: Int): Int {
            // Si el offset supera la cantidad de dígitos, vamos al final
            if (offset >= offsets.size) return finalString.length
            return offsets[offset]
        }

        // De la posición transformada a la original de dígitos
        override fun transformedToOriginal(offset: Int): Int {
            // Buscamos el primer índice en offsets que sea >= offset
            // para “regresar” al dígito que toque
            for ((i, pos) in offsets.withIndex()) {
                if (pos >= offset) return i
            }
            return offsets.size
        }
    }

    return TransformedText(AnnotatedString(finalString), phoneOffsetTranslator)
}