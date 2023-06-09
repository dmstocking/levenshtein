package com.github.dmstocking.levenshtein

import kotlin.math.min

class Levenshtein(private var height: Int = 16, private var width: Int = 16) {

    private var distance = Array(height) { Array(width) { 0 } }

    init {
        for (i in 0 until width) {
            distance[i][0] = i
        }

        for (j in 0 until height) {
            distance[0][j] = j
        }
    }

    fun distance(a: String, b: String): Int {
        val rows = a.length
        val columns = b.length

        growToFit(rows, columns)

        for (j in 1..columns) {
            for (i in 1..rows) {
                val substitutionCost = if (a[i-1] == b[j-1]) 0 else 1
                distance[i][j] = min(
                    distance[i-1][j  ] + 1, // delete
                    distance[i  ][j-1] + 1, // insert
                    distance[i-1][j-1] + substitutionCost  // substitution
                )
            }
        }
        return distance[rows][columns]
    }

    private fun growToFit(rows: Int, columns: Int) {
        while (width < rows + 2 || height < columns + 2) {
            height *= 2
            width *= 2
        }

        distance = Array(height) { Array(width) { 0 } }

        for (i in 0 until width) {
            distance[i][0] = i
        }

        for (j in 0 until height) {
            distance[0][j] = j
        }
    }
}

fun min(vararg values: Int): Int {
    return values.reduce { current, next -> min(current, next) }
}