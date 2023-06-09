package com.github.dmstocking.levenshtein

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.BufferedReader
import java.io.FileReader
import java.util.ArrayList
import java.util.stream.Collectors
import kotlin.random.Random
import kotlin.system.measureNanoTime

class LevenshteinTest {

    @Test
    fun distance() {
        Levenshtein().apply {
            assertEquals(3, distance("kitten", "sitting"))
            assertEquals(3, distance("Sunday", "Saturday"))
            assertEquals(8, distance("rosettacode", "raisethysword"))
        }
    }

    data class Result(val time: Long, val a: String, val b: String)

    @Test
    fun benchmark() {
        val words = FileReader("src/test/resources/words_alpha.txt")
            .let { BufferedReader(it) }
            .lines()
            .collect(Collectors.toList())
        val wordsCount = words.size
        Levenshtein(64, 64).apply {
            val random = Random(Random.nextInt())
            (0..1000).forEach {
                distance(words[random.nextInt(wordsCount)], words[random.nextInt(wordsCount)])
            }
            repeat(10) {
                val times = ArrayList<Result>(100_000)
                (0..100_000).forEach {
                    val a = words[random.nextInt(wordsCount)]
                    val b = words[random.nextInt(wordsCount)]
                    measureNanoTime {
                        distance(a, b)
                    }.let { times.add(Result(it, a, b)) }
                }

                println("max - ${times.maxBy { (t) -> t }}")
                println("min - ${times.minBy { (t) -> t }}")
                println("average - ${times.map { it.time }.sum().toDouble()/times.size}")
                println("median - ${times[times.size/2]}")
                println()
                println()
            }
        }
    }
}