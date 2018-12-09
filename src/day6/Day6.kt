package code.day6

import java.io.File
import java.io.FileInputStream

class Day6 {
    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 6 - Part A")

        // strategy - calculate the distance to every location in space from this point and mark it.
        // If proceeding points have smaller distance than previous point, claim it and update distance.

        val fileValuesList = readInputFile()

        val spaceDistance = 360

        var space = Array(spaceDistance) { Array(spaceDistance) { ChoronalPoint("blank", Int.MAX_VALUE) }}
        var edgePoints = mutableListOf<String>()

        fileValuesList.forEach{
            // for every point, mark distance from every space coordinate if closest so far
            val X = getX(it)
            val Y = getY(it)


            // mark all spots for distance from points
            for (x in 0..(spaceDistance-1)) {
                for (y in 0..(spaceDistance-1)) {
                    val label = "labal" + X + "x" + Y
                    val distance = Math.abs(X - x) + Math.abs(Y - y)

                    val existing = space[x][y]

                    if (existing.distance > distance) {
                        existing.distance = distance
                        existing.label = label
                    } else if (existing.distance == distance) {
                        existing.label = "blank"
                    }
                }
            }
        }

        // mark labels on the edge (infinity)
        for (x in 0..(spaceDistance-1)) {
            for (y in 0..(spaceDistance-1)) {
                if (x == 0 || y == 0 || x == (spaceDistance-1) || y == (spaceDistance-1)) {
                    val existing = space[x][y]
                    edgePoints.add(existing.label)
                }
            }
        }

        val spotMap = mutableMapOf<String, Int>()
        // count the number of spots closest for each point
        for (x in 0..(spaceDistance-1)) {
            for (y in 0..(spaceDistance-1)) {
                val spot = space[x][y]

                if (!edgePoints.contains(spot.label)) {
                    if (spotMap.containsKey(spot.label)) {
                        spotMap.put(spot.label, spotMap.get(spot.label)!! + 1)
                    } else {
                        spotMap.put(spot.label, 1)
                    }
                }
            }
        }

        spotMap.remove("blank")

        val largest = spotMap.values.max()

        println ("\t* Most surrounding spots: $largest $spotMap")

    }

    fun partB() {
        println("Day 6 - Part B")

        // strategy - for every point in space, calculate sum distance to all points.
        // then region of interest will be locations < 10k distance

        val fileValuesList = readInputFile()

        val spaceDistance = 360

        var space = Array(spaceDistance) { Array(spaceDistance) { ChoronalPoint("blank", 0) }}

        // mark all spots for distance from points
        for (x in 0..(spaceDistance-1)) {
            for (y in 0..(spaceDistance-1)) {
                fileValuesList.forEach{
                    // for every point, sum distance to all points, then fine region where sum < 10000
                    val X = getX(it)
                    val Y = getY(it)

                    val distance = Math.abs(X - x) + Math.abs(Y - y)
                    val existing = space[x][y]

                    existing.distance += distance
                }
            }
        }

        var regionOfInterest : Int = 0

        for (x in 0..(spaceDistance-1)) {
            for (y in 0..(spaceDistance-1)) {
                val spot = space[x][y]
                if (spot.distance <= 10000) {
                    regionOfInterest++
                }
            }
        }

        println ("\t* Size of region: $regionOfInterest")
    }

    private fun getX(coord: String) : Int {
        val id = coord.substring(0, coord.indexOf(","))
        return Integer.parseInt(id)
    }

    private fun getY(coord: String) : Int {
        val id = coord.substring(coord.indexOf(", ") + 2, coord.length)
        return Integer.parseInt(id)
    }

    private fun readInputFile(): List<String> {
        val inputStream: FileInputStream = File("./src/day6/day6_input.txt").inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }

        return lineList
    }
}