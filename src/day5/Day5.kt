package code.day4

import java.io.File
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Day5 {
    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 5 - Part A")

        var indexA = 0
        var indexB = 1

        var line = readInputFile()
        var done = false

        while (!done) {
            if (indexB < line.length) {
                val charA = line[indexA]
                val charB = line[indexB]

                val diff = Math.abs(charA - charB)

                if (diff == 32) { // characters are same but opposite case (using unicode value)
                    line = line.removeRange(indexA, indexB+1)
                    if (indexA > 0) {
                        indexA--
                        indexB--
                    }
                } else {
                    indexA++
                    indexB++
                }

                if (indexB >= line.length) {
                    done = true
                }
            }
        }

        println("\t* Number of units remaining: ${line.length}")
    }

    fun partB() {
        println("Day 5 - Part B")

        var units = arrayListOf<Char>('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
        var bestNumUnits = Int.MAX_VALUE
        var bestKill : Char = 'a'

        for (killUnit in units) {
            var indexA = 0
            var indexB = 1

            var line = readInputFile()
            var done = false

            while (!done) {
                val charA = line[indexA]
                val charB = line[indexB]

                if (charA == killUnit || charA == (killUnit-32)){ // test upper case by subtracting unicode value difference
                    line = line.removeRange(indexA, indexA + 1)
                } else if (charB == killUnit || charB == (killUnit-32)){
                    line = line.removeRange(indexB, indexB + 1)
                } else {
                    val diff = Math.abs(charA - charB)

                    if (diff == 32) { // characters are same but opposite case
                        line = line.removeRange(indexA, indexB + 1)
                        if (indexA > 0) {
                            indexA--
                            indexB--
                        }
                    } else {
                        indexA++
                        indexB++
                    }
                }
                if (indexB >= line.length) {
                    if (line.length < bestNumUnits) {
                        bestNumUnits = line.length
                        bestKill = killUnit
                    }
                    println("\t* ($killUnit) $line")
                    done = true
                }
            }
        }

        println("\t* Best Number of Units: $bestNumUnits by killing $bestKill")
    }


    private fun readInputFile(): String {
        val inputStream: InputStream = File("./src/day5/day5_input.txt").inputStream()

        var ret = ""
        inputStream.bufferedReader().useLines { lines -> lines.forEach {
            ret = it
        } }

        return ret
    }
}