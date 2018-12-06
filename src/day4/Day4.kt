package code.day4

import java.io.File
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Day4 {
    fun run() {
        partA()
        partB()
    }

    fun partA() {
        println("Day 4 - Part A")
        val fileValuesList = readInputFile()
        var map = mutableMapOf<String, IntArray>()

        var guardId = ""
        var sleep: Int = -1
        var awake: Int = -1

        fileValuesList.forEach{
            when {
                it.line.contains("Guard") -> {
                    guardId = getId(it.line)
                    sleep = -1
                    awake = -1
                }
                it.line.contains("wakes up") -> awake = getMinutes(it.line)
                it.line.contains("falls asleep") -> sleep = getMinutes(it.line)
            }

            // create array of minutes, increment each minute every time guard sleeps it
            if (sleep in 0..(awake - 1)) {
                if (!map.containsKey(guardId)) {
                    map[guardId] = IntArray(60)
                }

                for (i in sleep..awake) {
                    map[guardId]?.set(i, ((map[guardId]?.get(i) ?: 0) + 1))
                }
            }
        }

        // compute total minutes slept by each guard. Need guard that slept
        // the most minutes.
        var maxKey = ""
        var maxSleep = 0

        for (key in map.keys) {
            val array = map[key]
            var sum = 0

            for (i in 0..59) {
                val value = array!![i]
                sum += value
            }

            if (sum > maxSleep) {
                maxSleep = sum
                maxKey = key
            }
        }

        // for guard the slept most, find most slept minute
        val array = map[maxKey]

        var maxMinute: Int = 0
        var maxMinuteAt: Int = 0
        for (i in 0..59) {
            val value = array!![i]
            if (value > maxMinute) {
                maxMinute = value
                maxMinuteAt = i
            }
        }

        val answer = Integer.parseInt(maxKey) * maxMinuteAt

        println ("\t* ID: $maxKey Minutes Slept: $maxSleep Max Minute: $maxMinuteAt Answer: $answer")
    }

    fun partB() {
        println("Day 4 - Part B")
        val fileValuesList = readInputFile()
        var map = mutableMapOf<String, IntArray>()

        var guardId = ""
        var sleep: Int = -1
        var awake: Int = -1

        fileValuesList.forEach{
            when {
                it.line.contains("Guard") -> {
                    guardId = getId(it.line)
                    sleep = -1
                    awake = -1
                }
                it.line.contains("wakes up") -> awake = getMinutes(it.line)
                it.line.contains("falls asleep") -> sleep = getMinutes(it.line)
            }

            if (sleep in 0..(awake - 1)) {
                if (!map.containsKey(guardId)) {
                    map[guardId] = IntArray(60)
                }

                for (i in sleep..awake) {
                    map[guardId]?.set(i, ((map[guardId]?.get(i) ?: 0) + 1))
                }
            }
        }

        var maxKey = ""
        var maxMinute = 0
        var maxMinuteAt = 0

        // in this part, we don't care about max sleep time, just the guard with
        // the most times slept at the same minute
        for (key in map.keys) {
            val array = map[key]

            for (i in 0..59) {
                val value = array!![i]
                if (value > maxMinute) {
                    maxMinute = value
                    maxMinuteAt = i
                    maxKey = key
                }
            }
        }

        val answer = Integer.parseInt(maxKey) * maxMinuteAt

        println ("\t* ID: $maxKey Max Minute: $maxMinuteAt Answer: $answer")
    }

    private fun getMinutes(line: String) : Int {
        val id = line.substring(line.indexOf(":")+1, line.indexOf("]"))
        return Integer.parseInt(id)
    }

    private fun getDate(line: String) : String {
        val id = line.substring(line.indexOf("[")+1, line.indexOf("]"))
        return id
    }

    private fun getId(line: String) : String {
        val id = line.substring(line.indexOf("#")+1, line.indexOf(" begins"))
        return id
    }

    private fun readInputFile(): List<Line> {
        val inputStream: InputStream = File("./src/day4/day4_input.txt").inputStream()
        var lineList = mutableListOf<Line>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach {
            lineList.add(Line(getDate(it), it))
        } }
        val cmp = compareBy<Line> { LocalDateTime.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) }
        return lineList.sortedWith(cmp)
    }
}