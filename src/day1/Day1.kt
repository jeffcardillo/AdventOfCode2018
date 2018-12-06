package code.day1

import java.io.File
import java.io.InputStream

class Day1 {
    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 1 - Part A")
        val fileValuesList = readInputFile()

        var frequency = 0

        fileValuesList.forEach{
            frequency += it
        }

        println("\t* Final Frequency: $frequency")
    }

    fun partB() {
        println("Day 1 - Part B")

        var fileValuesList = readInputFile()
        val frequencyList = mutableListOf<Int>()

        var found = false
        var fileIterations = 0
        var frequency = 0

        while(!found) {
            loop@ for (it in fileValuesList) {
                frequencyList.add(frequency)
                frequency += it

                if (frequencyList.contains(frequency)) {
                    println("\t* Duplicate Frequency: $frequency ($fileIterations file iterations to find)")
                    found = true

                    break@loop
                }
            }
            fileIterations++
        }
    }

    private fun readInputFile(): List<Int> {
        val inputStream: InputStream = File("./src/day1/day1_input.txt").inputStream()
        val lineList = mutableListOf<Int>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(Integer.parseInt(it))} }

        return lineList
    }
}