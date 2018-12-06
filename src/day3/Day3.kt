package code.day3

import java.io.File
import java.io.InputStream

class Day3 {
    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 3 - Part A")
        val fileValuesList = readInputFile()

        var fabric = Array(1000) { IntArray(1000) }

        for(i in 0..fabric.size - 1) {
            var rowArray = fabric[i]
            for(j in 0 until rowArray.size) {
                rowArray[j] = 0
            }
        }


        fileValuesList.forEach{
            val startX = getStartX(it)
            val startY = getStartY(it)
            val width = getStartWidth(it) - 1
            val height = getStartHeight(it) - 1

            for (i in startY..(startY+height)) {
                var rowArray = fabric[i]
                for(j in startX..(startX+width)) {
                    rowArray[j] = ++rowArray[j]
                }
            }
        }

        var overlappingSquares = 0

        for (i in 0..999) {
            var rowArray = fabric[i]
            for(j in 0..999) {
                if (rowArray[j] > 1) {
                    overlappingSquares++
                }
            }
        }

        println("\t* Overlapping squares: $overlappingSquares")
    }

    fun partB() {
        println("Day 3 - Part B")
        val fileValuesList = readInputFile()

        var fabric = Array(1000, { IntArray(1000) })

        for(i in 0..fabric.size - 1) {
            var rowArray = fabric[i]
            for(j in 0..rowArray.size - 1) {
                rowArray[j] = 0
            }
        }

        fileValuesList.forEach{
            val startX = getStartX(it)
            val startY = getStartY(it)
            val width = getStartWidth(it) - 1
            val height = getStartHeight(it) - 1

            for (i in startY..(startY + height)) {
                var rowArray = fabric[i]
                for (j in startX..(startX + width)) {
                    rowArray[j] = ++rowArray[j]
                }
            }
        }

        // All sections marked. Do a second time, if a patch can be mapped completely with only
        // upping the number to 2 then it has only overlapped itself and nothing else has touched
        // the spot.
        fileValuesList.forEach{
            val startX = getStartX(it)
            val startY = getStartY(it)
            val width = getStartWidth(it) - 1
            val height = getStartHeight(it) - 1
            val id = getId(it)

            var noOverlap : Boolean = true
            loop@ for (i in startY..(startY + height)) {
                var rowArray = fabric[i]
                for (j in startX..(startX + width)) {
                    rowArray[j] = ++rowArray[j]
                    if (rowArray[j] > 2) {
                        noOverlap = false
                        break@loop
                    }
                }
            }

            if (noOverlap) {
                println("\t* No overlap id: $id")
            }
        }
    }

    private fun getStartX(line: String) : Int {
        val num = line.substring(line.indexOf("@") + 2, line.indexOf(","))
        return Integer.parseInt(num)
    }

    private fun getStartY(line: String) : Int {
        val num = line.substring(line.indexOf(",") + 1, line.indexOf(":"))
        return Integer.parseInt(num)
    }

    private fun getStartWidth(line: String) : Int {
        val num = line.substring(line.indexOf(":") + 2, line.indexOf("x"))
        return Integer.parseInt(num)
    }

    private fun getStartHeight(line: String) : Int {
        val num = line.substring(line.indexOf("x") + 1, line.length)
        return Integer.parseInt(num)
    }

    private fun getId(line: String) : String {
        val num = line.substring(0, line.indexOf("@"))
        return num
    }

    private fun readInputFile(): List<String> {
        val inputStream: InputStream = File("./src/day3/day3_input.txt").inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }

        return lineList
    }
}