package code.day9

import java.io.File
import java.io.FileInputStream

class Day9 {
    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 9 - Part A")

        val line = readInputFile()
        val players = getNumPlayers(line)
        val points = getWorth(line)

        calculateWinningValue(points, players)
    }

    private fun calculateWinningValue(points: Int, players: Int) {
        var marbleList = mutableListOf<Int>()
        marbleList.add(0)
        marbleList.add(1)

        var current = 1

        var playerPoints = IntArray(players)
        var playerTurn = 0

        for (i in 2..points) {
            if (i % 23 == 0) {
                playerPoints[playerTurn % players] += i
                current -= 7

                if (current < 0) {
                    current += marbleList.size
                }
                playerPoints[playerTurn % players] += marbleList[current]

                marbleList.removeAt(current)
            } else {
                var spot = current + 2
                if (spot > marbleList.size) {
                    spot -= marbleList.size
                }
                marbleList.add(spot, i)
                current = spot
            }

            if (i% 1000 == 0) {
                println("$i  ${marbleList.size} $current")
            }

            playerTurn ++

        }

        println ("\t* Winner Score: ${playerPoints.max()}")
    }

    fun partB() {
        println("Day 9 - Part B")

        val line = readInputFile()
        val players = getNumPlayers(line)
        val points = getWorth(line)

        calculateWinningValue(points*100, players)
    }

    private fun getNumPlayers(line: String): Int {
        return Integer.parseInt(line.substring(0, line.indexOf(" players")))
    }

    private fun getWorth(line: String): Int {
        return Integer.parseInt(line.substring(line.indexOf("worth ") + "worth ".length, line.indexOf(" points")))
    }

    private fun readInputFile(): String {
        val inputStream: FileInputStream = File("./src/day9/day9_input.txt").inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }

        return lineList.first()
    }
}