package code.day2

import java.io.File
import java.io.InputStream

class Day2 {
    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 2 - Part A")
        val fileValuesList = readInputFile()

        var twoLetterDups = 0
        var threeLetterDups = 0

        fileValuesList.forEach{
            if (redundantLetterFinder(it, 2)) {
                twoLetterDups ++
            }

            if (redundantLetterFinder(it, 3)) {
                threeLetterDups ++
            }
        }

        println("\t* Checksum: " + (twoLetterDups * threeLetterDups))
    }

    fun partB() {
        println("Day 2 - Part B")
        val fileValuesList = readInputFile()

        for (i in 0 .. (fileValuesList.size -1)) {
            for (y in 0 .. (fileValuesList.size -1)) {
                if (y == i) { continue }

                printIfMatchingIds(fileValuesList[i], fileValuesList[y], 1)
            }
        }
    }

    private fun printIfMatchingIds(first: String, second: String, diffThreshold: Int) {
        var redundantLetters = ""

        for (i in 0 .. (second.length - 1)) {
            if (first[i] == second[i]) {
                redundantLetters += first[i]
            }
        }

        if (redundantLetters.length == (first.length-diffThreshold)) {
            println("\t* match: $redundantLetters")
        }
    }


    private fun redundantLetterFinder(boxid: String, duplicates: Int): Boolean {
        val length = boxid.length
        var map = mutableMapOf<Char, Int>()

        for (i in 0 .. (length-1)) {
            val letter = boxid[i]

            if (map.containsKey(letter)) {
                var count = map[letter] ?: 0
                map[letter] = ++count
            } else {
                map[letter] = 1
            }
        }

        try {
            map.values.first { it == duplicates }
            return true
        } catch (e: NoSuchElementException) {
            return false
        }
    }

    private fun readInputFile(): List<String> {
        val inputStream: InputStream = File("./src/day2/day2_input.txt").inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }

        return lineList
    }
}